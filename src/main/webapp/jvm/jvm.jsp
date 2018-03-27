<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<html>
<head>
	<title>jvm</title>
	<script src="/static/jquery/jquery-1.8.3.min.js"></script>
	<script src="/static/js/echarts.min.js"></script>

	<script type="text/javascript">
        $(document).ready(function () {
            option = {
                title : {
                    text: 'JVM运行状态-已分配',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['已用内存','未用内存']
                },
                series : [
                    {
                        name: '单位(M)',
                        type: 'pie',
                        radius : '80%',
//                        center: ['100%', '100%'],
                        data:[
                            {value:<%=Runtime.getRuntime().totalMemory()/1024/1024-Runtime.getRuntime().freeMemory()/1024/1024 %>, name:'已用内存'},
                            {value:<%=Runtime.getRuntime().freeMemory()/1024/1024%>, name:'未用内存'},
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
<div id="main"  style="width: 600px;height:400px;"></div>

<div id="total" style="width: 600px;height:400px;"></div>

</body>
</html>