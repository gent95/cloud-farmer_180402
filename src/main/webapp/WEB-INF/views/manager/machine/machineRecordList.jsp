<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加工记录管理</title>
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
		function alert11(){
            layer.open({
                type: 2,
                title: false,
                area: ['100%', '100%'],
                shade: 0.8,
                closeBtn: 0,
                shadeClose: true,
                content: 'http://60.255.50.139:8087/cas/login'
            });
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li class="active"><a onclick="alert11()" href="javascript:void(0)">测试</a></li>--%>
		<li class="active"><a href="${ctx}/machine/machineRecord/">加工记录列表</a></li>
		<shiro:hasPermission name="machine:machineRecord:edit"><li><a href="${ctx}/machine/machineRecord/form">加工记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="machineRecord" action="${ctx}/machine/machineRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>加工方式：</label>
				<form:input path="machineMode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>物种名称：</label>
				<form:input path="plantName" htmlEscape="false" maxlength="64" class="input-medium"/>
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
					<th>物种名称</th>
					<th>加工方式</th>
					<th>加工总重量</th>
					<th>单件重量</th>
					<th>负责人</th>
					<th>加工时间</th>
					<th>备注</th>
					<shiro:hasPermission name="machine:machineRecord:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="machineRecord">
				<tr>
					<td>
						${machineRecord.plantName}
					</td>
					<td>
						${machineRecord.machineMode}
					</td>
					<td>
						${machineRecord.totalWeight}
					</td>
					<td>
						${machineRecord.singleWeight}
					</td>
					<td>
						${machineRecord.admin}
					</td>
					<td>
						<fmt:formatDate value="${machineRecord.machineTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						${machineRecord.remark}
					</td>
					<shiro:hasPermission name="machine:machineRecord:edit"><td>
						<div class="two"><a href="${ctx}/machine/machineRecord/form?id=${machineRecord.id}">修改</a></div>
						<div class="one"><a href="${ctx}/machine/machineRecord/delete?id=${machineRecord.id}" onclick="return confirmx('确认要删除该加工记录吗？', this.href)">删除</a></div>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>