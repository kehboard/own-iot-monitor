<%--
  Created by IntelliJ IDEA.
  User: kehboard
  Date: 22.04.2021
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="icon" type="image/png" href="/resources/img/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <title>My monitoring</title>

    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>
    <meta name="viewport" content="width=device-width"/>


    <!-- Bootstrap core CSS     -->
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- Animation library for notifications   -->
    <link href="/resources/css/animate.min.css" rel="stylesheet"/>
    <link href="/resources/js/uPlot/uPlot.min.css" rel="stylesheet"/>

    <!--  Light Bootstrap Table core CSS    -->
    <link href="/resources/css/light-bootstrap-dashboard.css" rel="stylesheet"/>


    <%--<!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="/resources/css/demo.css" rel="stylesheet" />--%>


    <!--     Fonts and icons     -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300' rel='stylesheet' type='text/css'>
    <link href="/resources/css/pe-icon-7-stroke.css" rel="stylesheet"/>

</head>
<body>

<div class="wrapper">
    <div class="sidebar" data-color="purple" data-image="/resources/img/sidebar-5.jpg">

        <!--
            Tip 1: you can change the color of the sidebar using: data-color="blue | azure | green | orange | red | purple"
            Tip 2: you can also add an image using data-image tag
        -->
        <div class="sidebar-wrapper">
            <div class="logo">
                <a href="/" class="simple-text">
                    My monitoring
                </a>
            </div>

            <ul class="nav">
                <li>
                    <a href="/dashboard">
                        <i class="pe-7s-signal"></i>
                        <p>Устройства</p>
                    </a>
                </li>
                <li class="">
                    <a href="/dashboard/addNewDevice">
                        <i class="pe-7s-plus"></i>
                        <p>Новое устройство</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <div class="main-panel">
        <nav class="navbar navbar-default navbar-fixed">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#navigation-example-2">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Панель управления</a>
                    <a type="button" class="btn" id="refreshButton"><i class="pe-7s-refresh-2"></i></a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                Учетная запись
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="/dashboard/accountSettings">Настройки</a></li>
                                <li class="divider"></li>
                                <li><a href="/logout">Выйти</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Об устройстве
                                </h4>
                                <p class="category"></p>
                            </div>
                            <div class="content">
                                <div class="row">
                                    <div class="col-md-12" id="deviceInfo">
                                        <p>Имя устройства: ${device.deviceName}</p>
                                        <hr>
                                        <h5>Описание</h5>
                                        <p>${device.description}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">График</h4>
                                <p class="category"></p>
                            </div>
                            <div class="content">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div style="width: 100%; height: 100%">
                                            <canvas id="test"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Измеряемые величины</h4>
                                <p class="category"></p>
                            </div>
                            <div class="content" id="measureNameDiv">
                                <div class="row">
                                    <div class="col-md-10">
                                        <table class="table" id="measureNames">
                                            <tr>

                                                <th>№</th>
                                                <th>Название</th>
                                                <th>Переменная</th>
                                                <th>*</th>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Последние данные</h4>
                                <p class="category"></p>
                            </div>
                            <div class="content">
                                <div class="row">
                                    <div class="col-md-12">
                                        <ul class="nav nav-tabs" id="tabs">

                                        </ul>
                                        <div class="tab-content" id="tab-content">

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer class="footer">
            <div class="container-fluid">
                <p class="copyright pull-right">
                    &copy; 2021 <a href="http://github.com/kehboard">kehboard</a>, made with love :)
                </p>
            </div>
        </footer>

    </div>
</div>


</body>

<!--   Core JS Files   -->
<script src="/resources/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>

<!--  Checkbox, Radio & Switch Plugins -->
<script src="/resources/js/bootstrap-checkbox-radio-switch.js"></script>

<!--  Notifications Plugin    -->
<script src="/resources/js/bootstrap-notify.js"></script>

<!--  Google Maps Plugin
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>-->

<!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
<script src="/resources/js/light-bootstrap-dashboard.js"></script>
<script src="/resources/js/jquery.cookie.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.1/Chart.bundle.min.js"></script>
<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
<%--<script src="/resources/js/demo.js"></script>--%>

<script type="text/javascript">
    function getMeasureNamesAndMeasurements() {
        idOfDevice = window.location.pathname.split("/")
        idOfDevice = idOfDevice[idOfDevice.length - 1]
        $.ajax({
            url: "/api/user/v1/device/" + idOfDevice + "/getLatestData?amount=10",
            type: "GET",
            contentType: "application/json",
            success: function (result) {
                console.log(result)
                //tableHeader = "<tr><th>№</th><th>Дата/Время</th>"
                tmpDatasets = []
                $("#measureNames").html("<tr>" +
                    "<th>№</th>" +
                    "<th>Название</th>" +
                    "<th>Переменная</th>" +
                    "<th>*</th>" +
                    " </tr>")
                for (i = 0; i < result.measureNames.length; i++) {
                    //tableHeader += "<th>"+ result.measureNames[i].measureName + "," + result.measureNames[i].measureSymbol +"</th>"

                    template = "<tr>" +
                        "<td>" + (i + 1) + "</td>" +
                        "<td>" + result.measureNames[i].measureName + "</td>" +
                        "<td>" + result.measureNames[i].iotName + "</td>" +
                        "<td>" + result.measureNames[i].measureSymbol + "</td>" +
                        "</tr>"
                    $("#measureNames").append(template)
                    tmpData = []
                    for (k = 0; k < result.measures[result.measureNames[i].id].length; k++) {
                        tmpData.push({
                            x: result.measures[result.measureNames[i].id][k].unixtime,
                            y: result.measures[result.measureNames[i].id][k].value
                        })
                    }
                    tmpDatasets.push({
                        label: result.measureNames[i].measureName + ", " + result.measureNames[i].measureSymbol,
                        data: tmpData
                    })

                }
                $("#tabs").html("")
                $("#tab-content").html("")
                for (i = 0; i < tmpDatasets.length; i++) {
                    templateTabs = '<li class="nav-item">' +
                        '<a class="nav-link" data-toggle="tab" href="#measure' + i + '">' + tmpDatasets[i].label + '</a>' +
                        '</li>'
                    templateTabsContent = '<div class="tab-pane fade" id="measure' + i + '"><table class="table"><tr><th>№</th><th>Дата</th><th>' + tmpDatasets[i].label + '</th></tr>'
                    for (k = 0; k < tmpDatasets[i].data.length; k++) {
                        templateTabsContent += "<tr><td>"
                        templateTabsContent += k + 1
                        templateTabsContent += "</td>"
                        templateTabsContent += "<td>"
                        dt = eval(tmpDatasets[i].data[k].x * 1000);
                        myDate = new Date(dt);
                        templateTabsContent += myDate.toLocaleString()
                        templateTabsContent += "</td>"
                        templateTabsContent += "<td>"
                        templateTabsContent += tmpDatasets[i].data[k].y
                        templateTabsContent += "</td></tr>"
                    }
                    templateTabsContent += "</table></div>"
                    $("#tabs ").append(templateTabs)
                    $("#tab-content").append(templateTabsContent)
                }
                var ctx = document.getElementById("test").getContext("2d");
                var scatterChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        datasets: tmpDatasets
                    },
                    options: {
                        animation:false,
                        tooltips: {
                            callbacks: {
                                title(datasets) {
                                    var time = new Date(datasets[0].xLabel * 1000);
                                    return (time.getMonth() + 1) + '/' + time.getDate() + ' ' + time.getHours();
                                }
                            }
                        },
                        scales: {
                            xAxes: [
                                {
                                    type: 'linear',
                                    position: 'bottom',
                                    ticks: {
                                        callback(value) {
                                            var time = new Date(value * 1000);
                                            return (time.getMonth() + 1) + '/' + time.getDate() + ' ' + time.getHours();
                                        }
                                    }
                                }
                            ]
                        }
                    }
                });
            }
        });
    }
    $(document).ready(function () {
        getMeasureNamesAndMeasurements()
        $("#refreshButton").click(getMeasureNamesAndMeasurements)
    });
</script>
