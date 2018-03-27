<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>APP轮播管理</title>
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
		<li class="active"><a href="${ctx}/banner/appBanner/">APP轮播列表</a></li>
		<shiro:hasPermission name="banner:appBanner:edit"><li><a href="${ctx}/banner/appBanner/form">APP轮播添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="appBanner" action="${ctx}/banner/appBanner/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div class="table-responsive">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>序号</th>
					<th>跳转</th>
					<th>路径</th>
					<shiro:hasPermission name="banner:appBanner:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="appBanner">
				<tr>
					<td><a href="${ctx}/banner/appBanner/form?id=${appBanner.id}">
						${appBanner.id}
					</a></td>
					<td>
						${appBanner.url}
					</td>
					<td>
						<img src="${appBanner.path}">
					</td>
					<shiro:hasPermission name="banner:appBanner:edit"><td>
						<a href="${ctx}/banner/appBanner/form?id=${appBanner.id}">修改</a>
						<a href="${ctx}/banner/appBanner/delete?id=${appBanner.id}" onclick="return confirmx('确认要删除该APP轮播吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>