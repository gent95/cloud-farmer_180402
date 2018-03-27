<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>中继管理</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
    <script src="/static/js/echarts.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            option = {
                title : {
                    text: '连接设备比例图',
                    subtext: '中继总数量(${total})/台',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['在线设备','离线设备']
                },
                series : [
                    {
//                        name: '设备状态',
                        type: 'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data:[
                            {value:${online}, name:'在线设备'},
                            {value:${offline}, name:'离线设备'},
                        ],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            var myChart = echarts.init(document.getElementById('main'));
            myChart.setOption(option);
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
<div id="main" class="device"></div>

<div class="bodyDiv">
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>中继编号</th>
        <th>连接时间</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${list}" var="list">
        <tr>
            <td>
                    ${list.relayMac}
            </td>
            <td>
                <%--<fmt:formatDate value="${list.lastTime}" type="time" timeStyle="long"/>--%>
                <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${list.lastTime}" />
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</body>
</html>