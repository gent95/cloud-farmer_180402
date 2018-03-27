<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>作业管理管理</title>
	<style>
		.noshow{ display: none;}
		select{
			background-color: rgba(255,255,255,0.10);
		}
		.op{
			background-color: rgba(0,0,0,0.40);
		}
	</style>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
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
                    if (ext && /^(jpg|png|bmp)$/.test(ext.toLowerCase())) {
                        this.setData({
                            'file': file
                        });
                    } else {
                        layer.msg("请上传格式为 jpg|png|bmp 的图片！");
                        return false;
                    }
                },
                onComplete: function (file, data) {
                    if (data.flag == '0') {
                        layer.msg(data.msg);
                        return;
                    }
                    $('#viewImg').attr('src', data.url);
                    $('#img').attr('value', data.url);
                }
            });
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
            $("#farmerlandId").change(function () {
                var fid=$(this).children('option:selected').val();
                if(fid!=-1){
                    $.ajax({
                        url:"${ctx}/farmerland/farmland/getFarmerById",
                        data:{
                            id:fid
                        },
                        dataType:"JSON",
                        success:function (result) {
                            $("#plant").html(result.info.plantVaritety);
                            $("[name='cropName']").val(result.info.plantVaritety);
                        }
                    });
                }else{
                    $("#plant").html(result.info.plantVaritety);
                    $("[name='cropName']").val(result.info.plantVaritety);
                }
            });
            $("#jobTypeId").change(function () {
                var name=$(this).children('option:selected').val();
               if(name!=-1){
                 /*  $("#zytype").parent().removeClass("noshow");*/
                    if(name=="浇水") {
                        $("#zytype").html("灌溉方式：");

                    }
                   else if(name=="施肥"){
                        $("#zytype").html("肥料名称：");
					}
					else{
                        $("#zytype").html("作业方式或名称");//.parent().addClass("noshow");
					}
                }
            });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/farmlandjob/farmlandJob/">作业管理列表</a></li>
		<li class="active"><a href="${ctx}/farmlandjob/farmlandJob/form?id=${farmlandJob.id}">作业管理<shiro:hasPermission name="farmlandjob:farmlandJob:edit">${not empty farmlandJob.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="farmlandjob:farmlandJob:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="farmlandJob" action="${ctx}/farmlandjob/farmlandJob/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">作业名称：</label>
			<div class="controls">
			<%--	<form:input path="jobTypeId" htmlEscape="false" maxlength="255" class="input-xlarge "/>--%>
				<select name="jobTypeId" id="jobTypeId">
					<option class="op" value="-1">——请选择——</option>
					<c:forEach items="${lists}" var="job" >
						<option class="op" value="${job.name}" <c:if test="${job.name == farmlandJob.jobTypeId}">selected="selected"</c:if> >${job.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业位置：</label>
			<div class="controls">
			<%--	<form:input path="farmerlandId" htmlEscape="false" maxlength="64" class="input-xlarge "/>--%>
				<select name="farmerlandId" id="farmerlandId">
					<option class="op" value="-1">——请选择——</option>
					<c:forEach items="${listFarm}" var="fa" >
						<option class="op" value="${fa.id}" <c:if test="${fa.id == farmlandJob.farmerlandId}">selected="selected"</c:if> >${fa.alias}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作物名称：</label>
			<div class="controls">
				<form:hidden path="cropName" htmlEscape="false" maxlength="255" name="cropName" class="input-xlarge "/>
				<span id="plant" name="plant">${farmlandJob.cropName}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业人员：</label>
			<div class="controls">
				<form:input path="workerId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" id="zytype">作业方式或名称：</label>
			<div class="controls">
				<form:input path="typeOrName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业量：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作业图片：</label>
			<div class="controls">
					<%--<form:hidden id="nameImage" path="img" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>--%>
				<img style="width: 200px;height: 200px" src="${farmlandJob.img}" id="viewImg">
				<input type="button" class="btn btn-primary" id="uploadPhoto" value="修改">
				<input  name="img"  id="img" type="hidden" value="${farmlandJob.img}">
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">审核是否通过 1:通过 0:不通过 2已完成：</label>
			<div class="controls">
				<form:input path="flag" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="500" class="input-xlarge" cssStyle="height: 100px;"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="farmlandjob:farmlandJob:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>