<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>安卓版本管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="/static/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/static/jctl/jcAjaxUpload.js"></script>
	<script type="text/javascript" src="/static/layer/layer.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

         new AjaxUpload('#uploadPhoto', {
                action: '/admin/uploadFile',
                name: 'file',
                responseType: 'json',
                onSubmit: function (file, ext) {
                        this.setData({
                            'file': file
                        });
                },
                onComplete: function (file, data) {
                    if (data.flag == '0') {
                        layer.msg(data.msg);
                        return;
                    }
                    $('#con').text("");
                    $('#con').text(data.url);
                    $("#url").val(data.url);
                }
            });
	    $("#uploadPhoto").click(function () {
				var files=$("[name='file']").val();
				if(files!=null&&files!=""){
                  $.ajax({
                        url: "/admin/uploadFile",
                        data: {
                            file: files
                        },
                        dataType: "json",
                        success: function (data) {
                            if (data.flag == '0') {
                                $("#con").text("上传失败");
                                layer.msg(data.msg);
                                return;
                            }else if(data.flag=='1'){
                                $("#con").text("上传成功");
                            }
                        }
                    })
				}
				else {
				    layer.msg("请选择文件！");
				}
            });
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
		<li><a href="${ctx}/version/appVersion/">安卓版本列表</a></li>
		<li class="active"><a href="${ctx}/version/appVersion/form?id=${appVersion.id}">安卓版本<shiro:hasPermission name="version:appVersion:edit">${not empty appVersion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="version:appVersion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="appVersion" action="${ctx}/version/appVersion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">内部版本：</label>
			<div class="controls">
				<form:input path="inVersion" htmlEscape="false" maxlength="126" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外部版本：</label>
			<div class="controls">
				<form:input path="outVersion" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">允许下载：</label>
			<div class="controls">
				<form:radiobuttons path="status" checked="checked"  items="${fns:getDictList('donw_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下载地址：</label>
			<div class="controls">
				<input type="hidden" value="${appVersion.url}" id="url" name="url" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<span id="con">${appVersion.url}</span>
				<input type="button" class="btn btn-primary" id="uploadPhoto" value="上传">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自动更新：</label>
			<div class="controls">
				<form:radiobuttons path="autoUpdate" checked="checked"  items="${fns:getDictList('donw_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="version:appVersion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>