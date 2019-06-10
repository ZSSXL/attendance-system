<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <title>no privileges</title>
</head>
<body>
        <h2 style="text-align: center;">YOU HAVE NO PRIVILEGES</h2>
        <h2 style="text-align: center;">${sessionScope.currentUser.username} 没有查看的权限</h2>
</body>
</html>