<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>员工考勤记录</title>
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
                        <h2 style="text-align: center">员工考勤记录</h2>
                </div>
        </div>
        <!-- 表格数据 -->
        <div class="row">
                <div class="col-md-12">
                        <table class="table table-hover" id="leave_table">
                                <thead>
                                <tr>
                                        <th>用户名</th>
                                        <th>签到时间</th>
                                        <th>签退时间</th>
                                        <th>打卡日期</th>
                                        <th>职位</th>
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
                <div class="col-md-8 " id="page_info_area">

                </div>
                <!-- 分页条信息 -->
                <div class="col-md-4" id="page_nav_area">

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
            url:"${APP_PATH}/select_history.do",
            data:"choice=all&pn="+pn, // 针对查询个人
            type:"get",
            success:function(result){
                //console.log(result);
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
         // 没有登录
        if(result.status == 10){
            alert("请先登录");
            window.open("${APP_PATH}/index.jsp",'_new');
            window.close();
        }
        // 没有权限
        if(result.status == 2){
            alert("你没有权限");
            window.location.href="${APP_PATH}/jsp/forbid.jsp";
        }
    };

    function build_leave_table(result){
        // 清空table表格
        $("#leave_table tbody").empty();

        var leave = result.data.list;
        $.each(leave,function(index,item){
            var usernameTd = $("<td></td>").append(item.username);
            var needTimeTd = $("<td></td>").append(item.startWork);
            var leaveTimeTd = $("<td></td>").append(item.stopWork);
            var backTimeTd = $("<td></td>").append(item.createTime);
            var reasonTd = $("<td></td>").append(item.role);

            // append方法执行完成以后返回原来的元素
            $("<tr></tr>")
                .append(usernameTd)
                .append(needTimeTd)
                .append(leaveTimeTd)
                .append(backTimeTd)
                .append(reasonTd)
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
</script>
</body>
</html>