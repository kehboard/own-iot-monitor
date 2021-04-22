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
                <li>
                    <a href="/dashboard">
                        <i class="pe-7s-signal"></i>
                        <p>Devices</p>
                    </a>
                </li>
                <li class="active">
                    <a href="/dashboard/addNewDevice">
                        <i class="pe-7s-plus"></i>
                        <p>Add new device</p>
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
                                Account
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="/dashboard/accountSettings">Settings</a></li>
                                <li class="divider"></li>
                                <li><a href="/logout">Log out</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-5">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Добавление устройства</h4>
                                <p class="category"></p>
                            </div>
                            <div class="content">
                                <form id="addDevice">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Имя устройства</label>
                                                <input type="text" name="name" class="form-control" placeholder="Имя устройства">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <input class="form-check-input" name="isPublic" type="checkbox" id="isPublic">
                                            <label class="form-check-label" for="isPublic">
                                                Сделать это устройство публичным
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Описание устройства</label>
                                                <textarea rows="5" class="form-control" name="description" placeholder="Описание устройства (необязательно)" value="Mike"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-raised btn-primary">Добавить устройство</button>
                                </form>
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

        $("#addDevice").submit(function (e) {
            e.preventDefault();
            var form = $(this).serializeArray().reduce(function(obj, item) {
                obj[item.name] = item.value;
                return obj;
            }, {});
            $.ajax({
                url:"/api/user/v1/device/create?apikey="+$.cookie("authToken"),
                type:"POST",
                contentType:"application/json",
                dataType:"json",
                data:JSON.stringify({"device":{"deviceName":form.name,
                        "description":form.description,
                        "isPublic": form.isPublic === undefined ? false : true}}),
                success:function(result){
                    $.notify({
                        icon: 'pe-7s-check',
                        message: "Устройсво "+form.name+" успешно добавлено"
                    },{
                        type: 'success',
                        timer: 4000
                    });
                    setTimeout(function (e){ window.location.href="/dashboard/viewDevice/"+result.id},1000)
                }
            });
            }
        )
        //getDevices()
    });
</script>

