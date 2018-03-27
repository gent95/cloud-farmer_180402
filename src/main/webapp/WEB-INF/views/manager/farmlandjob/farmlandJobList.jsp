<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>作业管理管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/farmlandjob/farmlandJob/">作业管理列表</a></li>
		<shiro:hasPermission name="farmlandjob:farmlandJob:edit"><li><a href="${ctx}/farmlandjob/farmlandJob/form">作业管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="farmlandJob" action="${ctx}/farmlandjob/farmlandJob/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>作业名称：</label>
				<form:input path="jobTypeId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>作物名称：</label>
				<form:input path="cropName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div class="table-responsive">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>作业名称</th>
					<th>作业位置</th>
					<th>作物名称</th>
					<th>作业人员</th>
					<th>作业时间</th>
					<th>作业方式或名称</th>
					<th>作业量</th>
					<th>作业图片</th>
					<th>备注</th>
					<shiro:hasPermission name="farmlandjob:farmlandJob:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="farmlandJob">
				<tr>
					<td>
						${farmlandJob.jobTypeId}
					</td>
					<td>
						${farmlandJob.farmerlandId}
					</td>
					<td>
						${farmlandJob.cropName}
					</td>
					<td>
						${farmlandJob.workerId}
					</td>
					<td>
						<fmt:formatDate value="${farmlandJob.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						${farmlandJob.typeOrName}
					</td>
					<td>
						${farmlandJob.amount}
					</td>
					<td>
						<img src="${farmlandJob.img}" style="height: 50px; width: 80px;"/>
					</td>
					<td>
						${farmlandJob.remark}
					</td>
					<shiro:hasPermission name="farmlandjob:farmlandJob:edit"><td>
						<div class="two"> <a href="${ctx}/farmlandjob/farmlandJob/form?id=${farmlandJob.id}">修改</a></div>
						<div class="one"><a href="${ctx}/farmlandjob/farmlandJob/delete?id=${farmlandJob.id}" onclick="return confirmx('确认要删除该作业管理吗？', this.href)">删除</a></div>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>