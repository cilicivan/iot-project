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
            $('#temperature').val(data.temperature)
            $('#brightness').val(data.brightness)
        },
        error: function (err) {
            alert('Something went wrong!')
        }
    })
    $('#submit_form').on('click',function () {
        var temperature=$('#temperature').val()
        var brightness=$('#brightness').val()
        $.ajax({
            type: 'POST',
            url: '/sensors/params',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            data: {temperature: temperature, brightness:brightness},
            success: function (data) {
                document.location.href="/"

            }
        })
        })
    })

