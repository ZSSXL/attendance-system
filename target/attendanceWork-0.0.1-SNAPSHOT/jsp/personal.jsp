<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>个人信息查询</title>
        <%
                pageContext.setAttribute("APP_PATH", request.getContextPath());
        %>
        <script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
        <link href="${APP_PATH }/static/css/bootstrap.min.css" rel="stylesheet">
        <script src="${APP_PATH }/static/js/bootstrap.min.js"></script>
        <script src="${APP_PATH}/static/js/clocks.js"></script>
</head>
<body>
<div class="container">
        <div class="row"><h2>个人信息</h2></div>
        <div class="row input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">姓 名</span>
                <input type="text" class="form-control" id="username" aria-describedby="sizing-addon1" disabled="disabled">
        </div>
        <div class="row"><h2></h2></div>
        <div class="row">
                <div class="input-group input-group-lg">
                       <span class="input-group-addon" id="sizing-addon2">密 码</span>
                        <input type="text" class="form-control" id="password" aria-describedby="sizing-addon1"  disabled="disabled">
                        <span class="input-group-btn">
                              <button class="btn btn-danger" type="button" id="passwd-btn">修改</button>
                        </span>
                </div><!-- /input-group -->
        </div><!-- /.row -->
        <div class="row"><h2></h2></div>
        <div class="row input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon3">职  务</span>
                <input type="text" class="form-control" id="role" aria-describedby="sizing-addon1" disabled="disabled">
        </div>
        <div class="row"><h2></h2></div>
        <div class="row">
                <div class="col-md-4 col-md-offset-5 ">
                        <button class="btn btn-primary col-md-4 btn-lg" id="update_btn" disabled="disabled">确定</button>
                </div>

        </div>
</div>
<script type="text/javascript">

    window.onload = function () {
        showMsg();
    };

    function showMsg() {
        $.ajax({
            url: "${APP_PATH}/personal_list.do",
            type: "GET",
            success: function (result) {
                // 判断是否登录
                judugeMsg(result);
                showPersonal(result);
            }
        });
    };

    function showPersonal(result) {
        $("#username").val(result.data.username);
        $("#password").val(result.data.password);
        $("#role").val(result.data.role);
    };

    $("#passwd-btn").click(function () {
        if(confirm("是否修改密码？")){
            $("#password").removeAttr("disabled"); // 解除密码框的不可编辑状态
            $("#update_btn").removeAttr("disabled"); // 接触按钮的不可点击状态
        }
    });

    $("#update_btn").click(function () {
        var newPassword = $("#password").val();
        $.ajax({
            url: "${APP_PATH}/change_password.do",
            data:"newPassword="+newPassword,
            type: "POST",
            success: function (result) {
                if(result.status == 1){
                    alert(result.msg);
                }else{
                    alert(result.msg);
                }
                reloadWin();
            }
        });
    });

    function judugeMsg(result){
        if(result.status == 10){
            alert("请先登录");
            window.open("${APP_PATH}/index.jsp",'_new');
            window.close();
        }
    }

    function reloadWin(){
        window.location.href="${APP_PATH}/jsp/personal.jsp";
    }
</script>
</body>
</html>