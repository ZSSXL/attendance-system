<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="x-admin-sm">
<head>
        <meta charset="UTF-8">
        <title>考勤管理系统</title>
        <%
                pageContext.setAttribute("APP_PATH", request.getContextPath());
        %>
        <%
                session.invalidate();
        %>
        <meta name="renderer" content="webkit|ie-comp|ie-stand">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta http-equiv="Cache-Control" content="no-siteapp"/>
        <link rel="stylesheet" href="${APP_PATH}/static/css/font.css">
        <link rel="stylesheet" href="${APP_PATH}/static/css/login.css">
        <link rel="stylesheet" href="${APP_PATH}/static/css/xadmin.css">
        <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
        <script src="${APP_PATH}/static/lib/layui/layui.js" charset="utf-8"></script>
        <!--[if lt IE 9]>
        <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
        <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>

        <![endif]-->
</head>
<body class="login-bg">
        <div class="login layui-anim layui-anim-up">

                <div class="message">考勤管理系统测试</div>
                <div id="darkbannerwrap"></div>
                <%--  action="${pageContext.request.contextPath}/login.do" --%>
                <form method="post" class="layui-form" id="loginForm">
                        <input name="username" placeholder="用户名" type="text" lay-verify="required" class="layui-input" id="username">
                        <hr class="hr15">
                        <input name="password" lay-verify="required" placeholder="密码" type="password" class="layui-input" id="password">
                        <hr class="hr15">
                        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit" id="loginBtn">
                        <hr class="hr20">
                        <input value="注册" lay-submit lay-filter="register" style="width:100%;" type="button" id="registerBtn">
                        <hr class="hr20">
                </form>
                <div id="message"></div>
        </div>
        <script type="text/javascript">

                $("#loginBtn").click(function(){
                    getMsg();
                    alert("欢迎光临");
                });

                function getMsg(){
                    var username = $("#username").val();
                    var password = $("#password").val();
                        $.ajax({
                            type:"post",
                            url:"${APP_PATH}/login.do",
                            data:"username="+username+"&password="+password,
                            success:function(result){
                                judgementResult(result);
                            }
                        });
                };

                // 判断登录结果
                function judgementResult(result){
                        if(result.status == 0){
                            confirm(result.msg);
                            window.location.href="${APP_PATH}/jsp/main.jsp";
                        }else{
                                confirm(result.msg);
                                $("#username").empty();
                                $("#username").empty();
                        }
                };

                $("#registerBtn").click(function(){
                   alert("思考了很久，还是不做这个功能了，这只是个考勤系统而已");
                });


         </script>1

</body>
</html>