<%--
  Created by IntelliJ IDEA.
  User: gent
  Date: 2017/5/23
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1,user-scalable=no">
    <title>四川</title>
    <link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxStatic}/css/media.css"/>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
    <script src="${ctxStatic}/js/map/echarts-all.js"></script>
    <script src="${ctxStatic}/js/map/map.js"></script>
    <style type="text/css">
        #box {
            margin-left: 15px;
        }
        #right {
            width: 450px;
        }

        #main {
            border: solid white 1px;
        }
        #one, #two {
            position: relative;
            height: 280px;
            border: solid white 1px;
            margin-bottom: 30px;
            background: rgba(0, 0, 0, .3)
        }
        #three {
            margin-bottom: 5px;
            padding: 5px;
            border: solid white 1px;
            background: rgba(0, 0, 0, .5);
        }
        #three table {
            border: none
        }

        #cun tr td {
            /*border: 1px solid #ffffff;*/
        }
        #logo {
            padding-left: 30px;
            padding-top: 10px;
        }
        a {
            display: block;
            width: 100%;
            text-decoration: none;
            color: white;
            transition: .3s ease-in-out;
        }
        a:hover {
            text-decoration: none;
            background: #fff;
            color: #000;
        }
    </style>
</head>
<body class="mapbg">
<div class="navbar">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand logo" href="${ctx}/chart/sysIndex"><img src="${ctxStatic}/images/no-3.png"/></a>
        </div>
    </div>
</div>
<div class="primary-map container">
    <div class="row">
        <div class="col-md-7 col-lg-8">
            <div id="main"></div>
        </div>
        <div class="col-md-5 col-lg-4">
            <div id="one" class="map-unit">
                <h1 class="map-title pull-right">(万亩)</h1>
                <iframe width="100%" height="100%" id="map-1" src="${ctx}/grow/getOne?parms=成都市"
                        style="border: none"
                        scrolling="no"></iframe>
            </div>
            <div id="two" class="map-unit">
                <h1 class="map-title pull-right">(晒统个 元/kg)</h1>
                <iframe width="100%" height="100%" id="map-2" src="${ctx}/grow/getTwo?parms=成都市"
                        style="border: none" scrolling="no"></iframe>
            </div>
            <div id="three" class="map-unit">
                <table id="cun" width="100%" style="color: white;font-size: 16px; font-weight: bold; ">
                    <tr>
                        <td colspan="6" style="text-align: center">敖平镇川穹种植分布</td>
                    </tr>
                    <tr>
                        <td>平安村</td>
                        <td>凤泉村</td>
                        <td><a href="${ctx}/chart/grow">鹤泉村</a></td>
                        <td>曙光村</td>
                        <td>石泉村</td>
                        <td>方桥村</td>
                    </tr>
                    <tr>
                        <td>双凤村</td>
                        <td>下泉村</td>
                        <td>中泉村</td>
                        <td>枷担村</td>
                        <td>定光村</td>
                        <td>高河村</td>
                    </tr>
                    <tr>
                        <td>漆树村</td>
                        <td>济泉村</td>
                        <td>石音村</td>
                        <td>民主村</td>
                        <td>罗林村</td>
                        <td>河北村</td>
                    </tr>
                    <tr>
                        <td colspan="6" style="text-align: center">隐峰镇川穹种植分布</td>
                    </tr>
                    <tr>
                        <td>农科村</td>
                        <td>杨寨村</td>
                        <td>红桥村</td>
                        <td>寿增村</td>
                        <td>石桥村</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    function createChart()
    {
        var myChart = echarts.init(document.getElementById('main'));
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: function (a) {
                    return a[1];
                }
            },
            series: [{
                name: '四川省',
                type: 'map',
                mapType: '四川',
                selectedMode: 'single',
                data: [{
                    name: '甘孜藏族自治州',
                    value: 1
                }, {
                    name: '阿坝藏族羌族自治州',
                    value: 2
                }, {
                    name: '凉山彝族自治州',
                    value: 3
                }, {
                    name: '绵阳市',
                    value: 4
                }, {
                    name: '达州市',
                    value: 5
                }, {
                    name: '广元市',
                    value: 6
                }, {
                    name: '雅安市',
                    value: 7
                }, {
                    name: '宜宾市',
                    value: 8
                }, {
                    name: '乐山市',
                    value: 9
                }, {
                    name: '南充市',
                    value: 10
                }, {
                    name: '巴中市',
                    value: 11
                }, {
                    name: '泸州市',
                    value: 12
                }, {
                    name: '成都市',
                    value: 13
                }, {
                    name: '资阳市',
                    value: 14
                }, {
                    name: '攀枝花市',
                    value: 15
                }, {
                    name: '眉山市',
                    value: 16
                }, {
                    name: '内江市',
                    value: 17
                }, {
                    name: '自贡市',
                    value: 18
                }, {
                    name: '广安市',
                    value: 19
                }, {
                    name: '遂宁市',
                    value: 20
                }, {
                    name: '德阳市',
                    value: 21
                }],
                itemStyle: {
                    normal: {
                        borderColor: 'green',
                        areaColor: 'white',
                    },
                    emphasis: {
                        areaColor: 'white',
                        borderWidth: 0
                    }
                }
            }]
        };

        myChart.on('click', function (params) {
            var parm = params.name;
            document.getElementById("map-1").src = "${ctx}/grow/getOne";
            document.getElementById("map-2").src = "${ctx}/grow/getTwo";
            document.getElementById("map-1").src = document.getElementById("map-1").src + "?parms=" + parm;
            document.getElementById("map-2").src = document.getElementById("map-2").src + "?parms=" + parm;
        });
        myChart.setOption(option, true);
        window.onresize = myChart.resize
    }

    /**
     * [判断Android还是iPhone]
     * @param test: 0:iPhone    1:Android
     */
    function ismobile(test){
        var u = navigator.userAgent, app = navigator.appVersion;
        if(/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))){
            if(window.location.href.indexOf("?mobile")<0){
                try{
                    if(/iPhone|mac|iPod|iPad/i.test(navigator.userAgent)){
                        return '0';
                    }else{
                        return '1';
                    }
                }catch(e){}
            }
        }else if( u.indexOf('iPad') > -1){
            return '0';
        }else{
            return '1';
        }
    };
    //图表响应
    function resizeHeight() {
        if ($(document).width() < 768) {
            $('#main').height(280)
            createChart()
            var test = $("#map-1").contents().find("#one");
            var test2 = $("#map-2").contents().find("#two");
            parseInt(ismobile(1)) === 1 ?  test.css('width', document.body.clientWidth) : test.css('width',window.screen.width)
            test2.css('width', document.body.clientWidth-30)
            test.addClass('middle')
            test2.addClass('middle')
        }
        else {
            $('#main').height(600)
            createChart()
            var test = $("#map-1").contents().find("#one");
            var test2 = $("#map-2").contents().find("#two");
            test.css('width', '100%')
            test2.css('width', '100%')
            test.addClass('middle')
            test2.addClass('middle')
        }
//        $('.zr-element').each(function(){
//            $(this).css('width', '100%')
//        })
        $('#main').css('background', 'rgba(0,0,0,.3)')
    }
    //初始化
    resizeHeight()
    $(window).resize(function () {
        resizeHeight()
    })
//    $(window).on('orientationchange', function() {
//    });

</script>
</body>
</html>
