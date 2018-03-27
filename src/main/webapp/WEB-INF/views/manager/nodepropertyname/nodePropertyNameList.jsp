<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>别名管理</title>
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
		<li class="active"><a href="${ctx}/nodepropertyname/nodePropertyName?nodeId=${nodeId}">别名列表</a></li>
		<shiro:hasPermission name="nodepropertyname:nodePropertyName:edit"><li>
			<a href="${ctx}/nodepropertyname/nodePropertyName/form?nodeId=${nodeId}">别名添加</a>
		</li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>显示名</th>
				<th>类型</th>
				<shiro:hasPermission name="nodepropertyname:nodePropertyName:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="nodePropertyName">
			<tr>
				<td>
					${nodePropertyName.name}
				</td>
				<td>
					<c:if test="${nodePropertyName.type eq 1}">
						原1号采集点
					</c:if>
					<c:if test="${nodePropertyName.type eq 2}">
						原2号采集点
					</c:if>
					<c:if test="${nodePropertyName.type eq 3}">
						原3号采集点
					</c:if>
				</td >
				<shiro:hasPermission name="nodepropertyname:nodePropertyName:edit">
					<td style="width: 25%">
    				<%--<a href="${ctx}/nodepropertyname/nodePropertyName/form?id=${nodePropertyName.id}&nodeId=${nodeId}">修改</a>--%>
					<a href="${ctx}/nodepropertyname/nodePropertyName/delete?id=${nodePropertyName.id}" onclick="return confirmx('确认要删除该别名吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>