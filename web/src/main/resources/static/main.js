console.log('ajde')

var brightness
var temperature
var avgTempInside
var avgTempOutside
var avgBrightness
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: '/sensors/params',
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            console.log(data.temperature)
            $('#temperature').text('Defined temperature: ' + data.temperature + '°C ')
            $('#brightness').text('Defined brightness: ' + data.brightness + '%')
            brightness = data.brightness
            temperature = data.temperature


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
            for (var i = 0; i < data.length; i++) {
                var sensor = data[i];


                $('#sensors').append('            <li><a href=#' + sensor.id + '>' + sensor.name + ' </a></li>\n')
                $('#sensor').append('<h3>' + sensor.name + '</h3>\n' +
                    '    <table id=' + sensor.id + ' class="table table-dark">\n' +
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

                var measurements = sensor.measurements;
                measurements.sort(function (a, b) {
                    return b.time - a.time
                })

                // console.log(measurements[i].parameter)
                var sum = 0
                var count = 0
                for (var j = 0; j < 3 && j < measurements.length; j++) {
                    var measurement = measurements[j];
                    $('#' + sensor.id).append(' <tr>\\n\' +\n' +
                        '                    \'            <th scope="row">' + (j + 1) + '</th>\\n\' +\n' +
                        '                    \'            <td>' + measurement.parameter + '</td>\\n\' +\n' +
                        '                    \'            <td>' + measurement.value + getParameter(measurement) + '</td>\\n\' +\n' +
                        '                    \'            <td>' + timeConverter(measurement.time) + '</td>\\n\' +\n' +

                        '                    \'        </tr>\\n\' +')

                    //count average
                    if (isRelevant(measurement.parameter)) {
                        sum += measurement.value;
                        count++;
                    }
                }
                if (measurements.length > 0) {
                    if (isRelevant(measurements[0].parameter)) {
                        console.log('sum is' + sum + ", count is " + count)
                        var avg = (sum / count).toFixed(2);
                        console.log('avg is ' + avg)
                        $('#' + sensor.id).append(' <tr><th scope="row"></th><td>...</td></tr><tr>\\n\' +\n' +
                            '                    \'            <th scope="row">AVERAGE</th>\\n\' +\n' +
                            '                    \'            <td><b>' + avg + getParameter(measurements[0]) + '</b></td>\\n\' +\n' +

                            '                    \'        </tr>\\n\' +')

                        console.log(measurements[0].parameter)

                        if (measurements[0].parameter == 'brightness') {
                            avgBrightness=avg

                            console.log('je')
                            if (avg > 1.2 * brightness) {
                                console.log('bri' + brightness + ', avg ' + avg)
                                $('#myModal').find('p').text('Brightness too high, turn lights off')
                                $('#myModal').modal('show')
                            }
                            if (avg < 0.8 * brightness) {
                                $('#myModal').find('p').text('Brightness too low, turn lights on')
                                $('#myModal').modal('show')
                            }
                        }
                        if (measurements[0].parameter == 'temperature-inside') {
                           avgTempInside=avg
                        }
                        if (measurements[0].parameter == 'temperature-outside') {
                            avgTempOutside=avg
                        }

                    }
                }
            }

            console.log('avg tem inside' +avgTempInside + ', temp '+ temperature)
            if(avgTempInside>1.2*temperature){
                console.log('asfasf')
                    $('#myModal').find('p').text('Temperature in the room too high,open window')
                $('#myModal').modal('show')
            }
            if(avgTempOutside<0.8*temperature){
                console.log('asdadsf')
                $('#myModal').find('p').text('Temperature in the room too low, turn on air condition')
                $('#myModal').modal('show')
            }
            $('#sensor').append(' <div class="col-sm-offset-2 col-sm-10">\n' +
                '        <button  onclick="window.location.href = \'#\'" class="btn btn-alert">HOME</button>\n' +
                '    </div>')
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
            data: {
                parameter: 'temperature-inside',
                value: 13.3,
                presence: true,
                time: Math.floor(Date.now() / 1000),
                sensorSerial: sensorSerial
            },
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
        case 'temperature-inside' :
            return '°C'
        case 'temperature-outside' :
            return '°C'
        case 'humidity' :
            return '%'
        case 'brightness' :
            return '%'

    }
}

function timeConverter(UNIX_timestamp) {
    var a = new Date(UNIX_timestamp * 1000);
    var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec;
    return time;
}


function isRelevant(parameter) {
    switch (parameter) {
        case 'temperature-inside'  :
            return true;
        case 'temperature-outside'  :
            return true;
        case  'brightness' :
            return true;
        case  'humidity'  :
            return true;
    }
    return false
}
