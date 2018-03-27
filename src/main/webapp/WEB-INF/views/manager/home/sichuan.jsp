<%--
  Created by IntelliJ IDEA.
  User: gent
  Date: 2017/5/23
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title></title>
    <script src="${ctxStatic}/js/map/sichuan.json"></script>
    <script src="${ctxStatic}js/map/sichuan.js"></script>
</head>

<body>
<div id="main" style="width:600px; height: 800px;"></div>
<script>
//    var ecConfig = require('js/echarts/');
    var chart = echarts.init(document.getElementById('main'));
    chart.setOption({
        series: [{
            type: 'map',
            map: '四川'
        }]
    });

    $.get('json/sichuan.json', function(chinaJson) {
        echarts.registerMap('china', chinaJson);
        var chart = echarts.init(document.getElementById('main'));
        chart.setOption({
            series: [{
                type: 'map',
                map: 'china'
            }]
        });
    });
</script>
</body>

</html>
