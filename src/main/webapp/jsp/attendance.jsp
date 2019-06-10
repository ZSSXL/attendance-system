<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>attendance</title>
        <%
                pageContext.setAttribute("APP_PATH", request.getContextPath());
        %>
        <!-- 引入jQuery -->
        <!-- 引入样式 -->
        <script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
        <link href="${APP_PATH }/static/css/bootstrap.min.css" rel="stylesheet">
        <script src="${APP_PATH }/static/js/bootstrap.min.js"></script>

</head>
<body>
        <div class="container">
                <div>
                        <h4>  </h4>
                </div>
                <div class="panel panel-default">
                        <div class="panel-body">
                                <h3>欢迎 <span class="label label-info">${sessionScope.currentUser.username}</span></h3>
                        </div>
                </div>

                <div class="col-md-12">
                        <button type="button" class="btn btn-success btn-lg col-md-5" id="signIn">签到</button>
                        <span class="col-md-1"></span>
                        <button type="button" class="btn btn-danger btn-lg col-md-5" id="signOut">签退</button>
                </div>
                <div class="col-md-offset-3">
                        <div class="clocks">
                                <canvas id="canvas" width="500" height="500"></canvas>
                        </div>
                </div>
        </div>
        <script type="text/javascript">
            // 点击签到
            $("#signIn").click(function(){
                var sign = "signIn";
                signInOut(sign);
            });
            // 点击签退
            $("#signOut").click(function(){
                var sign = "signOut";
                signInOut(sign);
            });

            function signInOut(sign){
                $.ajax({
                    type:"post",
                    url:"${APP_PATH}/sign_in_out.do",
                    data:"sign="+sign,
                    success:function (result) {
                        judgeMsg(result);
                    }
                });
            };

            function judgeMsg(result){
                if(result.msg == "NEED_LOGIN"){
                    alert("请先登录");
                    window.open("${APP_PATH}/index.jsp",'_new');
                    window.close();
                }else{
                    alert(result.msg);
                }
            };
        </script>
        <script src="${APP_PATH}/static/js/clocks.js"></script>
</body>
</html>