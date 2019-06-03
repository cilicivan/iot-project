console.log('ajde')

$(document).ready(function () {



    $('#addMeasurement').on('click', function () {
        var temperature = 5.5
        var brightness = 4.5
        var sensorSerial = "123"
        $.ajax({
            type: 'POST',
            url: '/measurements',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            data: {temperature: temperature, brightness: brightness, sensorSerial: sensorSerial},
            success: function (data) {
                alert('measurement  inserted!')
            },
            error: function (err) {
                alert(err.state())
            }

        })
    })
})
