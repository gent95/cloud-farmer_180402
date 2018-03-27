<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加工记录管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    var r=$("#totalWeight").val();
                    var  t=$("#singleWeight").val();
                    if(isNaN(r)){
                        $("#totalWeight").after("<label for='totalWeight' class='error'>请填写数字</label>");
                    }
                    if(isNaN(t)){
                        $("#singleWeight").after("<label for='singleWeight' class='error'>请填写数字</label>");
                    }
                    if(!isNaN(r)&&!isNaN(t)) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
            $("#farmlandId").change(function () {
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
                            $("[name='plantName']").val(result.info.plantVaritety);
                        }
                    });
				}else{
                    $("#plant").html(result.info.plantVaritety);
                    $("[name='plantName']").val(result.info.plantVaritety);
				}
            });
		});
        function  f1() {
            var r=$("#totalWeight").val();
            if(isNaN(r)){
                $("#totalWeight").after("<label for='totalWeight' class='error'>请填写数字</label>");
            }
        }

        function  f2() {
            var r=$("#singleWeight").val();
            if(isNaN(r)){
                $("#singleWeight").after("<label for='singleWeight' class='error'>请填写数字</label>");
            }
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/machine/machineRecord/">加工记录列表</a></li>
		<li class="active"><a href="${ctx}/machine/machineRecord/form?id=${machineRecord.id}">加工记录<shiro:hasPermission name="machine:machineRecord:edit">${not empty machineRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="machine:machineRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="machineRecord" action="${ctx}/machine/machineRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
	<%--	<div class="control-group">
			<label class="control-label">农场编号：</label>
			<div class="controls">
				<form:input path="farmerId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">所属农田：</label>
			<div class="controls">
				<select name="farmlandId" id="farmlandId">
					<option value="-1">——请选择——</option>
					<c:forEach items="${lists}" var="fa" >
						<option value="${fa.id}" <c:if test="${fa.id == machineRecord.farmlandId}">selected="selected"</c:if> >${fa.alias}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物种名称：</label>
			<div class="controls">
				<form:hidden path="plantName" htmlEscape="false" maxlength="64" name="plantName" class="input-xlarge"/>
				<span id="plant" name="plant">${machineRecord.plantName}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加工方式：</label>
			<div class="controls">
				<form:input path="machineMode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加工总重量（kg）：</label>
			<div class="controls">
				<form:input path="totalWeight" id="totalWeight" htmlEscape="false" maxlength="64" onblur="f1()" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单件重量（kg）：</label>
			<div class="controls">
				<form:input path="singleWeight" id="singleWeight" htmlEscape="false" maxlength="64" onblur="f2()" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人：</label>
			<div class="controls">
				<form:input path="admin" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加工时间：</label>
			<div class="controls">
				<input name="machineTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${machineRecord.machineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="255" class="input-xlarge " cssStyle="height: 100px;"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">所属用户：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${machineRecord.user.id}" labelName="user.name" labelValue="${machineRecord.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="machine:machineRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>