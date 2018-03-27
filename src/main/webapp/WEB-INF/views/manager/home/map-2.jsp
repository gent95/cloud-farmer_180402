map-1.jsp<%--
  Created by IntelliJ IDEA.
  User: gent
  Date: 2017/5/23
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="${ctxStatic}/css/cssreset-min.css">
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <style type="text/css">
        html{background: none;}
        #name{
            color: white;
            position: absolute;
            top: 2px;
            font-size: 14px;
        }
        .middle{
            position: absolute; top: 0; right: 0; bottom: 0; left: 0;
            margin: auto;
        }
    </style>
</head>
<body >
<div id="name">
    川芎价格走势一览图
</div>

    <div id="two" class="two" style="width: 100%; height: 280px; padding: 5px;"></div>
    <script src="${ctxStatic}/js/map/map.js"></script>
    <script type="text/javascript">
        getMap2();
    </script>
</body>
</html>
