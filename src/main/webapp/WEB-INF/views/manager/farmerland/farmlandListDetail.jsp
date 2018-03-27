<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>农田详情</title>
    <meta name="decorator" content="default"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1,user-scalable=no">
    <link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
    <link rel="stylesheet" href="/static/css/farmland.css"/>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
    <script type="text/javascript">
        function  command(a) {
            var task=(a.className=="open2")?"1":"0";
            var  b=a.parentNode.parentNode;
            var c=a.childNodes[1].value;
         $.ajax({
                type: "POST",
                url: "${ctx}/node/node/changeSwitch",
                dataType: "JSON",
                data: {
                    id: c,
                    task: task
                },
                success: function (result) {
                    if (result.flag == '0') {
                        layer.msg("设备掉线，或未连接！")

                    }
                    if (result.flag == '1') {
                        var index = layer.load(1, {
                            shade: [0.1, '#fff']
                        });
                        var num =0;
                        setInterval(function () {
                            if(num >= 1){
                                return;
                            }
                            $.ajax({
                                type: "POST",
                                url: "${ctx}/node/node/selectSwitch",
                                dataType: "JSON",
                                data: {
                                    id: c,
                                    task: task,
                                },
                                success: function (result) {
                                    if (result.flag == 0) {
                                        if(num == 0){
                                            layer.close(index);
                                            layer.msg("网络连接超时！");
                                            num++;
                                        }
                                    }
                                    if(result.flag == 1){
                                        layer.msg(result.msg);
                                    }
                                    if(result.flag == 2){
                                        layer.close(index);
                                        layer.msg("操作成功！");
                                        num++;
                                        a.className=(a.className=="close2")?"open2":"close2";
                                        b.className=(b.className=="close1")?"open1":"close1";
                                    }
                                }
                            });
                        }, 3000);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="container">
    <div class="row head">
        <a class="logo" href="${ctx}/chart/sysIndex"><img class="img-responsive" src="${ctxStatic}/images/no-3.png"/></a>
    </div>
    <div class="row">
        <div class="col-md-3">
            <div class="panel">
                <div  class="title"><span>人员信息</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center">
                    <div>
                        <span><img src="/static/images/userName.png"/></span>
                        <span>：${user.name}</span>
                    </div>
                    <div>
                        <span><img src="/static/images/iphone.png"/></span>
                        <span class="d">：${user.mobile}</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div  class="title"><span>种植管理</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center plant">
                    <c:forEach items="${plantList}" var="fa">
                        <c:if test="${fa.plantVaritety ne null}">
                            <div><span>${fa.plantVaritety}</span></div>
                        </c:if>
                    </c:forEach>
                    <c:if test="${farmlandList==null}">
                        <div><span>暂无数据</span></div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>设备数量列表</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center table-responsive">
                    <table class="table" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>中继</td>
                        </tr>
                        <tr>
                            <td><a href="${ctx}/relay/relay">${relays.size()}</a></td>
                        </tr>
                    </table>
                    <table class="table" cellpadding="0" cellspacing="0">
                        <tr> <td>节点</td></tr>
                        <tr> <td><a href="${ctx}/node/node">${nodes.size()}</a></td></tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>视频登录</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center box-center">
                    <a href="http://60.255.50.139"><img src="/static/images/jiankong.png"/></a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>大棚温度平均值</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center">
                    <div id="ptempChar"  style="width: 100%; height: 100%;"></div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>农田名称和种植种类</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center table-responsive">
                    <table class="table" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>农田名称</td>
                            <td>种植种类</td>
                        </tr>
                        <c:forEach items="${farmlandList}" var="fa">
                            <tr>
                                <td>${fa.alias}</td>
                                <td>${fa.plantVaritety}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>开关种类/手动控制</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center table-responsive">
                    <table class="table" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>开关种类</td>
                            <td>手动控制</td>
                        </tr>
                        <c:forEach items="${nodes}" var="no">
                            <c:if test="${no.onOffName!=null&&no.onOffName!=''}">
                                <tr>
                                    <td>
                                        <div>${no.onOffName}</div>
                                    </td>
                                    <td>
                                        <div id="div1"
                                                <c:choose>
                                                    <c:when test="${no.openFlag==1}"> class="open1"</c:when>
                                                    <c:otherwise> class="close1"</c:otherwise>
                                                </c:choose>>
                                            <a  style="cursor:pointer">
                                                <div id="div2" onclick="command(this)"
                                                        <c:choose>
                                                            <c:when test="${no.openFlag==1}"> class="open2"</c:when>
                                                            <c:otherwise> class="close2"</c:otherwise>
                                                        </c:choose>>
                                                    <input type="hidden" class="nodeId" value="${no.id}"/>
                                                </div>
                                            </a>
                                        </div>

                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>大棚湿度平均值</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center">
                    <div id="phumChar" style="width: 100%; height: 100%;"></div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>大棚二氧化碳浓度平均值</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center">
                    <div id="co2Char" style="width: 100%; height: 100%;"></div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>土壤温度平均值</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center">
                    <div id="tempChar" style="width: 100%; height: 100%;"></div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel">
                <div class="title"><span>土壤水分平均值</span><img src="/static/images/zhen.png"/></div>
                <div class="panel-body box-center">
                    <div id="humChar"  style="width: 100%; height: 100%;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="farmerId" value="${farmerId}"/>
<%--<table class="t1">--%>
    <%--<tr>--%>
		<%--<td>--%>
            <%--<div class="logo">--%>
                <%--<a href="${ctx}/chart/sysIndex">--%>
                <%--<img src="/static/images/smallLogo.png"/>--%>
                <%--</a>--%>
            <%--</div>--%>
			<%--<div class="msg">--%>
                <%--<div  class="title"><span>人员信息</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<div class="con">--%>
                    <%--<div><div><img src="/static/images/userName.png"/></div><div class="d">：${user.name}</div></div>--%>
                    <%--<div><div><img src="/static/images/iphone.png"/></div><div class="d">：${user.mobile}</div></div>--%>
                <%--</div>--%>
            <%--</div>--%>
		<%--</td>--%>
		<%--<td>--%>
			<%--<div class="centent">--%>
                <%--<div  class="title"><span>种植管理</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<div class="plant">--%>
                   <%--<c:forEach items="${plantList}" var="fa">--%>
                       <%--<c:if test="${fa.plantVaritety ne null}">--%>
                           <%--<div><span>${fa.plantVaritety}</span></div>--%>
                       <%--</c:if>--%>
                   <%--</c:forEach>--%>
                    <%--<c:if test="${farmlandList==null}">--%>
                        <%--<div><span>暂无数据</span></div>--%>
                    <%--</c:if>--%>
                <%--</div>--%>

            <%--</div>--%>
		<%--</td>--%>
		<%--<td>--%>
			<%--<div class="centent">--%>
                <%--<div class="title"><span>设备数量列表</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<div class="fl">--%>
                    <%--<table class="t3" cellpadding="0" cellspacing="0">--%>
                        <%--<tr>--%>
                            <%--<td>中继</td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td><a href="${ctx}/relay/relay">${relays.size()}</a></td>--%>
                        <%--</tr>--%>
                    <%--</table>--%>
                <%--<table class="t4" cellpadding="0" cellspacing="0">--%>
                    <%--<tr> <td>节点</td></tr>--%>
                    <%--<tr> <td><a href="${ctx}/node/node">${nodes.size()}</a></td></tr>--%>
                <%--</table>--%>
                <%--</div>--%>
                <%--</div>--%>
		<%--</td>--%>
		<%--<td>--%>
			<%--<div class="centent">--%>
                <%--<div class="title"><span>视频登录</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<div class="jiankong">--%>
                   <%--<a href="http://60.255.50.139"><img src="/static/images/jiankong.png"/></a>--%>
                <%--</div>--%>
            <%--</div>--%>
		<%--</td>--%>
		<%--</tr>--%>
        <%--<tr>--%>
        <%--<td>--%>
            <%--<div class="centent">--%>
                <%--<div class="title"><span>大棚温度平均值</span><img src="/static/images/zhen.png"/></div>--%>

                <%--<div  class="col-md-2 div-padding"  id="ptempChar"  style="width: 445px;height: 250px;margin: 0 auto;"></div>--%>
            <%--</div>--%>
        <%--</td>--%>
        <%--<td colspan="2">--%>
            <%--<div class="lists">--%>
                <%--<div class="title1"><span>农田名称和种植种类</span><img src="/static/images/zhen.png"/></div>--%>

              <%--<table class="t2" cellpadding="0" cellspacing="0">--%>
                    <%--<tr>--%>
                        <%--<td>农田名称</td>--%>
                        <%--<td>种植种类</td>--%>
                    <%--</tr>--%>
                  <%--<c:forEach items="${farmlandList}" var="fa">--%>
                      <%--<tr>--%>
                          <%--<td>${fa.alias}</td>--%>
                          <%--<td>${fa.plantVaritety}</td>--%>
                      <%--</tr>--%>
                  <%--</c:forEach>--%>

                <%--</table>--%>
            <%--</div>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<div class="centent">--%>
                <%--<div class="title"><span>开关种类/手动控制</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<table class="tt" cellpadding="0" cellspacing="0">--%>
                    <%--<tr>--%>
                        <%--<td>开关种类</td>--%>
                        <%--<td>手动控制</td>--%>
                    <%--</tr>--%>
                    <%--<c:forEach items="${nodes}" var="no">--%>
                        <%--<c:if test="${no.onOffName!=null&&no.onOffName!=''}">--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                                    <%--<div>${no.onOffName}</div>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--<div id="div1"--%>
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${no.openFlag==1}"> class="open1"</c:when>--%>
                                            <%--<c:otherwise> class="close1"</c:otherwise>--%>
                                        <%--</c:choose>>--%>
                                    <%--<a  style="cursor:pointer">--%>
                                    <%--<div id="div2" onclick="command(this)"--%>
                                        <%--<c:choose>--%>
                                             <%--<c:when test="${no.openFlag==1}"> class="open2"</c:when>--%>
                                            <%--<c:otherwise> class="close2"</c:otherwise>--%>
                                         <%--</c:choose>>--%>
                                        <%--<input type="hidden" class="nodeId" value="${no.id}"/>--%>
                                    <%--</div>--%>
                                    <%--</a>--%>
                                <%--</div>--%>

                        <%--</td>--%>
                    <%--</tr>--%>
                        <%--</c:if>--%>
                    <%--</c:forEach>--%>
                <%--</table>--%>
            <%--</div>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td>--%>
            <%--<div class="centent">--%>
                <%--<div class="title"><span>大棚湿度平均值</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<div class="col-md-2 div-padding" id="phumChar" style="width: 445px;height: 250px;margin: 0 auto;"></div>--%>
            <%--</div>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<div class="centent">--%>
                <%--<div class="title"><span>大棚二氧化碳浓度平均值</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<div class="col-md-2 div-padding" id="co2Char" style="width: 445px;height: 250px;margin: 0 auto;"></div>--%>
            <%--</div>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<div class="centent">--%>
                <%--<div class="title"><span>土壤温度平均值</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<div class="col-md-2 div-padding" id="tempChar"  style="width: 445px;height: 250px;margin: 0 auto;"></div>--%>
            <%--</div>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<div class="centent">--%>
                <%--<div class="title"><span>土壤水分平均值</span><img src="/static/images/zhen.png"/></div>--%>
                <%--<div class="col-md-2 div-padding" id="humChar"  style="width: 445px;height: 250px;margin: 0 auto;"></div>--%>
            <%--</div>--%>
        <%--</td>--%>
    <%--</tr>--%>
<%--</table>--%>
<div class="footer text-center">
   <%-- Copyright &copy;大唐移动通信设备有限公司--%>
</div>
<!--Echart Start -->

<script src="/static/js/echarts.js"></script>
<script src="/static/js/echarts.min.js"></script>
<%--<script type="text/javascript">
    function dateformatutil(longtypedate){
           alert(longtypedate);
        var date = new date(longtypedate);
        date.gethours()>10?date.gethours():""+date.gethours();
         h = date.gethours() + ':';
         m = date.getminutes();

         var time=(h+m);
        return time;
    }
</script>--%>
<script type="text/javascript">
$(function(){


    function infoList(){
        var flag = 0;
        var infoList;
        timeInterval=1000*60*60;
        var farmerid = $("#farmerId").val();
        // 配置路径
        require.config({
            paths: {
                echarts: '${ctxStatic}/js'  //（这里）
            }
        });
        $.ajax({
            url:"${ctx}/farmerland/farmland/getData",
            type:"POST",
            data:{
                farmerId:farmerid
            },
            dataType:"json",
            async:false,
            success:function(info){
                if(info.data.length==0){
                    if(flag==0){
                        layer.msg("暂无数据");
                        flag=-1;
                    }
                }
                else{
                    infoList=info;
                }
            }
        });
        require(
            [
                'echarts.min'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var echart = ec.init(document.getElementById('co2Char'));
                // 同步执行
                $.ajaxSettings.async = true;
                // 加载数据
                $.getJSON('node/getData.action', function (json) {
                    categories = json.dataTypeName;
                    values = json.dataValue;
                });


                var yValue = new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<8;i++)
                    {
                        yValue[i] = infoList.data[i].co2;
                        da[i] =infoList.data[i].nowTime;
                    }
                }

                var option = {
                    //设置标题的
                    title : {
                        text: '二氧化碳浓度',//大标题写类型

                        textStyle: {
                            color: '#ffffff',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    xAxis:  [
                        {
                        type: 'category',
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }
                    ],
                    yAxis: {
                        type: 'value',
                        splitLine: {
                            show: false
                        },
                        axisLabel: {
                            formatter: '{value}',
                        },
                        name : '（ppm）',
                        scale: true,
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        boundaryGap: [0.2, 0.2],
                        axisPointer: {
                            snap: true
                        }
                    },
                    color:
                        ['#e4ff00'],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'二氧化碳浓度',
                            type:'line',
                            smooth:true,
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                window.onresize = echart.resize
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
               setInterval(function (){
                  $.ajax({
                        url:"${ctx}/farmerland/farmland/getData",
                        type:"POST",
                        data:{
                            farmerId:farmerid
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<10;i++)
                            {
                                lastData[i] = info.data[i].co2;
                                axisData[i] =info.data[i].nowTime;
                            }
                        }
                    });
                     option.series[0].data.shift();
                   option.series[0].data.push(lastData);
                    option.xAxis[0].data.shift();
                     option.xAxis[0].data.push(axisData);
                    echart.setOption(option);
                }, timeInterval);
            }
        );
        require(
            [
                'echarts.min'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var echart = ec.init(document.getElementById('phumChar'));
                // 同步执行
                $.ajaxSettings.async = true;
                var yValue = new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<8;i++)
                    {
                        yValue[i] = infoList.data[i].airHumidity;
                        da[i] =infoList.data[i].nowTime;
                    }
                }

                var option = {
                    title : {
                        text: '大棚湿度',//大标题写类型
                        textStyle: {
                            color: '#ffffff',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    //设置标题的
                    tooltip : {
                        trigger: 'axis'
                    },
                    xAxis:  [{
                        type: 'category',
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }],
                    yAxis: {
                        type: 'value',
                        name:'（%RH）',
                        splitLine: {
                            show: false
                        },
                        axisLabel: {
                            formatter: '{value} ',
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        axisPointer: {
                            snap: true
                        }
                    },
                    color:
                        ['#00ffea'],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'大棚湿度',
                            type:'line',
                            smooth:true,
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                window.onresize = echart.resize
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                setInterval(function (){
                    $.ajax({
                        url:"${ctx}/farmerland/farmland/getData",
                        type:"POST",
                        data:{
                            farmerId:farmerid
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<10;i++)
                            {
                                lastData[i] = info.data[i].airHumidity;
                                axisData[i] =info.data[i].nowTime;
                            }
                        }
                    });
                    option.series[0].data.shift();
                    option.series[0].data.push(lastData);
                    option.xAxis[0].data.shift();
                    option.xAxis[0].data.push(axisData);
                    echart.setOption(option);
                    window.onresize = echart.resize
                }, timeInterval);
            }
        );
        require(
            [
                'echarts.min'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var echart = ec.init(document.getElementById('ptempChar'));
                // 同步执行
                $.ajaxSettings.async = true;
                var yValue = new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<8;i++)
                    {
                        yValue[i] = infoList.data[i].airTemperature;
                        da[i] =infoList.data[i].nowTime;
                    }
                }

                var option = {
                    title : {
                        text: '大棚温度',//大标题写类型
                        textStyle: {
                            color: '#ffffff',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    //设置标题的
                    tooltip : {
                        trigger: 'axis'
                    },
                    xAxis:  [{
                        type: 'category',
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }],
                    yAxis: {
                        type: 'value',
                        name:'（℃）',
                        splitLine: {
                            show: false
                        },
                        axisLabel: {
                            formatter: '{value} ',
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        axisPointer: {
                            snap: true
                        }
                    },
                    color:
                        ['#28ffb1'],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'大棚温度',
                            type:'line',
                            smooth:true,
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                window.onresize = echart.resize
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                setInterval(function (){
                    $.ajax({
                        url:"${ctx}/farmerland/farmland/getData",
                        type:"POST",
                        data:{
                            farmerId:farmerid
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<10;i++)
                            {
                                lastData[i] = info.data[i].airTemperature;
                                axisData[i] =info.data[i].nowTime;
                            }
                        }
                    });
                    option.series[0].data.shift();
                    option.series[0].data.push(lastData);
                    option.xAxis[0].data.shift();
                    option.xAxis[0].data.push(axisData);
                    echart.setOption(option);
                    window.onresize = echart.resize
                }, timeInterval);
            }
        );

        require(
            [
                'echarts.min'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var echart = ec.init(document.getElementById('humChar'));
                // 同步执行
                $.ajaxSettings.async = true;
                var yValue = new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<8;i++)
                    {
                        yValue[i] = infoList.data[i].soilHumidity1;
                        da[i] =infoList.data[i].nowTime;
                    }
                }

                var option = {
                    title : {
                        text: '大棚水分',//大标题写类型
                        textStyle: {
                            color: '#ffffff',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    //设置标题的
                    tooltip : {
                        trigger: 'axis'
                    },
                    xAxis:  [{
                        type: 'category',
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }],
                    yAxis: {
                        type: 'value',
                        name:'（%RH）',
                        splitLine: {
                            show: false
                        },
                        axisLabel: {
                            formatter: '{value} ',
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        axisPointer: {
                            snap: true
                        }
                    },
                    color:
                        ['#ff1e00'],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'土壤水分',
                            type:'line',
                            smooth:true,
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                window.onresize = echart.resize
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                setInterval(function (){
                    $.ajax({
                        url:"${ctx}/farmerland/farmland/getData",
                        type:"POST",
                        data:{
                            farmerId:farmerid
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<10;i++)
                            {
                                lastData[i] = info.data[i].soilHumidity1;
                                axisData[i] =info.data[i].nowTime;
                            }
                        }
                    });
                    option.series[0].data.shift();
                    option.series[0].data.push(lastData);
                    option.xAxis[0].data.shift();
                    option.xAxis[0].data.push(axisData);
                    echart.setOption(option);
                    window.onresize = echart.resize
                }, timeInterval);
            }
        );
        require(
            [
                'echarts.min'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var echart = ec.init(document.getElementById('tempChar'));
                // 同步执行
                $.ajaxSettings.async = true;
                var yValue = new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<8;i++)
                    {
                        yValue[i] = infoList.data[i].soilTemperature1;
                        da[i] =infoList.data[i].nowTime;
                    }
                }

                var option = {
                    title : {
                        text: '土壤温度',//大标题写类型
                        textStyle: {
                            color: '#ffffff',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    //设置标题的
                    tooltip : {
                        trigger: 'axis'
                    },
                    xAxis: [ {
                        type: 'category',
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }],
                    yAxis: {
                        type: 'value',
                        name:'（℃）',
                        splitLine: {
                            show: false
                        },
                        axisLabel: {
                            formatter: '{value} ',
                        },
                        axisLine:{
                            lineStyle:{
                                color:'#ffffff'
                            }
                        },
                        axisPointer: {
                            snap: true
                        }
                    },
                    color:
                        ['#ff9600'],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'土壤温度',
                            type:'line',
                            smooth:true,
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                window.onresize = echart.resize
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                setInterval(function (){
                    $.ajax({
                        url:"${ctx}/farmerland/farmland/getData",
                        type:"POST",
                        data:{
                            farmerId:farmerid
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<10;i++)
                            {
                                lastData[i] = info.data[i].soilTemperature1;
                                axisData[i] =info.data[i].nowTime;
                            }
                        }
                    });
                    option.series[0].data.shift();
                    option.series[0].data.push(lastData);
                    option.xAxis[0].data.shift();
                    option.xAxis[0].data.push(axisData);
                    echart.setOption(option);
                    window.onresize = echart.resize
                }, timeInterval);
            }
        );
    }
    infoList()
    $(window).resize(function(){
        infoList()
    })
})
</script>
</body>
</html>


