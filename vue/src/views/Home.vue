<template>
    <div>
        <el-row :gutter="10" style="margin-bottom: 60px">
            <el-col :span="6">
                <el-card style="color: #409EFF">
                    <div><i class="el-icon-user-solid"/> 用户总数</div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bold">
                        362
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #F56C6C">
                    <div><i class="el-icon-money"/> 销售总量</div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bold">¥1654820.91</div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #67C23A">
                    <div><i class="el-icon-bank-card"/> 收益总额</div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bold">¥389634.56</div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #E6A23C">
                    <div><i class="el-icon-s-shop"/> 门店总数</div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bold">89</div>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <div id="main" style="width: 500px;height: 400px"></div>
            </el-col>
            <el-col :span="12">
                <div id="pie" style="width: 500px;height: 400px"></div>
            </el-col>
        </el-row>
    </div>

</template>

<script>
    import * as echarts from 'echarts';

    export default {
        name: "Home",
        data(){
            return{

            }
        },
        //页面元素渲染完成之后才执行
        mounted() {
            var option = {
                title: {
                    text: '各季度会员注册统计',
                    subtext: '趋势图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                xAxis: {
                    type: 'category',
                    data: ["第一季度","第二季度","第三季度","第四季度"]
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:"蜜雪冰城",
                        data: [],
                        color:"#E40055",
                        type: 'line'
                    },
                    {
                        name: "蜜雪冰城",
                        data: [],
                        color:"#E40055",
                        type: 'bar'
                    },
                    {
                        name:"益禾堂",
                        data: [],
                        color:"#61e064",
                        type: 'line'
                    },
                    {
                        name: "益禾堂",
                        data: [],
                        color:"#61e064",
                        type: 'bar'
                    },
                ]
            };
            var pieOption = {
                title: {
                    text: '各季度会员注册统计',
                    subtext: '比例图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name:"蜜雪冰城",
                        type: 'pie',
                        radius: '60%',
                        center:['25%','55%'],
                        label:{            //饼图图形上的文本标签
                            normal:{
                                show:true,
                                position:'inner', //标签的位置
                                textStyle : {
                                    fontWeight : 300 ,
                                    fontSize : 12,    //文字的字体大小
                                    color: "#fff"
                                },
                                formatter:'{b}:{d}%'
                            }
                        },
                        data: [

                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    },
                    {
                        name:"益禾堂",
                        type: 'pie',
                        radius: '60%',
                        center:['75%','55%'],
                        label:{            //饼图图形上的文本标签
                            normal:{
                                show:true,
                                position:'inner', //标签的位置
                                textStyle : {
                                    fontWeight : 300 ,
                                    fontSize : 12,    //文字的字体大小
                                    color: "#fff"
                                },
                                formatter:'{b}:{d}%'
                            }
                        },
                        data: [
                            { name: '第一季度',value: 3},
                            { name: '第二季度', value: 4},
                            { name: '第三季度', value: 1},
                            { name: '第四季度', value: 3},
                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };

            var chartDom = document.getElementById('main');
            var myChart = echarts.init(chartDom);

            var pieChartDom = document.getElementById('pie');
            var pieMyChart = echarts.init(pieChartDom);

            this.request.get("echarts/members").then(res => {
                // console.log(res)
                // option.xAxis.data = res.data.x
                option.series[0].data = res.data
                option.series[1].data = res.data

                option.series[2].data = [3,4,1,3]
                option.series[3].data = [3,4,1,3]
                myChart.setOption(option);

                pieOption.series[0].data = [
                    { name: '第一季度',value: res.data[0]},
                    { name: '第二季度', value: res.data[1]},
                    { name: '第三季度', value: res.data[2]},
                    { name: '第四季度', value: res.data[3]},
                ]
                pieMyChart.setOption(pieOption);
            })


        }
    }
</script>

<style scoped>

</style>