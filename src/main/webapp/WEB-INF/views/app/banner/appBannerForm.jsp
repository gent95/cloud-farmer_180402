<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>APP轮播管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>

	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="/static/layui/layui.js" charset="utf-8"></script>
	<link rel="stylesheet" href="/static/layui/css/layui-bootstrap.css"  media="all">
	<link rel="stylesheet" href="${ctxStatic}/layui/css/global.css"  media="all">
	<script>
        host = window.location.host;
        var static = host+"/static";
        layui.use('upload', function () {
            layui.upload({
                url: '/admin/uploadFile'
                , elem: '#img_file'
                , method: 'post'
                , success: function (res) {
                    if (res.flag == 0) {
                        alert("上传失败");
                    } else {
                        img.src = res.url;
                        $(".imgPath").val(res.url);
                    }
                }
            });
        });
        var _htm = "<img id='img' src='"+static+"/images/default/default.png' style='border-radius: 100%'> <div class='img_btn' > <input type='file' name='file' id='img_file'><input type='hidden'  class='imgPath' name='path'  > </div>";
        $(function () {
            $(".img_upload").html(_htm);
            $("#img").attr("src",$(".default-pic").attr("src"));
            $(".imgPath").val($(".default-pic").attr("src"));

        })
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/banner/appBanner/">APP轮播列表</a></li>
		<li class="active"><a href="${ctx}/banner/appBanner/form?id=${appBanner.id}">APP轮播<shiro:hasPermission name="banner:appBanner:edit">${not empty appBanner.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="banner:appBanner:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appBanner" action="${ctx}/banner/appBanner/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">url：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<div class="img_upload">
				</div>
				<img style="display: none" class="default-pic" src="${appBanner.path}">
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="banner:appBanner:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>