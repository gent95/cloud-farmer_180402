<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信记录管理</title>
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
		<li class="active"><a href="${ctx}/smshistory/smsHistory/">短信记录列表</a></li>
		<shiro:hasPermission name="smshistory:smsHistory:edit"><li><a href="${ctx}/smshistory/smsHistory/form">短信记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="smsHistory" action="${ctx}/smshistory/smsHistory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>发送电话：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="16" class="input-medium"/>
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
					<th>序号</th>
					<th>请求人</th>
					<th>请求IP</th>
					<th>发送电话</th>
					<th>详情</th>
					<th>创建时间</th>
					<th>update_date</th>
					<shiro:hasPermission name="smshistory:smsHistory:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="smsHistory">
				<tr>
					<td><a href="${ctx}/smshistory/smsHistory/form?id=${smsHistory.id}">
						${smsHistory.id}
					</a></td>
					<td>
						${smsHistory.user.name}
					</td>
					<td>
						${smsHistory.ip}
					</td>
					<td>
						${smsHistory.mobile}
					</td>
					<td>
						${smsHistory.message}
					</td>
					<td>
						<fmt:formatDate value="${smsHistory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<fmt:formatDate value="${smsHistory.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<shiro:hasPermission name="smshistory:smsHistory:edit"><td>
						<a href="${ctx}/smshistory/smsHistory/form?id=${smsHistory.id}">修改</a>
						<a href="${ctx}/smshistory/smsHistory/delete?id=${smsHistory.id}" onclick="return confirmx('确认要删除该短信记录吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>