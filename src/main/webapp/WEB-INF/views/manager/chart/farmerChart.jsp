<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>农场详情</title>
    <meta name="decorator" content="default"/>
    <meta name="referrer" content="always">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <script type="text/javascript"></script>

</head>
<body>

${fns:getUser().name}
${fns:getUser().mobile}
<c:forEach items="${plants}" var="plants">
    <div>${plants.name}</div>
</c:forEach>



</body>
</html>