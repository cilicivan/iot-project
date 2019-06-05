// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

// This application uses the Microsoft Azure Event Hubs Client for Java
// For samples see: https://github.com/Azure/azure-event-hubs/tree/master/samples/Java
// For documenation see: https://docs.microsoft.com/azure/event-hubs/

package hr.fer.tel.iot.project.cloud;

import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import com.microsoft.azure.eventhubs.EventPosition;
import com.microsoft.azure.eventhubs.EventHubRuntimeInformation;
import com.microsoft.azure.eventhubs.PartitionReceiver;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.net.URI;
import java.net.URISyntaxException;

public class ReadDeviceToCloudMessages implements Runnable{

    // az iot hub show --query properties.eventHubEndpoints.events.endpoint --name {your IoT Hub name}
    private static final String eventHubsCompatibleEndpoint = "sb://iothub-ns-smart-home-1731109-06b6ded9dc.servicebus.windows.net/";

    // az iot hub show --query properties.eventHubEndpoints.events.path --name {your IoT Hub name}
    private static final String eventHubsCompatiblePath = "smart-home-iot-lab";

    // az iot hub policy show --name iothubowner --query primaryKey --hub-name {your IoT Hub name}
    private static final String iotHubSasKey = "Fn1Rxa99TB3a+nzGO4Ks1W/fZeZf1XyeohIYwHBvK9g=";
    private static final String iotHubSasKeyName = "iothubowner";

    // Track all the PartitionReciever instances created.
    private static ArrayList<PartitionReceiver> receivers = new ArrayList<PartitionReceiver>();

    // Asynchronously create a PartitionReceiver for a partition and then start
    // reading any messages sent from the simulated client.
    private static void receiveMessages(EventHubClient ehClient, String partitionId)
            throws EventHubException, ExecutionException, InterruptedException {

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Create the receiver using the default consumer group.
        // For the purposes of this sample, read only messages sent since
        // the time the receiver is created. Typically, you don't want to skip any messages.
        ehClient.createReceiver(EventHubClient.DEFAULT_CONSUMER_GROUP_NAME, partitionId,
                EventPosition.fromEnqueuedTime(Instant.now())).thenAcceptAsync(receiver -> {
            System.out.println(String.format("Starting receive loop on partition: %s", partitionId));
            System.out.println(String.format("Reading messages sent since: %s", Instant.now().toString()));

            receivers.add(receiver);

            while (true) {
                try {
                    // Check for EventData - this methods times out if there is nothing to retrieve.
                    Iterable<EventData> receivedEvents = receiver.receiveSync(100);

                    // If there is data in the batch, process it.
                    if (receivedEvents != null) {
                        for (EventData receivedEvent : receivedEvents) {
                            String paramsString = new String(receivedEvent.getBytes(), Charset.defaultCharset());
                            System.out.println(String.format("Telemetry received:\n %s",
                                    paramsString));
                            System.out.println(String.format("Application properties (set by device):\n%s",receivedEvent.getProperties().toString()));
                            System.out.println(String.format("System properties (set by IoT Hub):\n%s\n",receivedEvent.getSystemProperties().toString()));

                            CredentialsProvider provider = new BasicCredentialsProvider();
                            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("admin", "admin");
                            provider.setCredentials(AuthScope.ANY, credentials);


                            try {


                                RestTemplate restTemplate=new RestTemplate();
                                HttpHeaders headers=new HttpHeaders();
                                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                                headers.setBasicAuth("admin","admin");
                                HttpEntity<String> request=new HttpEntity<>(paramsString, headers);

                                restTemplate.put("http://localhost:8090/measurements",request);

                            }catch (Exception ex) {

                                System.out.println(ex);

                            }
                        }
                    }
                } catch (EventHubException e) {
                    System.out.println("Error reading EventData");
                }
            }
        }, executorService);
    }

    public  void run() {
        try {
            final ConnectionStringBuilder connStr = new ConnectionStringBuilder()
                    .setEndpoint(new URI(eventHubsCompatibleEndpoint))
                    .setEventHubName(eventHubsCompatiblePath)
                    .setSasKeyName(iotHubSasKeyName)
                    .setSasKey(iotHubSasKey);

            // Create an EventHubClient instance to connect to the
            // IoT Hub Event Hubs-compatible endpoint.
            final ExecutorService executorService = Executors.newSingleThreadExecutor();
            final EventHubClient ehClient = EventHubClient.createSync(connStr.toString(), executorService);

            // Use the EventHubRunTimeInformation to find out how many partitions
            // there are on the hub.
            final EventHubRuntimeInformation eventHubInfo = ehClient.getRuntimeInformation().get();

            // Create a PartitionReciever for each partition on the hub.
            for (String partitionId : eventHubInfo.getPartitionIds()) {
                receiveMessages(ehClient, partitionId);
            }

            // Shut down cleanly.
            System.out.println("Press ENTER to exit.");
            System.in.read();
            System.out.println("Shutting down...");
            for (PartitionReceiver receiver : receivers) {
                receiver.closeSync();
            }
            ehClient.closeSync();
            executorService.shutdown();
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
