<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>别名管理</title>
	<meta name="decorator" content="default"/>
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/nodepropertyname/nodePropertyName?nodeId=${nodeId}">别名列表</a></li>
		<li class="active"><a href="${ctx}/nodepropertyname/nodePropertyName/form?id=${nodePropertyName.id}">别名<shiro:hasPermission name="nodepropertyname:nodePropertyName:edit">${not empty nodePropertyName.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="nodepropertyname:nodePropertyName:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="nodePropertyName" action="${ctx}/nodepropertyname/nodePropertyName/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="nodeId" value="${nodeId}">
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" htmlEscape="false" maxlength="1" class="input-xlarge ">
					<form:option value="1">原一号采集点</form:option>
					<form:option value="2">原二号采集点</form:option>
					<form:option value="3">原三号采集点</form:option>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">重命名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="nodepropertyname:nodePropertyName:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>