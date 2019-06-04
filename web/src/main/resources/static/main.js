console.log('ajde')

$(document).ready(function () {
    $.ajax({
        type : 'GET',
        url: '/sensors/params',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            console.log(data.temperature)
            $('#temperature').text('Defined temperature: '+ data.temperature + '°C ' )
            $('#brightness').text('Defined brightness: ' + data.brightness + '%')


        },
        error: function (err) {
            alert('Something went wrong!')
        }
    })
    console.log('prosa prvi')
    $.ajax({
        type: 'GET',
        url: '/sensors',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            for(var i=0;i<data.length;i++){
                var sensor=data[i];


                $('#sensors').append('            <li><a href=#'+sensor.id+'>'+sensor.name +' </a></li>\n')
                $('#sensor').append('<h3>'+sensor.name+'</h3>\n' +
                    '    <table id='+sensor.id+' class="table table-dark">\n' +
                    '        <thead>\n' +
                    '        <tr>\n' +
                    '\n' +
                    '            <th scope="col">#</th>\n' +
                    '            <th scope="col">Parameter</th>\n' +
                    '            <th scope="col">Value</th>\n' +
                    '            <th scope="col">Time</th>\n' +
                    '        </tr>\n' +
                    '        </thead>\n' +
                    '        <tbody>\n' +
                    '        </tbody>\n' +
                    '    </table>');

                var measurements=sensor.measurements;
                measurements.sort(function(a,b){return  b.time-a.time})

               // console.log(measurements[i].parameter)
                for(var j=0;j<3 && j<measurements.length;j++){
                    var measurement=measurements[j];
                    $('#'+sensor.id).append(' <tr>\\n\' +\n' +
                        '                    \'            <th scope="row">'+(j+1)+'</th>\\n\' +\n' +
                        '                    \'            <td>'+measurement.parameter+'</td>\\n\' +\n' +
                        '                    \'            <td>'+measurement.value+getParameter(measurement)+'</td>\\n\' +\n' +
                        '                    \'            <td>'+timeConverter(measurement.time)+'</td>\\n\' +\n' +

                        '                    \'        </tr>\\n\' +')

                }
            }
        },
        error: function (err) {
            alert(err.state())
        }
    })



    $('#addMeasurement').on('click', function () {
        var temperature = 5.5
        var brightness = 4.5
        var sensorSerial = "1"
        $.ajax({
            type: 'POST',
            url: '/measurements',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            data: {parameter: 'temperature', value: 13.3,presence : true, time : Math.floor(Date.now() / 1000),sensorSerial: sensorSerial},
            success: function (data) {
                alert('measurement  inserted!')
                document.location.reload()

            },
            error: function (err) {
                alert(err.state())
            }

        })
    })
})



function getParameter(measurement) {

    switch (measurement.parameter) {
        case 'temperature' : return '°C'
        case 'humidity' : return '%'
        case 'brightness' : return '%'

    }
}

function timeConverter(UNIX_timestamp){
    var a = new Date(UNIX_timestamp * 1000);
    var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec ;
    return time;
}


