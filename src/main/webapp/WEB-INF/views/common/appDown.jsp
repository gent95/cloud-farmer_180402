<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>APP下载</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
</head>
<body>
<div class="flex-center">
    <h2 align="center">请使用手机浏览器扫描下载</h2>
    <div class="appcode">
        <div id="qrcode" ></div>
    </div>
    <h6 align="center">版本:${version.outVersion} </h6>
</div>

<script src="/static/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/static/jquery-qrcode/jquery.qrcode.min.js"></script>
<script>
    jQuery(function(){
        jQuery('#qrcode').qrcode("${version.url}");
    })
</script>

</body>
</html>