<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>开关控制</title>
<meta name="decorator" content="default" />
	<link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
<script type="text/javascript">
        $(document).ready(function () {
            //初始化智能呢个控制列表
            $.ajax({
                type:"get",
                url:"${ctx}/waring/waringCycle/getList",
				dataType:"JSON",
				data:{
					nodeNum : $("input[name='nodeNum']").val()
				},
				success:function(result){
                        for (var i=0; i <= result.msg.length; i++){
                            switch (result.msg[i].property){
								case "soilTemperature2":
                                    result.msg[i].property = "土壤温度2"
									break;
                                case "soilTemperature1":
                                    result.msg[i].property = "土壤温度1"
                                    break;
                                case "soilTemperature3":
                                    result.msg[i].property = "土壤温度3"
                                    break;
                                case "soilHumidity3":
                                    result.msg[i].property = "土壤湿度3"
                                    break;
                                case "soilHumidity2":
                                    result.msg[i].property = "土壤湿度2"
                                    break;
                                case "soilHumidity1":
                                    result.msg[i].property = "土壤湿度1"
                                    break;
                                case "airHumidity":
                                    result.msg[i].property = "大气湿度"
                                    break;
                                case "airTemperature":
                                    result.msg[i].property = "大气温度"
                                    break;
                                case "co2":
                                    result.msg[i].property = "二氧化碳浓度"
                                    break;
                           }
                        $("#auto table tbody").append(
                            "<tr><td >"+result.msg[i].nodeNum+"</td>"
							+"<td >"+result.msg[i].property+"</td>"
                            +"<td >"+result.msg[i].max+"</td>"
                            +"<td >"+result.msg[i].min+"</td>"
                            +"<td >"+result.msg[i].cycle+"</td>"
							+"<td><div class='two'><a onclick='modify("+result.msg[i].id+")'>修改</a></div><div class='one'><a onclick='deleteWaringCycle("+result.msg[i].id+")'>删除</a></div></td></tr>"
						);
                    }
                }
			});

            $("#inputForm").validate({
                submitHandler: function (form) {
                    //loading('正在提交，请稍等...');
                    layer.msg('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            /**
             * 更改设备开关
             */
            $(".node-switch").click(function () {
                var task = $("input[name='task']:checked").val();
                $.ajax({
                    type: "POST",
                    url: "${ctx}/node/node/changeSwitch",
                    dataType: "JSON",
                    data: {
                        id: ${node.id},
                        task: task,
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
                                        id: ${node.id},
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
                                            setTimeout("location.reload()", 2000);
                                        }
                                    }
                                });
                            }, 3000);
                        }
                    }
                });
            })
        });

        var reg = new RegExp("[0-9]+");
        //添加方法
        function addWaringCycle() {
            var nodeNum = $("input[name='nodeNum']").val();
            var property = $("select[name='property']").val();
            var max = $("input[name='max']").val();
            if (max == null || max == '' || !reg.test(max)) {
                layer.msg("最大值不能为空，且只能输入纯数字！")
                return;
            }
            var min = $("input[name='min']").val();
            if (min == null || min == '' || !reg.test(min)) {
                layer.msg("最大值不能为空，且只能输入纯数字！")
                return;
            }
            var cycle = $("input[name='cycle']").val();
            if (min > max) {
                layer.msg("！输入错误，最小值不能大于最大值");
                return;
            }

            $.ajax({
                type: "POST",
                url: "${ctx}/waring/waringCycle/add",
                dataType: "JSON",
                data: {
                    nodeNum: nodeNum,
                    property: property,
                    max: max,
                    min: min,
                    cycle: cycle,
                },
                success: function (result) {
                    if (result.status == 1) {
                        layer.msg("添加成功!");
                        $("input[name='max']").val('');
                        $("input[name='min']").val('');
                        $("input[name='cycle']").val('');
                        setTimeout(location.reload(), 2000);
                    }
                    if (result.status == 0) {
                        layer.msg(result.msg);
                    }
                }
            });


        }
        //删除方法
        function deleteWaringCycle(id) {

            $.ajax({
                type: "POST",
                url: "${ctx}/waring/waringCycle/del",
                dataType: "JSON",
                data: {
                    id: id,
                },
                success: function (result) {
                    if (result.status == 1) {
                        layer.msg("！删除成功");
                        setTimeout("location.reload()", 1000);
                    }
                    if (result.status == 0) {
                        layer.msg("！删除失败");
                    }
                }
            });
        }
        <%--href="${ctx}/waring/waringCycle/form?id=${waringCycle.id}"--%>
        function modify(id) {
            layer.open({
                title: "详细数据",
                type: 2,
                area: ['800px', '600px'],
                fixed: false, //不固定
                maxmin: true,
                content: "${ctx}/waring/waringCycle/form?id=" + id
            });

        }
    </script>
</head>
<body>
	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="#manual" data-toggle="tab"> 手动 </a></li>
		<li><a href="#cycl" data-toggle="tab"> 周期 </a></li>
		<li><a href="#auto" data-toggle="tab"> 智能 </a></li>
	</ul>
	<!-- action="${ctx}/node/node/manual?id=${node.id}" -->
	<div id="tab" class="tab-content">
		<div class="tab-pane fade in active" id="manual">
			<form:form id="manualform" modelAttribute="nodeCollectionCycle"
				method="post" class="form-horizontal">
				<div class="control-group">
					<label class="control-label">节点编号：</label>
					<div class="controls">
						<input name="nodeId" type="text" maxlength="20"
							readonly="readonly" class="input-xlarge" value="${node.nodeNum}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">节点名称：</label>
					<div class="controls">
						<input name="nodeAlise" type="text" readonly="readonly"
							maxlength="20" value="${node.nodeAlise}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">开启：<input id="open"
						type="radio" name="task" value="1"
						<c:if test="${node.openFlag eq '1'}">checked="checked"</c:if> /></label>
					<label class="control-label">关闭：<input id="close"
						type="radio" name="task" value="0"
						<c:if test="${node.openFlag eq '0'}">checked="checked"</c:if> /></label>
				</div>
				<div class="control-group">
					<label class="control-label">状态：</label>
					<div class="controls">
						<c:if test="${node.openFlag eq '0'}">
							<div
								style="background-color: red; border-radius: 5px; height: 22px; width: 50px;">
								已 关 闭</div>
						</c:if>
						<c:if test="${node.openFlag eq '1'}">
							<div
								style="background-color: green; border-radius: 5px; height: 22px; width: 50px;">
								已 开 启</div>
						</c:if>
					</div>
				</div>
				<div class="form-actions">
					<input type="button" class="btn node-switch" value="立即执行" />
				</div>
			</form:form>
		</div>

		<div class="tab-pane fade in" id="cycl">
			<form:form id="inputForm" modelAttribute="nodeCollectionCycle"
				action="${ctx}/timingstrategy/nodeCollectionCycle/save"
				method="post" class="form-horizontal">
				<div class="control-group">
					<label class="control-label">节点编号：</label>
					<div class="controls">
						<input name="nodeId" type="text" readonly="readonly"
							maxlength="20" value="${node.nodeNum}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">节点名称：</label>
					<div class="controls">
						<input name="nodeAlise" type="text" readonly="readonly"
							maxlength="20" value="${node.nodeAlise}" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label">采集周期：</label>
					<div class="controls">
						<ul class="ul-form" type="none">				
							 <select id="cycleTime" name="cycleTime">
									<option  value="5" <c:if test="${min == '5'}"> selected="selected"</c:if>>5分钟</option>
									<option  value="10" <c:if test="${min == '10'}"> selected="selected"</c:if>
									>10分钟</option>
									<option  value="30" <c:if test="${min == '30'}"> selected="selected"</c:if>
									>30分钟</option>
									<option  value="59" <c:if test="${min == '59'}"> selected="selected"</c:if>
									>60分钟</option>
							</select></li> 
						</ul>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">关闭策略：</label>
					<div class="controls">
						<input name="cycleOff" type="text" maxlength="20"
							class="input-medium Wdate " readonly="readonly"
							value="${nodeCollectionCycle.cycleOff}"
							onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">关闭重复：</label>
					<div class="controls">
						<input name="off" value="1" type="checkbox"  <c:if test="${weeks.off[0] eq '1'}"> checked="checked" </c:if> > 星期日 
						<input name="off" value="2" type="checkbox" <c:if test="${weeks.off[1] eq '2' || weeks.off[0] eq '2' }"> checked="checked" </c:if> > 星期一 
						<input name="off" value="3" type="checkbox" <c:if test="${weeks.off[2] eq '3' || weeks.off[0] eq '3' || weeks.off[1] eq '3'}"> checked="checked" </c:if> > 星期二 
						<input name="off" value="4" type="checkbox" <c:if test="${weeks.off[3] eq '4' || weeks.off[0] eq '4' || weeks.off[1] eq '4' || weeks.off[2] eq '4'}"> checked="checked" </c:if> > 星期三 
						<input name="off" value="5" type="checkbox" <c:if test="${weeks.off[4] eq '5' || weeks.off[0] eq '5' || weeks.off[1] eq '5' || weeks.off[2] eq '5' || weeks.off[3] eq '5'}"> checked="checked" </c:if> > 星期四 
						<input name="off" value="6" type="checkbox" <c:if test="${weeks.off[1] eq '6' || weeks.off[2] eq '6' || weeks.off[3] eq '6' || weeks.off[4] eq '6' || weeks.off[5] eq '6' || weeks.off[0] eq '6'}"> checked="checked" </c:if> > 星期五
						<input name="off" value="7" type="checkbox" <c:if test="${weeks.off[1] eq '7' ||  weeks.off[2] eq '7' ||  weeks.off[3] eq '7' ||  weeks.off[4] eq '7' ||  weeks.off[5] eq '7' || weeks.off[6] eq '7' || weeks.off[0] eq '7'}"> checked="checked" </c:if> > 星期六  
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">开启策略：</label>
					<div class="controls">
						<input name="cycleOn" type="text" maxlength="20"
							class="input-medium Wdate " readonly="readonly"
							value="${nodeCollectionCycle.cycleOn}"
							onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">开启重复：</label>
					<div class="controls">
						<input name="on" value="1" type="checkbox"  <c:if test="${weeks.on[0] eq '1'}"> checked="checked" </c:if> > 星期日 
						<input name="on" value="2" type="checkbox" <c:if test="${weeks.on[1] eq '2' || weeks.on[0] eq '2' }"> checked="checked" </c:if> > 星期一 
						<input name="on" value="3" type="checkbox" <c:if test="${weeks.on[2] eq '3' || weeks.on[0] eq '3' || weeks.on[1] eq '3'}"> checked="checked" </c:if> > 星期二 
						<input name="on" value="4" type="checkbox" <c:if test="${weeks.on[3] eq '4' || weeks.on[0] eq '4' || weeks.on[1] eq '4' || weeks.on[2] eq '4'}"> checked="checked" </c:if> > 星期三 
						<input name="on" value="5" type="checkbox" <c:if test="${weeks.on[4] eq '5' || weeks.on[0] eq '5' || weeks.on[1] eq '5' || weeks.on[2] eq '5' || weeks.on[3] eq '5'}"> checked="checked" </c:if> > 星期四 
						<input name="on" value="6" type="checkbox" <c:if test="${weeks.on[1] eq '6' || weeks.on[2] eq '6' || weeks.on[3] eq '6' || weeks.on[4] eq '6' || weeks.on[5] eq '6' || weeks.on[0] eq '6'}"> checked="checked" </c:if> > 星期五
						<input name="on" value="7" type="checkbox" <c:if test="${weeks.on[1] eq '7' ||  weeks.on[2] eq '7' ||  weeks.on[3] eq '7' ||  weeks.on[4] eq '7' ||  weeks.on[5] eq '7' || weeks.on[6] eq '7' || weeks.on[0] eq '7'}"> checked="checked" </c:if> > 星期六  
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">添加时间：</label>
					<div class="controls">
						<input name="addTime" type="text" readonly="readonly"
							maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${nodeCollectionCycle.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">修改时间：</label>
					<div class="controls">
						<input name="updateTime" type="text" readonly="readonly"
							maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${nodeCollectionCycle.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
					</div>
				</div>
				<div class="form-actions">
					<shiro:hasPermission name="timingstrategy:nodeCollectionCycle:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit"
							value="保 存" />&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回"
						onclick="history.go(-1)" />
				</div>
			</form:form>
		</div>

		<div class="tab-pane fade in" id="auto">
			<%--添加--%>
			<form modelAttribute="waringCycle" id="waringCycle"
				class="form-horizontal">

				<div class="control-group">
					<label class="control-label">节点编号：</label>
					<div class="controls">
						<input name="nodeNum" type="text" readonly="readonly"
							maxlength="20" value="${node.nodeNum}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">节点名称：</label>
					<div class="controls">
						<input name="nodeAlise" type="text" readonly="readonly"
							maxlength="20" value="${node.nodeAlise}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">属性：</label>
					<div class="controls">
						<select name="property" style="width: 180px">
							<option value="airTemperature">大气温度</option>
							<option value="airHumidity">大气湿度</option>
							<option value="soilTemperature1">1号采集点温度</option>
							<option value="soilHumidity1">1号采集点湿度</option>
							<option value="soilTemperature2">2号采集点温度</option>
							<option value="soilHumidity2">2号采集点湿度</option>
							<option value="soilTemperature3">3号采集点温度</option>
							<option value="soilHumidity3">3号采集点湿度</option>
							<option value="co2">二氧化碳浓度</option>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">最大值：</label>
					<div class="controls">
						<input type="text" name="max" htmlEscape="false"
							class="input-xlarge " />

					</div>
				</div>
				<div class="control-group">
					<label class="control-label">最小值：</label>
					<div class="controls">
						<input type="text" name="min" htmlEscape="false"
							class="input-xlarge " />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">异常周期：</label>
					<div class="controls">
						<input name="cycle" type="text" placeholder="请输入分钟数"
							htmlEscape="false" maxlength="255" class="input-xlarge " />
					</div>
				</div>
				<div class="form-actions">
					<input onclick="addWaringCycle()" class="btn btn-primary"
						type="button" value="添 加" />&nbsp;
				</div>
			</form>

			<%--列表--%>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th>节点编号</th>
						<th>属性</th>
						<th>最大值</th>
						<th>最小值</th>
						<th>异常周期</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody id="tbody">

					</tbody>
				</table>

		</div>

	</div>
</body>
</html>