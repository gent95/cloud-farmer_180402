/**
 * Created by gent on 2017/5/23.
 */
require.config({
    paths: {
        echarts: 'http://echarts.baidu.com/build/dist'
    }
});
// 路径配置
//作物分布
function getMap1(result){
    require(
        [
            'echarts',
            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('one'));
            backgroundColor: 'rgba(181, 219, 255, 0.30)',
                option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    series: [
                        {
                            name:'种植面积',
                            type:'pie',
                            radius: ['50%', '70%'],
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
                            data:result
                        }
                    ]
                };
            // myChart.on('click', function (params) {
            //     if(params.name == '蔬菜'){
            //         getMap2();
            //     }else{
            //
            //         getMap2();
            //     }
            // });
            // 为echarts对象加载数据
            myChart.setOption(option);
            //图表响应
            window.onresize = myChart.resize
        }

    );
}

function getMap2(){
    require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('two'));
            backgroundColor: 'rgba(181, 219, 255, 0.30)',
            option = {
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
                            color: '#fff'
                        }
                    },
                    boundaryGap: true,
                    data: ['2016-12','2017-1','2017-2','2017-3','2017-4','2017-5']
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        textStyle: {
                            color: '#fff'
                        }
                    },
                },
                series: [{
                    type: 'line',
                    stack: '总量',
                    data: [25, 26, 25.5, 24.5, 23.5,20]
                }]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
            //图表响应
            window.onresize = myChart.resize
        }
    );
}
