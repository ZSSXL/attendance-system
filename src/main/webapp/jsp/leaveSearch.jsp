<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>假条查询</title>
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
<div class="container">

        <div class="modal fade"  id="leaveModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                        <div class="modal-content">
                                <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title">假条详情</h4>
                                </div>
                                <form>
                                        <div class="modal-body">
                                                <div class="form-group">
                                                        <span>申请人:</span>
                                                        <label id="applyer"></label>
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
                                        </div>
                                </form>
                                <div class="modal-footer">
                                        <button type="button" id="update_btn" class="btn btn-warning" data-dismiss="modal">确认修改</button>
                                        <button type="button" id="cancel_btn" class="btn btn-info">关闭</button>
                                </div>
                        </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <div class="row">
                <div class="col-md-8">
                        <h1>假条审核</h1>
                </div><!-- 按钮 -->
                        <div class="col-md-4 col-md-offset-10">
                                <button class="btn btn-success" id="personal-btn" choice_personal_btn="personal">个人</button>
                               <%-- 判断是否为管理员，是则显示这个按钮 --%>
                                <c:if test="${sessionScope.currentUser.rid == 1}">
                                        <button class="btn btn-info" id="all-btn" choice_all_btn="all">全部</button>
                                </c:if>
                        </div>
        </div>
        <!-- 表格数据 -->
        <div class="row">
                <div class="col-md-12">
                        <table class="table table-hover" id="leave_table">
                                <thead>
                                <tr>
                                        <th>申请人</th>
                                        <th>申请时长</th>
                                        <th>哪天开始</th>
                                        <th>哪天结束</th>
                                        <th>请假理由</th>
                                        <th>申请时间</th>
                                        <th>假条状态</th>
                                        <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                        </table>
                </div>
        </div>
        <!-- 分页信息 -->
        <div class="row">
                <!-- 分页文字信息 -->
                <div class="col-md-6 " id="page_info_area">

                </div>
                <!-- 分页条信息 -->
                <div class="col-md-6" id="page_nav_area">

                </div>
        </div>
</div>
<script type="text/javascript">
    var totalRecord,currentPage;	// 保存分页总记录数
    var choice = "personal"; // 将choice设置为默认的personal

    // 1、页面加载完成以后，直接发送一个ajax请求，要到分页数据
    window.onload = function () {
        to_page_choice(1,choice); // 默认第一页
    }

    function to_page_choice(pn,choice){
        $.ajax({
            url:"${APP_PATH}/select_leave.do",
            data:"pn="+pn+"&choice="+choice,// 已经是默认第一页
            type:"get",
            success:function(result){
                // console.log(result);
                judgeMsg(result);
                // 1、解析并显示假条数据
                //console.log(result.data.list[0].needTime);
                build_leave_table(result);
                // 2、解析显示分页信息
                build_page_info(result);
                //console.log(result);
                // 3、解析显示分页条数据
                build_page_nav(result);

            }
        });
    };

    function judgeMsg(result){
        if(result.msg == "NEED_LOGIN"){
            alert("请先登录");
            window.open("${APP_PATH}/index.jsp",'_new');
            window.close();
        }
    };

    function build_leave_table(result){
        // 清空table表格
        $("#leave_table tbody").empty();

        var leave = result.data.list;
        $.each(leave,function(index,item){
            var usernameTd = $("<td></td>").append(item.username);
            var needTimeTd = $("<td></td>").append(item.needTime);
            var leaveTimeTd = $("<td></td>").append(item.leaveTime);
            var backTimeTd = $("<td></td>").append(item.backTime);
            var reasonTd = $("<td></td>").append(item.reason);
            var createTimeTd = $("<td></td>").append(item.createTime);
            var statusTd = $("<td></td>").append(item.status);
            /* 添加操作按钮 */
            var oprBtn = $("<button></button>").addClass("btn btn-success btn-sm operation-btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-cog"))
                .append("修改");

            // 为修改按钮添加一个自定义的属性,来表示当前员工的id
            oprBtn.attr("opr-id",item.id);

            var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash"))
                .append("删除");

            // 为修改按钮添加一个自定义的属性,来表示当前员工的id
            delBtn.attr("opr-id",item.id);

            var btnTd = $("<td></td>").append(oprBtn).append(delBtn);
            // append方法执行完成以后返回原来的元素
            $("<tr></tr>")
                .append(usernameTd)
                .append(needTimeTd)
                .append(leaveTimeTd)
                .append(backTimeTd)
                .append(reasonTd)
                .append(createTimeTd)
                .append(statusTd)
                .append(btnTd)
                .appendTo("#leave_table tbody");
        });
    }

    // 解析显示分页记录信息
    function build_page_info(result){
        // 有则清空
        $("#page_info_area").empty();

        var pageinfo = result.data;
        $("#page_info_area").append(" 当前"+pageinfo.pageNum+"页,总共"+pageinfo.pages+"页,共"+pageinfo.total+"条记录");
        totalRecord = pageinfo.total;
        currentPage = pageinfo.pageNum;
    }

    // 解析显示分页条
    function build_page_nav(result){
        // 有则清空
        $("#page_nav_area").empty();
        var ul = $("<ul></ul>").addClass("pagination");
        // 第一页
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
        // 前一页
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
        // 判断时候还有上一页，没有则disable
        if(result.data.hasPreviousPage == false){
            firstPageLi.addClass("disable");
            prePageLi.addClass("disable");
        }else{
            // 跳转第一页点击事件
            firstPageLi.click(function(){
                to_page_choice(1,choice);
            });
            // 跳转上一页点击事件
            prePageLi.click(function(){
                to_page_choice(result.data.pageNum - 1,choice);
            });
        }''
        // 下一页
        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        // 最后一页
        var LastPageLi = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
        // 判断时候还有下一页，没有则disable
        if(result.data.hasNextPage == false){
            nextPageLi.addClass("disable");
            LastPageLi.addClass("disable");
        }else{
            // 跳转下一页点击事件
            nextPageLi.click(function(){
                to_page_choice(result.data.pageNum + 1,choice);
            });
            // 跳转最后一页点击事件
            LastPageLi.click(function(){
                to_page_choice(result.data.pages,choice);
            });
        }

        ul.append(firstPageLi).append(prePageLi);
        $.each(result.data.navigatepageNums,function(index,item){
            var numLi =  $("<li></li>").append($("<a></a>").append(item));
            if(result.data.pageNum == item){
                numLi.addClass("active");
            }
            numLi.click(function(){
                to_page_choice(item,choice);
            })
            ul.append(numLi);
        })
        // 添加下一页和尾页的提示
        ul.append(nextPageLi).append(LastPageLi);
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page_nav_area");
    }

    // 选择查看所有
    $("#all-btn").click(function () {
        choice = $(this).attr("choice_btn");
        to_page_choice(1,choice);
    });

   // choice_personal_btn
    $("#personal-btn").click(function () {
        choice = $(this).attr("choice_personal_btn");
        to_page_choice(1,choice);
    });


    // 查看详情
    // 弹出模态框，实现修改
    $(document).on("click",".operation-btn",function(){
        // alert($(this).parents("tr").find("td:eq(1)").text());
        var leaveId = $(this).attr("opr-id");
        var applyName = $(this).parents("tr").find("td:eq(0)").text();
        var status = $(this).parents("tr").find("td:eq(6)").text();
        if(status == "untreated"){
            // 弹出模态框
            $("#leaveModal").modal({
                backdrop:"static"
            });
            $("#applyer").text(applyName);

            // 发送ajax请求
            $.ajax({
                url:"${APP_PATH}/show_leave.do",
                data:"lid="+leaveId,// 已经是默认第一页
                type:"get",
                success:function(result){
                        // console.log(result.data);
                    var item = result.data;
                    $("#update_btn").attr("leave_id",item.id);
                    $("#needTime").attr("value",item.needTime);
                    $("#leaveTime").attr("value",item.leaveTime);
                    $("#backTime").attr("value",item.backTime);
                    $("#reason").text(item.reason);
                }
            });

        }else{
            alert("该订单已经审核，不可修改");
        }
    });

    // 删除假条
    $(document).on("click",".delete_btn",function(){
        var leaveId = $(this).attr("opr-id");
        var status = $(this).parents("tr").find("td:eq(6)").text();
        if(status == "untreated"){
            if(confirm("还未经过审核，确认删除该假条吗？")){
                delectLeaveById(leaveId);
            }
            to_page_choice(1,choice);
        }else{
            if(confirm("已经审核，确认删除该假条吗？")){
                delectLeaveById(leaveId);
            }
            to_page_choice(1,choice);
        }
    });

    // 发送ajax请求删除假条
    function delectLeaveById(leaveId){
        $.ajax({
            url:"${APP_PATH}/delete_leave.do",
            data:"leaveId="+leaveId,
            type:"post",
            success:function(result){
                alert(result.msg);
            }
        });

    }

    // 取消修改假单
    $("#cancel_btn").click(function () {
        // 关闭模态框，返回第一页
        $("#leaveModal").modal("hide");
        to_page_choice(1,choice); // 默认第一页
    });

    // 确认修改假单
    $("#update_btn").click(function () {
        var leaveId = $("#update_btn").attr("leave_id");
        var needTime = $("#needTime").val();
        var leaveTime = $("#leaveTime").val();
        var backTime = $("#backTime").val();
        var reason = $("#reason").val();
        // 发送ajax请求
        $.ajax({
            url:"${APP_PATH}/update_leave.do",
            data:"leaveId="+leaveId+"&needTime="+needTime+"&leaveTime="+leaveTime+"&backTime="+backTime+"&reason="+reason,// 表单序列化
            type:"put",
            success:function(result){
                alert(result.msg);
                to_page_choice(1,choice); // 返回第一页
            }
        });
    });


</script>
</body>
</html>