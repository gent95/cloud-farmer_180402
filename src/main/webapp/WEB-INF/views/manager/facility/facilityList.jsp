<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>气象站设备管理</title>
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
		<li class="active"><a href="${ctx}/facility/facility/">气象站列表</a></li>
		<shiro:hasPermission name="facility:facility:edit"><li><a href="${ctx}/facility/facility/form">气象站添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="facility" action="${ctx}/facility/facility/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>农场编号：</label>
				<form:input path="farmerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>气象站</th>
				<th>所属农场</th>
				<th>录入时间</th>
				<shiro:hasPermission name="facility:facility:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="facility">
			<tr>
				<td><a href="${ctx}/facility/facility/form?id=${facility.id}">
					${facility.id}
				</a></td>
				<td>
					鹤泉村站
					<!-- ${facility.name} -->
				</td>
				<td>
					鹤泉村试点
					<!-- ${facility.farmerId} -->
				</td>
				<td>
					<fmt:formatDate value="${facility.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="facility:facility:edit"><td>
    				<div class="two"><a href="${ctx}/facility/facility/form?id=${facility.id}">修改</a></div>
					<div class="one"><a href="${ctx}/facility/facility/delete?id=${facility.id}" onclick="return confirmx('确认要删除该气象站设备吗？', this.href)">删除</a></div>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>