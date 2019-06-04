$(document).ready(function () {
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

            },
            error: function (err) {
                alert(err.state())
            }

        })
        })
    })

