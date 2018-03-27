<%--
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
    <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.js"></script>
    <style>
        html{background: none;}
        .middle{
            position: absolute; top: 0; right: 0; bottom: 0; left: 0;
            margin: auto;
        }
    </style>
</head>
<body >
    <div id="one" class="one" style="width: 100%; height: 280px;background-color: white;"></div>
    <script src="${ctxStatic}/js/map/map.js"></script>
    <script type="text/javascript">
        $(function(){
            $.ajax({
                url:"${ctx}/grow/goToOne?parms=成都市",
                type:"get"
            });
            $.ajax({
                url:"${ctx}/grow/getData",
                type:"get",
                dataType:"json",
                success:function(plantSes){
                    var yValue=new Array();
                    for(i=0;i<plantSes.length;i++){
                        yValue[i]=plantSes[i];
                    }
                    getMap1(yValue);
                }
            });
        });
    </script>
</body>
</html>
