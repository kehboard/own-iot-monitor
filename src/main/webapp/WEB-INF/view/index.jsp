<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <a href="/dashboard" class="simple-text">
                    My monitoring
                </a>
            </div>

            <ul class="nav">
                <li class="active">
                    <a href="/dashboard">
                        <i class="pe-7s-signal"></i>
                        <p>Устройства</p>
                    </a>
                </li>
                <li>
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
                    <%--<div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Пример устройства</h4>
                                <p class="category">Последние данные</p>
                            </div>
                            <div class="content">
                                <div id="chartPreferences2" class="ct-chart ct-perfect-fourth"></div>

                                <div class="footer">
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> Измерение 1
                                        <i class="fa fa-circle text-danger"></i> Измерение 2
                                        <i class="fa fa-circle text-warning"></i> Измерение N
                                    </div>
                                    <hr>
                                    <div class="stats">
                                        <i class="fa fa-clock-o"></i> Последние данные получены 1 минуту назад
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>--%>
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

<!--  Charts Plugin -->
<script src="/resources/js/chartist.js"></script>

<!--  Notifications Plugin    -->
<script src="/resources/js/bootstrap-notify.js"></script>

<!--  Google Maps Plugin    -->
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

<!-- Light Bootstrap Table Core javascript and methods for Demo purpose -->
<script src="/resources/js/light-bootstrap-dashboard.js"></script>
<script src="/resources/js/jquery.cookie.js"></script>

<!-- Light Bootstrap Table DEMO methods, don't include it in your project! -->
<%--<script src="/resources/js/demo.js"></script>--%>

<script type="text/javascript">
    $(document).ready(function () {
        function getDevices() {
            $.ajax({
                url:"/api/user/v1/device/list?apikey="+$.cookie("authToken"),
                type:"GET",
                success:function(result){
                    result.forEach(function (elem) {
                        template="<a href=\"/dashboard/viewDevice/"+elem.id+"\"><div class=\"col-md-4\">\n" +
                            "                        <div class=\"card\">\n" +
                            "                            <div class=\"header\">\n" +
                            "                                <h4 class=\"title\">"+(elem.isPublic ? "<i class=\"pe-7s-global\"></i> " : "<i class=\"pe-7s-lock\"></i> ")+elem.deviceName+"</h4>\n" +
                            "                                <p class=\"category\">"+elem.description+"</p>\n" +
                            "                            </div>\n" +
                            "                            <div class=\"content\">\n" +
                            "                            </div>\n" +
                            "                        </div>\n" +
                            "                    </div></a>"
                        $(template).appendTo(".row")
                    })
                }
            });
        }
        getDevices()
    });
</script>

