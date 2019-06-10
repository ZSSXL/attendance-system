<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>假单审核</title>
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
                <div class="row">
                        <div class="col-md-12">
                                <h2 style="text-align: center">假条审核</h2>
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
                                                <th>职位</th>
                                                <th>假条状态</th>
                                                <th>审核</th>
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

    // 1、页面加载完成以后，直接发送一个ajax请求，要到分页数据
    window.onload = function () {
       to_page(1); // 默认第一页
    }


    function to_page(pn){
        $.ajax({
            url:"${APP_PATH}/leave_list.do",
            data:"pn="+pn,// 已经是默认第一页
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
            var roleTd = $("<td></td>").append(item.role);
            var statusTd = $("<td></td>").append("未审核");
            /* 添加两个操作按钮 */
            var passBtn = $("<button></button>").addClass("btn btn-success btn-sm pass-btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-ok"))
                .append("同意");
            // 为编辑按钮添加一个自定义的属性,来表示当前员工的id
            passBtn.attr("pass-id",item.id);
            passBtn.attr("pass-operation","pass");

            var dispassBtn = $("<button></button>").addClass("btn btn-danger btn-sm dispass-btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-remove"))
                .append("不同意");
            // 为删除按钮添加一个自定义的属性，来表示当前员工的id
            dispassBtn.attr("dispass-id",item.id);
            dispassBtn.attr("dispass-operation","dispass");

            var btnTd = $("<td></td>").append(passBtn).append(dispassBtn);
            // append方法执行完成以后返回原来的元素
            $("<tr></tr>")
                .append(usernameTd)
                .append(needTimeTd)
                .append(leaveTimeTd)
                .append(backTimeTd)
                .append(reasonTd)
                .append(createTimeTd)
                .append(roleTd)
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
                to_page(1);
            });
            // 跳转上一页点击事件
            prePageLi.click(function(){
                to_page(result.data.pageNum - 1);
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
                to_page(result.data.pageNum + 1);
            });
            // 跳转最后一页点击事件
            LastPageLi.click(function(){
                to_page(result.data.pages);
            });
        }

        ul.append(firstPageLi).append(prePageLi);
        $.each(result.data.navigatepageNums,function(index,item){
            var numLi =  $("<li></li>").append($("<a></a>").append(item));
            if(result.data.pageNum == item){
                numLi.addClass("active");
            }
            numLi.click(function(){
                to_page(item);
            })
            ul.append(numLi);
        })
        // 添加下一页和尾页的提示
        ul.append(nextPageLi).append(LastPageLi);
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page_nav_area");
    }

    // 请假通过
    $(document).on("click",".pass-btn",function(){
        // alert($(this).parents("tr").find("td:eq(1)").text());
        var lid = $(this).attr("pass-id");
        var operation = $(this).attr("pass-operation");
        if(confirm("确认审核通过吗？")){
            // 确认，返送ajax通过请求
           $.ajax({
                url:"${APP_PATH}/pass_dispass.do",
                type:"POST",
                data:"lid="+lid+"&type="+operation,
                success:function(result){
                    to_page(currentPage);
                }

            });
        }
    });

    // 请假不通过
    $(document).on("click",".dispass-btn",function(){
        // alert($(this).parents("tr").find("td:eq(1)").text());
        var lid = $(this).attr("dispass-id");
        var operation = $(this).attr("dispass-operation");
        if(confirm("确认审核不通过吗？")){
            // 确认，返送ajax不通过请求
            $.ajax({
                url:"${APP_PATH}/pass_dispass.do",
                type:"POST",
                data:"lid="+lid+"&type="+operation,
                success:function(result){
                    to_page(currentPage);
                }
            });
        }
    });
</script>
</body>
</html>