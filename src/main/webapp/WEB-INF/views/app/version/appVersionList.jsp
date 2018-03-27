<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>安卓版本管理</title>
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
		<li class="active"><a href="${ctx}/version/appVersion/">安卓版本列表</a></li>
		<shiro:hasPermission name="version:appVersion:edit"><li><a href="${ctx}/version/appVersion/form">安卓版本添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appVersion" action="${ctx}/version/appVersion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<div class="table-responsive">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>序号</th>
					<th>内部版本</th>
					<th>外部版本</th>
					<th>版本备注</th>
					<th>允许更新</th>
					<th>下载地址</th>
					<th>自动更新</th>
					<th>添加时间</th>
					<shiro:hasPermission name="version:appVersion:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="appVersion">
				<tr>
					<td><a href="${ctx}/version/appVersion/form?id=${appVersion.id}">
						${appVersion.id}
					</a></td>
					<td>
						${appVersion.inVersion}
					</td>
					<td>
						${appVersion.outVersion}
					</td>
					<td>
						${appVersion.remark}
					</td>
					<td>
						${fns:getDictLabel(appVersion.status, 'donw_status', '不可以')}
					</td>
					<td>
						<a href="${appVersion.url}">下载</a>
					</td>
					<td>
						${fns:getDictLabel(appVersion.autoUpdate, 'donw_status', '不可以')}
					</td>
					<td>
						<fmt:formatDate value="${appVersion.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<shiro:hasPermission name="version:appVersion:edit"><td>
						<a href="${ctx}/version/appVersion/form?id=${appVersion.id}">修改</a>
						<a href="${ctx}/version/appVersion/delete?id=${appVersion.id}" onclick="return confirmx('确认要删除该安卓版本吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>