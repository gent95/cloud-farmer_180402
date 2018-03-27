<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1,user-scalable=no">
    <title>登录</title>
    <link rel="stylesheet" href="${ctxStatic}/css/cssreset-min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/login.css"/>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/layer/layer.js" type="text/javascript"></script>
    <script type="text/javascript">
        function loginOut() {
            $.get("${ctx}/logout", function () {
                sessionid = '';
            });
            layer.msg("正在登录...")
            setTimeout(function () {
                $("#loginForm").submit();
            }, 2000);

        }
        $(document).ready(function () {
//                $("#login").click(function () {
//
////
//                })
            /*    $("#loginForm").validate({
             messages: {
             username: {required: "请填写用户名."},password: {required: "请填写密码."}
             },
             errorLabelContainer: "#messageBox",
             errorPlacement: function(error, element) {
             error.appendTo($("#loginError").parent());
             }
             });*/
        });
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
            alert('未登录或登录超时。请重新登录，谢谢！');
           // layer.msg("账号已在异地登录！！请注意！！login+login+login");
            top.location = "${ctx}";
        }
    </script>
	<script type="text/javascript" src="http://cdn.goeasy.io/goeasy.js"></script>
	<script type="text/javascript">
      /*  var goEasy = new GoEasy({appkey: 'BC-d14d4984a76247e99820ceb5f3ac219c'});
        goEasy.subscribe({
            channel: '通知',
            onMessage: function(message){
                alert(message.content);
            }
        });*/
	</script>
</head>
<body class="body" onkeydown="if(event.keyCode==13){loginOut()}">
<!--[if lte IE 6]><br/>
<div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a>
    <h4>温馨提示：</h4>
    <p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的
        Chrome、Firefox、Safari 等。</p></div><![endif]-->
<div class="header">
    <div class="he_left"><img src="${ctxStatic}/images/no-3.png"/></div>
    <div class="he_rigth"><a href="${adminPath}">登录</a> | <a href="${ctx}/reg/optionRole">注册</a></div>
    <div class="clearfix"></div>
</div>
<div class="header2">
    <div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}">
        <label id="loginError" class="error">${message}</label>
    </div>
</div>
<div class="content fixed-center">
    <form id="loginForm" name="form1" class="form-signin" action="${ctx}/login" method="post">
        <div class="texts">
            Welcome
        </div>
        <div class="username1 cell">
            <img src="${ctxStatic}/images/login/user.png"/>
            <input type="text" class="input" name="username" id="username" value="${user.loginName}"/>
        </div>
        <div class="password1 cell">
            <img src="${ctxStatic}/images/login/pwd.png"/>
            <input type="password" class="input" id="password" name="password"/>
        </div>
        <div class="jizhu">
            <a href="#" class="oneo">
                <input type="checkbox" id="rememberMe"
                       name="rememberMe" ${rememberMe ? 'checked' : ''}/>
                <span>记住密码</span>
            </a>
            <a href="${ctx}/reg/forgetPassword" class="floatR">忘记密码?</a>
        </div>
        <%--<div class="btn">
            <div onclick="submitform()"><a>登录</a></div>
        </div>--%>
        <input onclick="loginOut()" type="button" class="btn" value="登录"/>
    </form>
</div>
<div class="footer">
 <%--   Copyright &copy;大唐移动通信设备有限公司--%>
</div>
<%--<div class="wenjian">
	<a><img src="${ctxStatic}/images/login/tianqi.png"/></a>
	<a><img src="${ctxStatic}/images/login/jianjie.png"/></a>
	<a style="margin-right: 0px;"><img src="${ctxStatic}/images/login/yiwen.png"/></a>
</div>--%>
</body>
</html>
