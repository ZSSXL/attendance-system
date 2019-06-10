<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>apply</title>
        <%
                pageContext.setAttribute("APP_PATH", request.getContextPath());
        %>
        <!-- 引入jQuery -->
        <!-- 引入样式 -->
        <script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
        <link href="${APP_PATH }/static/css/bootstrap.min.css" rel="stylesheet">
        <script src="${APP_PATH }/static/js/bootstrap.min.js"></script>
        <script src="${APP_PATH}/static/js/clocks.js"></script>
</head>
<body>
        <div class="container" id="applyLeave">
                <form class="apply_table">
                        <div>
                                <h2 style="text-align: center">请假申请</h2>
                        </div>
                        <div class="form-group">
                                <label>请假时长</label>
                                <input type="text" class="form-control" id="needTime" placeholder="请假时长"/>
                        </div>
                        <div class="form-group">
                                <label>请假开始时间</label>
                                <input type="date" id="leaveTime" class="form-control"/>
                        </div>
                        <div class="form-group">
                                <label>请假结束时间</label>
                                <input type="date" id="backTime" class="form-control"/>
                        </div>
                        <div class="form-group">
                                <label>请假理由</label>
                                <textarea class="form-control" rows="3" id="reason" placeholder="请输入请假理由"></textarea>
                        </div>
                        <button type="button" class="btn btn-success" id="applyBtn">提交</button>
                </form>
        </div>
        <script type="text/javascript">
                $("#applyBtn").click(function () {
                    apply();
                });

                function apply(){
                    var needTime = $("#needTime").val();
                    var leaveTime = $("#leaveTime").val();
                    var backTime = $("#backTime").val();
                    var reason = $("#reason").val();
                    $.ajax({
                        url:"${APP_PATH}/apply.do",
                        type:"POST",
                        data:"needTime="+needTime+"&leaveTime="+leaveTime+"&backTime="+backTime+"&reason="+reason,
                        success:function (result) {
                            //console.log(result);
                            judugeMsg(result);
                       }
                  });
                };

                function judugeMsg(result){
                    if(result.status == 10){
                        alert("请先登录");
                        window.open("${APP_PATH}/index.jsp",'_new');
                        window.close();
                    }

                    if(result.status == 0){
                        alert(result.msg);
                        //$("#leave_table").empty();
                        $("#needTime").val("");
                        $("#leaveTime").val("");
                        $("#backTime").val("");
                        $("#reason").val("");
                    }else if(result.status == 1){
                        alert("对不起，"+result.msg+"，请重新尝试！");
                        $("#needTime").val("");
                        $("#leaveTime").val("");
                        $("#backTime").val("");
                        $("#reason").val("");
                    }
                };
        </script>
</body>
</html>