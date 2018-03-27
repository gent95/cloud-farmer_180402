<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>作业类型管理</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/jobtype/jobType/">作业类型列表</a></li>
    <%--<shiro:hasPermission name="jobtype:jobType:edit">--%>
        <li><a href="${ctx}/jobtype/jobType/form">作业类型添加</a></li>
    <%--</shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="jobType" action="${ctx}/jobtype/jobType/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
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
            <th>名称</th>
            <th>图标</th>
                <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="jobType">
            <tr>
                <td><a href="${ctx}/jobtype/jobType/form?id=${jobType.id}">
                        ${jobType.name}
                </a></td>
                <td>
                      <img src="  ${jobType.icon}" style="width: 40px;width: 40px">
                </td>
                    <td style="width: 20%">
                        <div class="two"><a href="${ctx}/jobtype/jobType/form?id=${jobType.id}">修改</a></div>
                        <div class="one"><a href="${ctx}/jobtype/jobType/delete?id=${jobType.id}"
                           onclick="return confirmx('确认要删除该作业类型吗？', this.href)">删除</a></div>
                    </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="pagination">${page}</div>
</body>
</html>