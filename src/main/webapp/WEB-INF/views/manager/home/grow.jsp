<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1,user-scalable=no">
    <title>农业种植首页</title>
    <link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css"/>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script>
    <script src="${ctxStatic}/js/grow.js"></script>
    <style type="text/css">
        .head{padding: 15px 0;}
        #farmer{margin: 11px 0; float: right;}
        #farmer > a{
            display: inline-block; vertical-align: middle;
            height: 29px; padding: 5px; border-radius: 5px;
            color: #333; background: #fff;
            text-decoration: none;
            -webkit-transition: .3s ease-in-out;
            -moz-transition: .3s ease-in-out;
            -ms-transition: .3s ease-in-out;
            -o-transition: .3s ease-in-out;
            transition: .3s ease-in-out;
        }
        #farmer > a:hover{background: rgba(181, 219, 255, 0.60); color: yellow;}
        .navbar-brand{float: none;}
        #farmerName{outline: none;}
        .footer{padding-bottom: 15px; color: #fff;}
        .panel{background: none;}
        .BMap_bubble_content {
            color: #000000;
        }
        #box {
            margin: 10px 20px;
            height: auto;
            width: auto;
        }
        .myChart {
            height: 240px;
            width: 100%;
            background-color: #FFFFFF;
            background-color: rgba(181, 219, 255, 0.30);
            border-bottom-left-radius: 15px;
            border-bottom-right-radius: 15px;
        }
        .topHead {
            color: yellow;
            font-size: 18px;
            height: 28px;
            background-color: #FFFFFF;
            background-color: rgba(181, 219, 255, 0.60);
            text-align: center;
            line-height: 28px;
            border-top-left-radius: 15px;
            border-top-right-radius: 15px;
        }
        #logo {border-radius: 15px;}
        option {
            height: 60px;
            padding-left: 40%;
        }
        select {
            text-align: center;
        }
        @media(max-width: 768px){
            .container{width: 100%;}
            .head #logo{width: 190px;}
            #farmer{float: none; text-align:center;}
        }
    </style>
</head>

<body>
<div class="container">
    <div class="row head">
        <div class="col-md-4">
            <div id="logo"><a href="${ctx}/chart/sysIndex"><img class="img-responsive" src="${ctxStatic}/images/no-3.png"/></a></div>
        </div>
        <div class="col-md-8">
            <div id="farmer">
            <select id="farmerName" name="farmerName">
                <option>请选择农场</option>
            </select>
            <a href="${ctx}/grow/goBack">返回</a>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">人员数量</div>
                <div id="chart1" class="myChart" ></div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">大气温度(℃)</div>
                <div id="chart2" class="myChart" ></div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">光照(LUX)</div>
                <div id="chart3" class="myChart" ></div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel" id="farmland">
                <div class="topHead">土地类型分布</div>
                <div id="chart33" class="myChart" ></div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">大气湿度(%RH)</div>
                <div id="chart4" class="myChart" ></div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel">
                <div class="topHead">地图</div>
                <div id="chart5" class="myChart" >
                    <div style="width:100%;height:100%;" id="container"></div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">气压(hpa)</div>
                <div id="chart7" class="myChart" ></div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">降雨量(mm/min)</div>
                <div id="chart8" class="myChart" ></div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">设备数量</div>
                <div id="chart9" class="myChart" ></div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">风速(m/s)</div>
                <div id="chart10" class="myChart" ></div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="topHead">作物分布</div>
                <div id="chart11" class="myChart" ></div>
            </div>
        </div>
    </div>
</div>
<div class="footer text-center">
<%--    Copyright&copy;大唐移动通信设备有限公司--%>
</div>
<script type="text/javascript">
    $(function(){
        function getSome(){
            var farmerId = $("select option:selected").val();
            getPeople("${ctx}/grow/getByFarmerlandTypeCount",farmerId);
            getNodeAndRelayCount("${ctx}/grow/getNodeAndRelayCount",farmerId);
            $.ajax({
                url:"${ctx}/grow/getLogAndLat" ,
                type:"get",
                dataType:"json",
                data:{
                    farmerId:farmerId
                },
                success:function(result){
                    getAltis(result);
                }
            });
        }
        getFarmers("${fns:getUser().id}");
        getIt("${ctx}/grow/getDatectionDates");
        $("#farmland").click(function(){
            var farmerId = $("select option:selected").val();
            location.href="${ctx}/farmerland/farmland/farmlandDetail?id="+farmerId;
        });

        $("select").change(function(){
            getSome()
        })

        /**
         * 图表响应&&转屏监听
         */
        $(window).resize(function(){
            getFarmers("${fns:getUser().id}")
            getIt("${ctx}/grow/getDatectionDates")
            getSome()
        })
    });
    function  getFarmers(userId) {
        $.ajax({
            url:"${ctx}/grow/getFarmers",
            type:"get",
            dataType:"json",
            data:{
                userId:userId
            },
            success:function (result) {
                for(var i = 0;i < result.farmers.length;i++){
                    $("select").append("<option value="+result.farmers[i].id+">"+result.farmers[i].name + "</option>");
                }
            }
        });
    }
</script>
</body>

</html>