<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>历史命令管理</title>
	<meta name="decorator" content="default"/>
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
		<li class="active"><a href="${ctx}/nodehistory/nodeHistory/">历史命令列表</a></li>
		<shiro:hasPermission name="nodehistory:nodeHistory:edit"><li><a href="${ctx}/nodehistory/nodeHistory/form">历史命令添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="nodeHistory" action="${ctx}/nodehistory/nodeHistory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>中继：</label>
				<form:input path="relayId" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>节点：</label>
				<form:input path="nodeId" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>中继</th>
				<th>节点</th>
				<th>命令详情</th>
				<th>添加时间</th>
				<th>添加人</th>
				<th>修改时间</th>
				<th>修改人</th>
				<shiro:hasPermission name="nodehistory:nodeHistory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="nodeHistory">
			<tr>
				<td><a href="${ctx}/nodehistory/nodeHistory/form?id=${nodeHistory.id}">
					${nodeHistory.id}
				</a></td>
				<td>
					${nodeHistory.relayId}
				</td>
				<td>
					${nodeHistory.nodeId}
				</td>
				<td>
					${nodeHistory.orders}
				</td>
				<td>
					<fmt:formatDate value="${nodeHistory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${nodeHistory.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${nodeHistory.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${nodeHistory.updateBy.id}
				</td>
				<shiro:hasPermission name="nodehistory:nodeHistory:edit"><td>
    				<a href="${ctx}/nodehistory/nodeHistory/form?id=${nodeHistory.id}">修改</a>
					<a href="${ctx}/nodehistory/nodeHistory/delete?id=${nodeHistory.id}" onclick="return confirmx('确认要删除该历史命令吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>