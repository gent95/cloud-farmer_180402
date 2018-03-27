// 路径配置
require.config({
    paths: {
        echarts: 'http://echarts.baidu.com/build/dist'
    }
});

//人员数量
function getPeople(url, farmerId) {
    $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        data: {
            farmerId: farmerId
        },
        success: function (result) {
            getPengAndTianCount(result);
            /*alert("字符串是:"+result.plant);*/
            getPlant(result);
            require(
                [
                    'echarts',
                    'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
                ],
                function (ec) {
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init(document.getElementById('chart1'));

                    option = {
                        backgroundColor: 'rgba(181, 219, 255, 0.30)',
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b}: {c} ({d}%)"
                        },
                        series: [{
                            type: 'pie',
                            radius: ['0%', '60%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data: [{
                                value: result.usedCount,
                                name: '工作人员'
                            }, {
                                value: result.userCount,
                                name: '管理人员'
                            }]
                        }]
                    };

                    // 为echarts对象加载数据
                    myChart.setOption(option);
                }
            );
        }
    });
}

//获取气象站信息
function getIt(url) {
    $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        success: function (result) {
            map(result);
        }
    });
}


function map(result) {
    //降雨
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('chart8'));
            option = {
                backgroundColor: 'rgba(181, 219, 255, 0.30)',
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                    boundaryGap: true,
                    data: result.dateTime
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                },
                series: [{
                    name: '降雨量',
                    type: 'line',
                    stack: '总量',
                    data: result.rainV
                }]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );
//光照
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('chart3'));

            option = {
                backgroundColor: 'rgba(181, 219, 255, 0.30)',
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                    boundaryGap: true,
                    data: result.dateTime
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                },
                series: [{
                    name: '光照',
                    type: 'line',
                    stack: '总量',
                    data: result.evaporation
                }]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );

    //风速
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('chart10'));

            option = {
                backgroundColor: 'rgba(181, 219, 255, 0.30)',
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                    boundaryGap: true,
                    data: result.dateTime
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                },
                series: [{
                    name: '风速',
                    type: 'line',
                    stack: '总量',
                    data: result.windSpeed
                }]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );

    //大气湿度
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('chart4'));

            option = {
                backgroundColor: 'rgba(181, 219, 255, 0.30)',
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                    boundaryGap: true,
                    data: result.dateTime
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                },
                series: [{
                    name: '大气湿度',
                    type: 'line',
                    stack: '总量',
                    data: result.humidity
                }]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );

    //气压
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('chart7'));

            option = {
                backgroundColor: 'rgba(181, 219, 255, 0.30)',
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                    boundaryGap: true,
                    data: result.dateTime
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    },
                },
                series: [{
                    name: '气压',
                    type: 'line',
                    stack: '总量',
                    data: result.radiate
                }]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );

//大气温度
    require(
        [
            'echarts',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('chart2'));

            var option = {
                backgroundColor: 'rgba(181, 219, 255, 0.30)',
                tooltip: {
                    show: true
                },
                xAxis: [{
                    type: 'category',
                    data: result.dateTime,
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    }
                }],
                yAxis: [{
                    type: 'value',
                    axisLabel: {
                        textStyle: {
                            color: '#FFFFFF'
                        }
                    }
                }],
                series: [{
                    name: '大气温度',
                    "type": "bar",
                    "data": result.airTemperature
                }]
            };
            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );
}


//获取大棚和大田数量
function getPengAndTianCount(result) {
    require(
        [
            'echarts',
            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('chart33'));

            option = {
                backgroundColor: 'rgba(181, 219, 255, 0.30)',
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [{
                    name: '土地类型分布',
                    type: 'pie',
                    radius: ['0%', '60%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: [{
                        value: result.peng,
                        name: '大棚'
                    }, {
                        value: result.tian,
                        name: '大田'
                    }]
                }]
            };
            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );
}

//获取节点和中继数量
function getNodeAndRelayCount(url, id) {
    $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        data: {id: id},
        success: function (result) {
            require(
                [
                    'echarts',
                    'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
                ],
                function (ec) {
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init(document.getElementById('chart9'));

                    option = {
                        backgroundColor: 'rgba(181, 219, 255, 0.30)',
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b}: {c} ({d}%)"
                        },
                        series: [{
                            name: '设备数量',
                            type: 'pie',
                            radius: ['0%', '60%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data: [{
                                value: result.nodeCount,
                                name: '节点'
                            }, {
                                value: result.relayCount,
                                name: '中继'
                            }]
                        }]
                    };

                    // 为echarts对象加载数据
                    myChart.setOption(option);
                }
            );
        }
    });

}

//作物分布
function getPlant(result) {
    var yValue = new Array();
    for (var num in result.plant) {
        yValue.push(result.plant[num]);
    }
    require(
        [
            'echarts',
            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('chart11'));

            option = {
                backgroundColor: 'rgba(181, 219, 255, 0.30)',
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [{
                    name: '作物分布',
                    type: 'pie',
                    radius: ['0%', '60%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: yValue
                }]
            };
            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );
}


function getAltis(result) {

    // var lat=31.100000;
    // var lng=104.030000;
    var lat = result.lat;
    var lng = result.lng;
    //在指定的容器内创建地图实例
    var map = new BMap.Map("container");

    map.enableScrollWheelZoom();//启用滚轮放大缩小，默认禁用。
    var point = new BMap.Point(lng, lat); //默认中心点
    var marker = new BMap.Marker(point);
    var opts = {
        width: 40,  // 信息窗口宽度
        height: 80,  // 信息窗口高度
        title: "地址" // 信息窗口标题
    }
    var infoWindow = new BMap.InfoWindow("当前位置<br/>经度:" + lng + "<br/>纬度：" + lat, opts); // 创建信息窗口对象
    marker.enableDragging(); //启用拖拽
    marker.addEventListener("dragend", function (e) {
        point = new BMap.Point(e.point.lng, e.point.lat); //标记坐标（拖拽以后的坐标）
        infoWindow = new BMap.InfoWindow("当前位置<br />经度：" + e.point.lng + "<br />纬度：" + e.point.lat, opts);
        marker = new BMap.Marker(point);
        map.openInfoWindow(infoWindow, point);
    })
    map.addControl(new BMap.NavigationControl()); //左上角控件
    map.enableScrollWheelZoom(); //滚动放大
    map.enableKeyboard(); //键盘放大
    map.centerAndZoom(point, 14); //绘制地图
    map.addOverlay(marker); //标记地图
    map.openInfoWindow(infoWindow, map.getCenter());  // 打开信息窗口
}

