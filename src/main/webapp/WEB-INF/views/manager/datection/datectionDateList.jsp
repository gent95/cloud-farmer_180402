datectionDateList.jsp<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备数据管理</title>
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
		<li class="active"><a href="${ctx}/datection/datectionDate/">设备数据列表</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>气象站</th>
				<th>风速</th>
				<th>雨量</th>
				<th>风向</th>
				<th>大气温度</th>
				<th>大气湿度</th>
				<th>数字气压</th>
				<th>照度</th>
				<th>检测时间</th>
				<shiro:hasPermission name="datection:datectionDate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="datectionDate">
			<tr>
				<td><a href="${ctx}/datection/datectionDate/form?id=${datectionDate.id}">
					${datectionDate.id}
				</a></td>
				<td>
					鹤泉村站
					<!--  ${datectionDate.facId}-->
				</td>
				<td>
					${datectionDate.windSpeed}
				</td>
				<td>
					${datectionDate.airTemperature}
				</td>
				<td>
					${datectionDate.humidity}
				</td>
				<td>
					${datectionDate.rainV}
				</td>
				<td>
					${datectionDate.radiate}
				</td>
				<td>
					${datectionDate.windDirection}
				</td>
				<td>
					${datectionDate.evaporation}
				</td>
				<td>
					<fmt:formatDate value="${datectionDate.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="datection:datectionDate:edit"><td>
    				<!-- <a href="${ctx}/datection/datectionDate/form?id=${datectionDate.id}">修改</a>-->
					<div class="one"><a href="${ctx}/datection/datectionDate/delete?id=${datectionDate.id}" onclick="return confirmx('确认要删除该设备数据吗？', this.href)">删除</a></div>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>