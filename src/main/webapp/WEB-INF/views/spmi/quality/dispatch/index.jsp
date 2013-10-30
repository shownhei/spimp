<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>调度专业 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar">
					<button id="create" class="btn btn-small btn-success" title="新建检查">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">新建</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
					<button id="view" class="btn btn-small btn-info disabled">
						<i class="icon-file-alt"></i>
						<span class="hidden-phone">查看</span>
					</button>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="grade-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal" onsubmit="return false;">
						<table class="table-col-4">
							<tbody>
								<tr>
									<td>
										<label>专业</label>
										<input name="category" readonly="readonly" value="调度">
									</td>
									<td>
										<label>年份</label> <select id="yearSelect" name="year">
										</select>
									</td>
									<td>
										<label>月份</label> <select id="monthSelect" name="month">
										</select>
									</td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<table class="grade-table" style="width: 843px;margin-top: 5px">
							<thead>
								<tr>
									<th style="width: 55px">检查项目</th>
									<th style="width: 55px">项目内容</th>
									<th style="width: 260px">检查内容及标准</th>
									<th style="width: 60px">检查方法</th>
									<th style="width: 140px">考核评分办法</th>
									<th style="width: 40px">标准分</th>
									<th style="width: 160px">扣分原因</th>
									<th style="width: 40px">实得分</th>
								</tr>
							</thead>
						</table>
						<div style="height: 500px; overflow: auto;">
							<table id="grade-record-table" class="grade-table">
								<tbody>
									<tr style="background-color: #ccc;">
										<td class="widthTd" style="width: 55px"></td>
										<td class="widthTd" style="width: 55px"></td>
										<td class="widthTd" style="width: 260px"></td>
										<td class="widthTd" style="width: 60px"></td>
										<td class="widthTd" style="width: 140px"></td>
										<td class="widthTd" style="width: 40px"></td>
										<td class="widthTd" style="width: 160px"></td>
										<td class="widthTd" style="width: 40px"></td>
									</tr>
									<tr>
										<td rowspan="3">一、组织机构(5)</td>
										<td>人员配备</td>
										<td>1.调度只至少配备主任、副主任、主任工程师等3名负责人；配备不少于10名调度值班人员</td>
										<td rowspan="3">现场检查</td>
										<td>调度室负责人配备每缺少一人扣1分；调度值班人员每缺少一人扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>人员素质</td>
										<td>2.调度室负责人应具备煤矿安全生产相关专业大专（同等学历）及以上文化程度，并具备三年以上煤矿基层工作经历；调度值班人员应具备煤矿安全生产相关专业中专（同等学历）及以上文化程度，并具有两年以上煤矿基层工作经历</td>
										<td>工作经历、文化程度有一人达不到要求的各扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>资格证书</td>
										<td>3.调度室负责人应经培训并取得安全资格证书；调度室值班人员应经上级培训机构培训并取得上岗资格证书</td>
										<td>无资格证书，没人扣0.5分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">5</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="5">二、调度装备(5)</td>
										<td>综合调度台</td>
										<td>1.调度通行应与上一级调度总机、矿区专网、市话公网通过环路中继或数字中继等方式联网；调度电话进行分模块化设置，应具有汇接、转接、录音、放音、扩音、群呼、组呼、强插、强拆以及同时发起多组电话会议等调度功能。电话录音保存时间不少于一个季度</td>
										<td rowspan="3">全面检查</td>
										<td>调度台电话功能缺少一项扣0.3分；电话薄设置不规范或未设置扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>计算机、传真机等</td>
										<td>2.调度室应配备性能先进、运行稳定的计算机、传真机、打印机，并放在调度台、用于随时接收、传送文件或答应文件、处理各类生产统计报表、存储数据和信息等调度业务</td>
										<td>为配备计算机、传真机、打印机或不能正常使用，本项不得分；计算机、传真机打印机不在调度台扣0.5分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>打屏幕显示装置</td>
										<td>3.配备总面积不少于4平方米的拼接显示系统，显示生产过程动态监测监控信息及各类图表。应保证画面清晰连贯，能够24小时连续运行</td>
										<td>无大屏显示装置不得分；面积、内容不符合要求扣0.5分；大屏不能正常使用，不得分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>供电及备用电源</td>
										<td>4.调度室必须实现双回路供电，配备备用电源；备用电源应与调度总机、传真机、打印机、计算机、应急照明等调度室相关设备相连，保证上述用电设备在常规供电中断时仍能正常使用4小时以上</td>
										<td>现场检查</td>
										<td>为实现双回路供电、没有配诶符合要求的备用电源，该项不得分；常规供电中断后有一种规定的用电设备不能正常工作，扣0.3分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>机房建设</td>
										<td>5.机房的面积、电源、防雷、接地、消防、空调等设备安装及布线等满足调度工作需要，并符合有关规定</td>
										<td>全面检查</td>
										<td>面积狭小、难以保证工作需要、不符合有关规定的，扣0.5分；其它每项不合格扣0.3 分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">5</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="16">三、调度管理(36)</td>
										<td>调度管理制度</td>
										<td>1.各类人员岗位责任制、调度值班制度、调度交接班制度、调度逐级汇报制度、安全事故汇报制度、调度员下井（下基层）管理制度、调度会议管理制度、调度资料保存管理制度、调度质量标准化管理（验收）制度、业务保安制度、调度业务学习制度、监测监控系统报警信息处理制的等。各项管理制度齐全，内容具体、责任明确，并装订成册；应备有矿井灾害预防和处理计划、应急救援预案、《煤矿安全规程》等法规、文件
										</td>
										<td rowspan="9">查阅资料</td>
										<td>没少一种管理制度扣0.5分；为未装订成册扣0.3分；调度台没少一种应具备的安全法规、文件，扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="4">协调指挥生产</td>
										<td>2.组织完成生产作业计划完成。完成原煤、洗精煤产量、掘进总进尺、开拓进尺等月年度计划</td>
										<td>抽查2个月，每一项指标未完成月计划扣1分，未完成年度计划扣2分</td>
										<td align="center">4</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>3.原煤生产百万吨死亡率</td>
										<td>超过下大的控制指标本项不得分</td>
										<td align="center">4</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>4.及时有效解决安全生产中出现的问题，并详细记录解决问题的时间、地点、参加人、内容、处理意见等</td>
										<td>查相应记录本，任查2个月，无记录本扣2分；缺少一次解决问题的记录扣0.5分；对问题有记录无处理意见的扣0.5分；记录不完整，每次扣0.3分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>5.原煤库存管理：掌握生产、外运和库存量。调度室每月负责组织盘清原煤库存量、要有详细的测量图纸及计算资料、盘库人员签名单，并填写盘库月报表上报上一级调度室</td>
										<td>有一个月未盘点煤库扣1分；盘库月报表为按时上报或数据不准确，没次扣0.5分；缺少一份资料扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="4">业务保安</td>
										<td>6.掌握安全生产动态和安全年生产重点工作：掌握矿井供电、通风、（供）排水、提升、运输、采掘工作面等动态情况，掌握巷道贯通、初（末）次放顶、过地质构造、采面安装（拆除）、停产检修、大型设备检修、恢复生产、重点工程等情况；掌握老窑区、火区处理等情况；详细记录相关工程进展和安全技术措施落实情况。对上述工作要了解有关安全技术措施并监督落实，了解现场工作人员及负责人、工作进度、终结等情况，做好记录；对发生的事故，要详细记录汇报人、事故发生的时间、地点、事故情况、处理结果等内容
										</td>
										<td>不能掌握安全生产动态情况，有一次扣1分；相关工程记录没缺少一种，扣1分；有记录无处理结果的每一项扣0.5分；记录内容不全的每一项扣0.3分</td>
										<td align="center">4</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>7.做好文件的接收和处理工作；对上级下达的安全生产文件、通知、通报、传真等，做号登记、签收、报送及处理工作，记录接收时间、单位名称、文号、主题内容、接收人、领导批示、处理情况，下发或转发时间、接收人等内容，做好文件的分类存档等工作</td>
										<td>未建立文件收、发记录本，扣1分；无记录一次扣0.5分；记录不完整、内容部清楚等每有一次扣0.3分；文件为分类存档的扣1分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>8.掌握检测监控、工业视屏监控系统的动态情况，掌握压风自救、供水施救、井下作业人员管理系统、通信联络和井下避险系统的运行情况；及时组织对系统报警、异常信息的处理，记录报警情况、采取的措施、责任人、处理结果等</td>
										<td>检查相关的记录，无记录扣1分，记录不完整，每次扣0.5分。因调度工作失误或造成重大损失，本项不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>9.赋予调度人员以下权限：在遇到重大险情时第一时间下达停工或停产撤人命令的直接决策和指挥权；在接到发生或可能发生矿井瓦斯、有害气体中毒、透水等事故报警时，调度人员应立即下达命令，组织危险区域的人员按避险路线撤离；发生矿井火灾事故时、调度人员应立即指挥组织现场人员处置危险区域人员的撤离；对井下停电、停风等影响安全生产的重大隐患和问题及时下达调度指令
										</td>
										<td>查看调度人员应急处置《矿长授权书》，没有授权书，本项不得分；在遇到重大险情时没有下达撤人命令的，本项不得分；对影响生产的重大隐患及问题没有及时下达调度指令的，本项不得分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>深入现场</td>
										<td>10.调度人员要经常深入生产现场调查研究，及时协调解决相关问题，并做好下井（现场）记录，记录时间、姓名、地点、现场情况、发生的问题、处理情况等；要建立调度人员下井统计表。调度人员下井每人每月不少于5次</td>
										<td rowspan="2">查阅相关资料</td>
										<td>无下井记录或无调度人员下井统计表各减1分；调度人员下井每少一次扣0.5分，缺一次下井记录扣0.3分；下井记录不内容不足完整过、对对发现的问题无处理意见，每次扣0.3分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>业务学及培训</td>
										<td>11.调度室应制定年度培训学习计划，按计划组织调度人员的培训工作。每月组织不少于2次集中业务学习，每次时间不少于2小时；要做好培训学习记录，记录学习时间、参加人、学习内容等；结合学习内容每月组织一次考试，试卷和成绩统计表要装订存档</td>
										<td>缺年度培训学习计划减1分；培训、学习记录少一次扣0.5分；培训学习没有达到规定时间的，一次扣1分；考试没考一次扣1分；培训学习记录有一次不完整或没有成绩统计表，一次扣0.5分；少一人试卷扣0.2分；试卷重复或雷同的扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>图表</td>
										<td>12.调度室应备有矿井地质和水文地图，井上下对照图，采掘工程平面图，通风系统图，井下运输系统图，监测监控布装备布置图，排水、防尘、防火注浆、压风、充填、抽放瓦斯等管路系统图，井下通信系统图，井上下配电系统图和井下电气设备布置图。应有事故报告程序图（表），应急电话表、领导值、带班表，通讯录，采掘衔接计划表，领导下井带班统计表。图标必须及时填绘和修订，于实际情况相符，图标必须归档管理
										</td>
										<td rowspan="3">查阅资料</td>
										<td>缺少一种图、表扣0.5分；填绘或更换不及时、于实际不相符、内容不完整、图画不清晰，有一处扣0.3分（采用计算机绘制显示图标功能符合使用要求的评分相同）</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>台账</td>
										<td>13.台账①综合台账调度综合台账要记录当日矿井及各队（班）原煤产量、进尺计划及完成情况，洗选外运完成情况，各采、掘、开工作面的安全、生产情况，值班、跟班领导、出入井人数等。要求内容完整，数据准确，账面整洁，字迹工整。（综合台账可采用计算机电子台账）②统计台账调度室要建立以下月统计台账：a.原煤产量、外运（入洗）、库存量当月及累计完成台账；b.总进尺、开拓进尺当月及累计完成台账；c.重点采、掘开队当月及累计完成台账；d.重大生产事故当月及累计统计台账。要求各统计台账及时填写，数据准确、清晰，与有关统计数据一致。（可采用计算机电子台账）
										</td>
										<td>无综合台账不得分；调账缺少内容或填写不完整、数据不准确、账面不整洁、字迹不工整，每处扣0.2分（使用计算机电子台账评分相同）缺一种统计台账扣0.5分；缺少内容或填写不完整、数据不准确、账面不整洁、字迹不工整，每处扣0.2分（使用计算机电子台账评分相同）</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>记录薄</td>
										<td>"14.记录薄 调度室应建立下列记录薄
											①调度综合记录按时间顺序记录当班生产、安全等方面反应的问题及协调处理结果的情况；②领导值班记录：每日矿领导值班工作情况记录（有值班领导填写）；③调度员交接班记录；④安全生产（视频）会议记录，包括起止时间、地点、参加人、记录人、会议内容等；⑤调度业务会议记录：调度室每月至少召开一次调度业务工作会议，对工作进行总结分析，安排下一步主要工作；⑥人员伤亡事故记录：记录伤亡事故发生的时间、地点、汇报人、伤员姓名、受伤部位及程度、事故类别、抢救处理过程、上报时间、接收人；⑦飞伤亡事故记录：记录非伤亡事故发生的时间、地点、汇报人、事故类别、现场处理负责人、处理结果、上报时间、接收人等
											各种记录要求准确及时、内容完整、条例清楚、字迹工整清晰、具备原始性"</td>
										<td>缺少一种记录薄扣0.5分；每有一次记录不准、不完整、混乱不清、存档不全扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>信息公示</td>
										<td>"15.调度动态信息公示 内容包括原煤产量、开掘进尺、外运量、原煤库存量、值（带）班领导、下井人数等信息。 要求内容准确，并及时更新"</td>
										<td>现场检查</td>
										<td>无调度动态信息公示，本项不得分；缺一项内容扣0.2分；一项数据不准确扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>达标建设</td>
										<td>16.调度室要制定信息调度质量标准化建设年度在计划。每月组织达标自检一次，对存在问题要制定和落实整改措施</td>
										<td>查阅资料</td>
										<td>没有制定年度工作计划，扣1分；每月一次自查，扣0.5分；对存在的问题没有制定和落实整改措施，扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">36</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="9">四、调度汇报(25)</td>
										<td>班汇报</td>
										<td>1.当班采、掘班队长每班应不少于2次向矿调度室汇报生产和安全情况；矿调度每班应向上一级调度室汇报当班生产组织情况及安全生产情况。遇有生产安全事故、重大问题或紧急情况立即报告值班领导、本单位负责人和上一级调度室</td>
										<td rowspan="2">查阅统计报表、相关文档及计算机信息系统</td>
										<td>任查一个月相关记录和汇报，当班无汇报，每一次扣1分；汇报不及时或有错误的，每一次扣0.5分</td>
										<td align="center">5</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>日汇报</td>
										<td>2.每日调度统计报表、安全生产信息、煤矿负责人值班带班情况等经审核后，调度室按规定及时报上一级调度室，并在日调度会上通报生产组织和安全生产情况。按时编制印发安全生产调度日报</td>
										<td>抽查2个月相关记录记录和汇报材料，日汇报每缺一次扣1分；汇报不及时或有错误，每一次扣0.5分，每确一份生产生产日报扣 0.5分</td>
										<td align="center">5</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>旬(周)汇报</td>
										<td>3.按照规定时间和要求，将本旬 （周）调度报表、安全生产情况、生产组织情况、存在的主要问题及采取的措施，重点工作、下旬(周)中重点工作安排等报上一级调度室</td>
										<td rowspan="4">查阅资料</td>
										<td>任查2个月文档记录，有一次未上报扣1分、迟报一次扣0.5分；内容不全面每缺一项扣0.3分；发现一处错误扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>月汇报</td>
										<td>4.调度室将本生产计划及指标完成情况、安全生产情况、存在问题、安全隐患及采取的主要措施、下月重点工作安排等进行分析总结并安按照规定时间和要求报上一级调度室。有关调度统计报表、领导值班带班表等可与月度汇报材料同时上报</td>
										<td>任查3个月文档记录，有一次未上报扣1分、迟报一次扣0.5分；内容不全面每缺一项扣0.3分；发现一处错误扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>专题汇报</td>
										<td>5.专题汇报:按上级要求及时汇报，形成书面材料并按时间顺序存档。 以下事项应在事前汇报上一级调度室：
											①节假日停产放假、检修安排，领导值班表；停、复产安全技术措施；②矿井停产（停电）大修安排及安全措施；③主要大型设备检修（更换）安排及安全措施；④拆启密闭排放瓦斯，重大排（探）放水安排及安全技术措施；⑤初（末）次放顶、巷道贯通安全技术措施等。 其它专题汇报按上级要求完成</td>
										<td>查相关资料，少一次汇报扣0.5分；迟报一次扣0.3分；汇报材料内容不全、缺安全技术措施的，有一项各扣0.3分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>季节性汇报</td>
										<td>6.季节性汇报 对雨季防汛、防雷电、冬季防寒、防冻，冬、春季防火等季节性工作安排要按规定要求报上一级调度室，发生紧急情况要立即报告值班领导、本单位负责人和上一级调度室</td>
										<td>查相关资料，无季节性工作安排，本项不得分；缺一项扣0.5分；汇报材料不完整、存档材料不完整、存档资料不全有一项各扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="2">事故汇报</td>
										<td>7.人身伤亡事故汇报 ①发生重伤及3人以下（不含3人）伤亡事故，调度室应立即报告值班领导、煤矿负责人，组织抢救救援，并在30分钟内向上一级调度室汇报，在20小时内将伤亡事故汇报卡报上一级调度室。 ②发生3人及以上伤亡或被困事故，要立即启动事故应急预案，在接到报告立即报告上一级调度室</td>
										<td rowspan="3">查矿相关事故原始记录、录音机资料</td>
										<td>重伤、一般事故迟报一次扣0.5分；迟报超过2小时，一次扣1分；3人及以上伤亡或被困事故迟报一次扣2分，迟报超过2小时本项不得分</td>
										<td align="center">4</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>8.非伤亡事故汇报 ①煤矿发生影响盘区（采区）生产超过1小时非人身伤亡生产事故，矿调度室须在4小时内向上一级调度室报告。 ②煤矿发生影响全矿井生产超过1小时非人身伤亡事故，矿调度室要在30分钟内向上一级调度室报告</td>
										<td>发生影响盘区（采区）生产超过4小时事故迟报一次扣0.5分；发生影响全矿井生产超过1小时的事故迟报一次扣1分；有一次没有报告的本项不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>突发事件汇报</td>
										<td>9.发生突发事件，调度室要在事件发生30分钟内向值班领导、矿负责人和上一级调度室报告。汇报内容包括时间、地点、事件类型、影响程度等，并根据事态发展随时汇报</td>
										<td>突发性时间没有向上一级调度室报告的一次扣1分；迟报一次扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">25</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="14">五、调度信息化管理(25)</td>
										<td rowspan="3">通信联络系统</td>
										<td>1.有线通信 矿井必须装备调度交换机，调度交换机应支持通过数字中继或通过IP语音网关实现联网功能，并应与上一级调度总机、矿区行政电话、井下移动通信等系统互联互通。 下井电话必须经过安全耦合器，电话机应是本质安全型。
											井下采掘工作面、变电所、上下山绞车房、主要水泵房、井底车场、运输调度站、带式输送机及中国控制硐室、永久避难硐室、井下水平和采取最高点、主要硐室等重要工作地点应设置调度电话。
											地面矿领导值班室、科队值班室、地面变电站（所）、通风机房、瓦斯集中抽放站、主副井绞车房、水泵房、、井口急救站、小车队应设置调度电话</td>
										<td rowspan="14">查阅资料 现场检查</td>
										<td>调度交换机未于上一级调度部门总机联网扣0.5分；未与矿区行政电话联网扣0.5分； 井下电话未经过安全耦合器，电话机不是本质安全型，本项不得分。每少一部要求的调度电话（电话不通视同未安装）扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>2.井下应急广播系统
											矿井必须装备井下应急广播通信系统并确保正常运行：①主机与广播端应设置在调度台；②在井下人员相对集中位置如采掘工作面、井底车场、井底换装站、主要候车点、变电所、永久避难硐室等地点应设置有广播终端；③系统及广播终端应为本质安全型并具有MA标志证书；④语音应清晰，最大音量应不小于90分贝；⑤支持在断电情况下连续播放不小于2小时；⑥应实现于调度双向对讲、区域对讲、监听等功能；⑦与矿井调度电话系统互联互通
										</td>
										<td>无井下广播通信系统不得分；主机及广播端为设置在调度台扣0.5分；井下一处不能正常运行扣0.3分；一处人员集中位置未设置广播终端扣0.5分；音量不达标扣0.3分；在断电情况下连续播放不足2小时扣0.5分；其它功能缺失一项扣0.5分。
											系统及广播终端不是本质安全型或不具有MA标志证书，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>3.井下移动通信系统
											矿井必须装备井下移动通信系统，并与矿井调度、行政电话、井下广播通信系统互联互通；井上下主要安全生产工作人员应配备移动通信电话；系统支持在断电情况下连续工作不小于2小时；井下移动通信系统应具备有录音、放音、扩音、群呼、组呼、短信等功能；系统级移动通信终端应为本质安全型，并具有MA标志证书</td>
										<td>无井下移动通信系统不得分；功能缺失一项扣0.2分。系统及移动通信电话不是本质安全型或不具有MA标志证书，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="2">视频系统</td>
										<td>4.视频会议系统 矿井应装备视频会议系统，系统应与上级管理部门（集团公司、子分公司、主题企业）之间联网；保证系统24小时随时使用；确保系统画面、语音清晰连贯、能正常使用</td>
										<td>无视频会议系统或系统为联网，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>5.工业视频系统
											煤矿应装备工业视频系统，在井下主要胶带运输机头，盘区皮带转载点，井底车场、井下变电所、主副井井口、主通风机房、永久避难硐室等和井上主要工作场所如洗煤场、外运装车点、工业广场、办公楼等地点安装工业视频摄像头，并在调度大屏幕显示；图像储存时间应不小于7天，图像信号应能上传上一级调度室</td>
										<td>无工业电视系统不得分；工业电视摄像头不按要求安装或不按规定提供上传信号扣0.5分；图像储存时间达不到要求的扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="4">监测监控系统</td>
										<td>6.安全监控系统 矿井必须安装安全监控系统，调度室应有安全监控系统专用显示终端；设备的安装使用管理应符合国家标准要求</td>
										<td>调度室未设置系统专用显示终端，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>7.井下作业人员管理系统 矿井必须安装井下作业人员管理系统，调度室应有系统显示终端，准确显示井下总人数及重点区域人员分布情况，设备的安装使用管理应符合国家标准要求</td>
										<td>调度室未设置系统专用显示终端，不得分；不能准确显示井下总人数及重点区域人员分布情况，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>8.煤炭产量监控系统 矿井必须安装煤炭产量监控系统及配套视频装置，调度室应有系统显示终端，设备的安装使用管理应符合国家标准要求</td>
										<td>调度室未设置系统专用显示终端，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>9.安全监管执法与决策支持系统 矿井必须安装安全监管执法与决策支持系统，调度室应配备系统显示终端，数据采集应符合山西省煤炭工业厅有关要求</td>
										<td>调度室未设置系统专用显示终端，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>井下工业环网</td>
										<td>10.为保证井下各系统传输数率和传输的稳定性，矿井应安装井下工业环网</td>
										<td>核定生产能力在90万吨/年及以上的矿未安装，不得分；发现中断6小时以下一次扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="2">安全生产调度管理系统</td>
										<td>11.应用计算机处理日常报表 矿井要能够使用计算机处理日常报表：①生产日报；②旬、月汇报；③外运日报；④矿领导逐月带班下井统计表；⑤生产事故统计表；⑥采煤队工作面衔接情况表；⑦开掘队工作面衔接情况表；⑧初（末）次放顶及巷道贯通预报表；⑨达标规划、培训计划；⑩工作总结及专题汇报材料
										</td>
										<td>一项未使用计算机处理扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>12.调度管理信息系统 矿井应编制能适应调度管理工作需要的调度管理信息系统，并应能与上一级调度部门联网，实现生产、安全、营运、外运等日常报表信息的计算机处理并实现实时传输，满足调度业务管理和使用要求</td>
										<td>无调度信息管理系统，不得分。未与上一级调度部门联网减1分；系统功能不全或功能不正常，每有一项扣0.5分；汇报不及时，上报不准确，一次扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>生产过程动态监测</td>
										<td>13.应将矿井提升、运输、供电、通风、主排水、抽放、压风、采掘工作面设备运转 等系统运行情况在计算机或大屏幕上动态模拟显示</td>
										<td>矿井提升、运输、供电、通风、主排水、抽放、压风、采掘工作面设备运转等系统运行情况动态模拟显示，每缺一处扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>综合信息管理平台</td>
										<td>14.矿井应建立综合信息管理平台。平台内容包括:①日常工作所使用的各种台账、报表；②集成的监测监控子系统；③集成的生产过程动态监控子系统等</td>
										<td>核定生产能力在300万吨/年及以上矿井无综合信息 管理平台，不得分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">25</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="3">六、工作场所及环境文明(4)</td>
										<td>调度室、视频会议室</td>
										<td>1.调度室面积不小于60平方米，有满足中层及以上领导参加的视频会议室，调度室、视频会议室设备、设施的安装符合有关规程要求并保持整洁</td>
										<td rowspan="3">现场检查</td>
										<td>调度室面积小于60平方米的，扣1分；其它每项不合格的各扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>工作环境</td>
										<td>2.调度室、视频会议室、办公室应清洁整齐，物放有序，图纸、资料、文件摆放整齐，图表规范统一</td>
										<td>图表、资料等杂乱无章、不规范统一，一项扣0.3分；环境卫生差扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>文明办公</td>
										<td>3.调度人员统一着装并佩戴胸牌，恪尽职守，工作认真负责，做到文明用语、不卑不亢，礼貌待人</td>
										<td>根据现场情况掌握，用语不文明、工作不认真等，有一项次扣0.3分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">4</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">合计</td>
										<td align="center">100</td>
										<td colspan="2"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</form>
				</div>
				<div id="create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="create-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 编辑 -->
	<div id="edit-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal" onsubmit="return false;"></form>
				</div>
			</div>
			<div id="edit-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="edit-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="edit-save" class="btn btn-small btn-primary">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 删除 -->
	<div id="remove-modal" class="modal modal-xs hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 删除
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 提示：确认删除选中的评分？
				</div>
			</div>
			<div id="remove-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="remove-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="remove-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 查看 -->
	<div id="view-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="light-blue">
				<i class="icon-file-alt"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="view-form" class="form-horizontal" onsubmit="return false;">
						<table class="table-col-4">
							<tbody>
								<tr>
									<td>
										<label>专业</label>
										<input name="category" readonly="readonly" value="调度">
									</td>
									<td>
										<label>年份</label>
										<input name="year" readonly="readonly">
									</td>
									<td>
										<label>月份</label>
										<input name="month" readonly="readonly">
									</td>
									<td>
										<label>合计</label>
										<input name="score" readonly="readonly">
									</td>
								</tr>
							</tbody>
						</table>
						<table class="grade-table" style="width: 843px;margin-top: 5px">
							<thead>
								<tr>
									<th style="width: 55px">检查项目</th>
									<th style="width: 55px">项目内容</th>
									<th style="width: 260px">检查内容及标准</th>
									<th style="width: 60px">检查方法</th>
									<th style="width: 140px">考核评分办法</th>
									<th style="width: 40px">标准分</th>
									<th style="width: 160px">扣分原因</th>
									<th style="width: 40px">实得分</th>
								</tr>
							</thead>
						</table>
						<div style="height: 500px; overflow: auto;">
							<table id="grade-record-view-table" class="grade-table">
								<tbody>
									<tr style="background-color: #ccc;">
										<td class="widthTd" style="width: 55px"></td>
										<td class="widthTd" style="width: 55px"></td>
										<td class="widthTd" style="width: 260px"></td>
										<td class="widthTd" style="width: 60px"></td>
										<td class="widthTd" style="width: 140px"></td>
										<td class="widthTd" style="width: 40px"></td>
										<td class="widthTd" style="width: 160px"></td>
										<td class="widthTd" style="width: 40px"></td>
									</tr>
									<tr>
										<td rowspan="3">一、组织机构(5)</td>
										<td>人员配备</td>
										<td>1.调度只至少配备主任、副主任、主任工程师等3名负责人；配备不少于10名调度值班人员</td>
										<td rowspan="3">现场检查</td>
										<td>调度室负责人配备每缺少一人扣1分；调度值班人员每缺少一人扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>人员素质</td>
										<td>2.调度室负责人应具备煤矿安全生产相关专业大专（同等学历）及以上文化程度，并具备三年以上煤矿基层工作经历；调度值班人员应具备煤矿安全生产相关专业中专（同等学历）及以上文化程度，并具有两年以上煤矿基层工作经历</td>
										<td>工作经历、文化程度有一人达不到要求的各扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>资格证书</td>
										<td>3.调度室负责人应经培训并取得安全资格证书；调度室值班人员应经上级培训机构培训并取得上岗资格证书</td>
										<td>无资格证书，没人扣0.5分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">5</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="5">二、调度装备(5)</td>
										<td>综合调度台</td>
										<td>1.调度通行应与上一级调度总机、矿区专网、市话公网通过环路中继或数字中继等方式联网；调度电话进行分模块化设置，应具有汇接、转接、录音、放音、扩音、群呼、组呼、强插、强拆以及同时发起多组电话会议等调度功能。电话录音保存时间不少于一个季度</td>
										<td rowspan="3">全面检查</td>
										<td>调度台电话功能缺少一项扣0.3分；电话薄设置不规范或未设置扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>计算机、传真机等</td>
										<td>2.调度室应配备性能先进、运行稳定的计算机、传真机、打印机，并放在调度台、用于随时接收、传送文件或答应文件、处理各类生产统计报表、存储数据和信息等调度业务</td>
										<td>为配备计算机、传真机、打印机或不能正常使用，本项不得分；计算机、传真机打印机不在调度台扣0.5分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>打屏幕显示装置</td>
										<td>3.配备总面积不少于4平方米的拼接显示系统，显示生产过程动态监测监控信息及各类图表。应保证画面清晰连贯，能够24小时连续运行</td>
										<td>无大屏显示装置不得分；面积、内容不符合要求扣0.5分；大屏不能正常使用，不得分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>供电及备用电源</td>
										<td>4.调度室必须实现双回路供电，配备备用电源；备用电源应与调度总机、传真机、打印机、计算机、应急照明等调度室相关设备相连，保证上述用电设备在常规供电中断时仍能正常使用4小时以上</td>
										<td>现场检查</td>
										<td>为实现双回路供电、没有配诶符合要求的备用电源，该项不得分；常规供电中断后有一种规定的用电设备不能正常工作，扣0.3分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>机房建设</td>
										<td>5.机房的面积、电源、防雷、接地、消防、空调等设备安装及布线等满足调度工作需要，并符合有关规定</td>
										<td>全面检查</td>
										<td>面积狭小、难以保证工作需要、不符合有关规定的，扣0.5分；其它每项不合格扣0.3 分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">5</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="16">三、调度管理(36)</td>
										<td>调度管理制度</td>
										<td>1.各类人员岗位责任制、调度值班制度、调度交接班制度、调度逐级汇报制度、安全事故汇报制度、调度员下井（下基层）管理制度、调度会议管理制度、调度资料保存管理制度、调度质量标准化管理（验收）制度、业务保安制度、调度业务学习制度、监测监控系统报警信息处理制的等。各项管理制度齐全，内容具体、责任明确，并装订成册；应备有矿井灾害预防和处理计划、应急救援预案、《煤矿安全规程》等法规、文件
										</td>
										<td rowspan="9">查阅资料</td>
										<td>没少一种管理制度扣0.5分；为未装订成册扣0.3分；调度台没少一种应具备的安全法规、文件，扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="4">协调指挥生产</td>
										<td>2.组织完成生产作业计划完成。完成原煤、洗精煤产量、掘进总进尺、开拓进尺等月年度计划</td>
										<td>抽查2个月，每一项指标未完成月计划扣1分，未完成年度计划扣2分</td>
										<td align="center">4</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>3.原煤生产百万吨死亡率</td>
										<td>超过下大的控制指标本项不得分</td>
										<td align="center">4</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>4.及时有效解决安全生产中出现的问题，并详细记录解决问题的时间、地点、参加人、内容、处理意见等</td>
										<td>查相应记录本，任查2个月，无记录本扣2分；缺少一次解决问题的记录扣0.5分；对问题有记录无处理意见的扣0.5分；记录不完整，每次扣0.3分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>5.原煤库存管理：掌握生产、外运和库存量。调度室每月负责组织盘清原煤库存量、要有详细的测量图纸及计算资料、盘库人员签名单，并填写盘库月报表上报上一级调度室</td>
										<td>有一个月未盘点煤库扣1分；盘库月报表为按时上报或数据不准确，没次扣0.5分；缺少一份资料扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="4">业务保安</td>
										<td>6.掌握安全生产动态和安全年生产重点工作：掌握矿井供电、通风、（供）排水、提升、运输、采掘工作面等动态情况，掌握巷道贯通、初（末）次放顶、过地质构造、采面安装（拆除）、停产检修、大型设备检修、恢复生产、重点工程等情况；掌握老窑区、火区处理等情况；详细记录相关工程进展和安全技术措施落实情况。对上述工作要了解有关安全技术措施并监督落实，了解现场工作人员及负责人、工作进度、终结等情况，做好记录；对发生的事故，要详细记录汇报人、事故发生的时间、地点、事故情况、处理结果等内容
										</td>
										<td>不能掌握安全生产动态情况，有一次扣1分；相关工程记录没缺少一种，扣1分；有记录无处理结果的每一项扣0.5分；记录内容不全的每一项扣0.3分</td>
										<td align="center">4</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>7.做好文件的接收和处理工作；对上级下达的安全生产文件、通知、通报、传真等，做号登记、签收、报送及处理工作，记录接收时间、单位名称、文号、主题内容、接收人、领导批示、处理情况，下发或转发时间、接收人等内容，做好文件的分类存档等工作</td>
										<td>未建立文件收、发记录本，扣1分；无记录一次扣0.5分；记录不完整、内容部清楚等每有一次扣0.3分；文件为分类存档的扣1分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>8.掌握检测监控、工业视屏监控系统的动态情况，掌握压风自救、供水施救、井下作业人员管理系统、通信联络和井下避险系统的运行情况；及时组织对系统报警、异常信息的处理，记录报警情况、采取的措施、责任人、处理结果等</td>
										<td>检查相关的记录，无记录扣1分，记录不完整，每次扣0.5分。因调度工作失误或造成重大损失，本项不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>9.赋予调度人员以下权限：在遇到重大险情时第一时间下达停工或停产撤人命令的直接决策和指挥权；在接到发生或可能发生矿井瓦斯、有害气体中毒、透水等事故报警时，调度人员应立即下达命令，组织危险区域的人员按避险路线撤离；发生矿井火灾事故时、调度人员应立即指挥组织现场人员处置危险区域人员的撤离；对井下停电、停风等影响安全生产的重大隐患和问题及时下达调度指令
										</td>
										<td>查看调度人员应急处置《矿长授权书》，没有授权书，本项不得分；在遇到重大险情时没有下达撤人命令的，本项不得分；对影响生产的重大隐患及问题没有及时下达调度指令的，本项不得分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>深入现场</td>
										<td>10.调度人员要经常深入生产现场调查研究，及时协调解决相关问题，并做好下井（现场）记录，记录时间、姓名、地点、现场情况、发生的问题、处理情况等；要建立调度人员下井统计表。调度人员下井每人每月不少于5次</td>
										<td rowspan="2">查阅相关资料</td>
										<td>无下井记录或无调度人员下井统计表各减1分；调度人员下井每少一次扣0.5分，缺一次下井记录扣0.3分；下井记录不内容不足完整过、对对发现的问题无处理意见，每次扣0.3分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>业务学及培训</td>
										<td>11.调度室应制定年度培训学习计划，按计划组织调度人员的培训工作。每月组织不少于2次集中业务学习，每次时间不少于2小时；要做好培训学习记录，记录学习时间、参加人、学习内容等；结合学习内容每月组织一次考试，试卷和成绩统计表要装订存档</td>
										<td>缺年度培训学习计划减1分；培训、学习记录少一次扣0.5分；培训学习没有达到规定时间的，一次扣1分；考试没考一次扣1分；培训学习记录有一次不完整或没有成绩统计表，一次扣0.5分；少一人试卷扣0.2分；试卷重复或雷同的扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>图表</td>
										<td>12.调度室应备有矿井地质和水文地图，井上下对照图，采掘工程平面图，通风系统图，井下运输系统图，监测监控布装备布置图，排水、防尘、防火注浆、压风、充填、抽放瓦斯等管路系统图，井下通信系统图，井上下配电系统图和井下电气设备布置图。应有事故报告程序图（表），应急电话表、领导值、带班表，通讯录，采掘衔接计划表，领导下井带班统计表。图标必须及时填绘和修订，于实际情况相符，图标必须归档管理
										</td>
										<td rowspan="3">查阅资料</td>
										<td>缺少一种图、表扣0.5分；填绘或更换不及时、于实际不相符、内容不完整、图画不清晰，有一处扣0.3分（采用计算机绘制显示图标功能符合使用要求的评分相同）</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>台账</td>
										<td>13.台账①综合台账调度综合台账要记录当日矿井及各队（班）原煤产量、进尺计划及完成情况，洗选外运完成情况，各采、掘、开工作面的安全、生产情况，值班、跟班领导、出入井人数等。要求内容完整，数据准确，账面整洁，字迹工整。（综合台账可采用计算机电子台账）②统计台账调度室要建立以下月统计台账：a.原煤产量、外运（入洗）、库存量当月及累计完成台账；b.总进尺、开拓进尺当月及累计完成台账；c.重点采、掘开队当月及累计完成台账；d.重大生产事故当月及累计统计台账。要求各统计台账及时填写，数据准确、清晰，与有关统计数据一致。（可采用计算机电子台账）
										</td>
										<td>无综合台账不得分；调账缺少内容或填写不完整、数据不准确、账面不整洁、字迹不工整，每处扣0.2分（使用计算机电子台账评分相同）缺一种统计台账扣0.5分；缺少内容或填写不完整、数据不准确、账面不整洁、字迹不工整，每处扣0.2分（使用计算机电子台账评分相同）</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>记录薄</td>
										<td>"14.记录薄 调度室应建立下列记录薄
											①调度综合记录按时间顺序记录当班生产、安全等方面反应的问题及协调处理结果的情况；②领导值班记录：每日矿领导值班工作情况记录（有值班领导填写）；③调度员交接班记录；④安全生产（视频）会议记录，包括起止时间、地点、参加人、记录人、会议内容等；⑤调度业务会议记录：调度室每月至少召开一次调度业务工作会议，对工作进行总结分析，安排下一步主要工作；⑥人员伤亡事故记录：记录伤亡事故发生的时间、地点、汇报人、伤员姓名、受伤部位及程度、事故类别、抢救处理过程、上报时间、接收人；⑦飞伤亡事故记录：记录非伤亡事故发生的时间、地点、汇报人、事故类别、现场处理负责人、处理结果、上报时间、接收人等
											各种记录要求准确及时、内容完整、条例清楚、字迹工整清晰、具备原始性"</td>
										<td>缺少一种记录薄扣0.5分；每有一次记录不准、不完整、混乱不清、存档不全扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>信息公示</td>
										<td>"15.调度动态信息公示 内容包括原煤产量、开掘进尺、外运量、原煤库存量、值（带）班领导、下井人数等信息。 要求内容准确，并及时更新"</td>
										<td>现场检查</td>
										<td>无调度动态信息公示，本项不得分；缺一项内容扣0.2分；一项数据不准确扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>达标建设</td>
										<td>16.调度室要制定信息调度质量标准化建设年度在计划。每月组织达标自检一次，对存在问题要制定和落实整改措施</td>
										<td>查阅资料</td>
										<td>没有制定年度工作计划，扣1分；每月一次自查，扣0.5分；对存在的问题没有制定和落实整改措施，扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">36</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="9">四、调度汇报(25)</td>
										<td>班汇报</td>
										<td>1.当班采、掘班队长每班应不少于2次向矿调度室汇报生产和安全情况；矿调度每班应向上一级调度室汇报当班生产组织情况及安全生产情况。遇有生产安全事故、重大问题或紧急情况立即报告值班领导、本单位负责人和上一级调度室</td>
										<td rowspan="2">查阅统计报表、相关文档及计算机信息系统</td>
										<td>任查一个月相关记录和汇报，当班无汇报，每一次扣1分；汇报不及时或有错误的，每一次扣0.5分</td>
										<td align="center">5</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>日汇报</td>
										<td>2.每日调度统计报表、安全生产信息、煤矿负责人值班带班情况等经审核后，调度室按规定及时报上一级调度室，并在日调度会上通报生产组织和安全生产情况。按时编制印发安全生产调度日报</td>
										<td>抽查2个月相关记录记录和汇报材料，日汇报每缺一次扣1分；汇报不及时或有错误，每一次扣0.5分，每确一份生产生产日报扣 0.5分</td>
										<td align="center">5</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>旬(周)汇报</td>
										<td>3.按照规定时间和要求，将本旬 （周）调度报表、安全生产情况、生产组织情况、存在的主要问题及采取的措施，重点工作、下旬(周)中重点工作安排等报上一级调度室</td>
										<td rowspan="4">查阅资料</td>
										<td>任查2个月文档记录，有一次未上报扣1分、迟报一次扣0.5分；内容不全面每缺一项扣0.3分；发现一处错误扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>月汇报</td>
										<td>4.调度室将本生产计划及指标完成情况、安全生产情况、存在问题、安全隐患及采取的主要措施、下月重点工作安排等进行分析总结并安按照规定时间和要求报上一级调度室。有关调度统计报表、领导值班带班表等可与月度汇报材料同时上报</td>
										<td>任查3个月文档记录，有一次未上报扣1分、迟报一次扣0.5分；内容不全面每缺一项扣0.3分；发现一处错误扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>专题汇报</td>
										<td>5.专题汇报:按上级要求及时汇报，形成书面材料并按时间顺序存档。 以下事项应在事前汇报上一级调度室：
											①节假日停产放假、检修安排，领导值班表；停、复产安全技术措施；②矿井停产（停电）大修安排及安全措施；③主要大型设备检修（更换）安排及安全措施；④拆启密闭排放瓦斯，重大排（探）放水安排及安全技术措施；⑤初（末）次放顶、巷道贯通安全技术措施等。 其它专题汇报按上级要求完成</td>
										<td>查相关资料，少一次汇报扣0.5分；迟报一次扣0.3分；汇报材料内容不全、缺安全技术措施的，有一项各扣0.3分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>季节性汇报</td>
										<td>6.季节性汇报 对雨季防汛、防雷电、冬季防寒、防冻，冬、春季防火等季节性工作安排要按规定要求报上一级调度室，发生紧急情况要立即报告值班领导、本单位负责人和上一级调度室</td>
										<td>查相关资料，无季节性工作安排，本项不得分；缺一项扣0.5分；汇报材料不完整、存档材料不完整、存档资料不全有一项各扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="2">事故汇报</td>
										<td>7.人身伤亡事故汇报 ①发生重伤及3人以下（不含3人）伤亡事故，调度室应立即报告值班领导、煤矿负责人，组织抢救救援，并在30分钟内向上一级调度室汇报，在20小时内将伤亡事故汇报卡报上一级调度室。 ②发生3人及以上伤亡或被困事故，要立即启动事故应急预案，在接到报告立即报告上一级调度室</td>
										<td rowspan="3">查矿相关事故原始记录、录音机资料</td>
										<td>重伤、一般事故迟报一次扣0.5分；迟报超过2小时，一次扣1分；3人及以上伤亡或被困事故迟报一次扣2分，迟报超过2小时本项不得分</td>
										<td align="center">4</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>8.非伤亡事故汇报 ①煤矿发生影响盘区（采区）生产超过1小时非人身伤亡生产事故，矿调度室须在4小时内向上一级调度室报告。 ②煤矿发生影响全矿井生产超过1小时非人身伤亡事故，矿调度室要在30分钟内向上一级调度室报告</td>
										<td>发生影响盘区（采区）生产超过4小时事故迟报一次扣0.5分；发生影响全矿井生产超过1小时的事故迟报一次扣1分；有一次没有报告的本项不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>突发事件汇报</td>
										<td>9.发生突发事件，调度室要在事件发生30分钟内向值班领导、矿负责人和上一级调度室报告。汇报内容包括时间、地点、事件类型、影响程度等，并根据事态发展随时汇报</td>
										<td>突发性时间没有向上一级调度室报告的一次扣1分；迟报一次扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">25</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="14">五、调度信息化管理(25)</td>
										<td rowspan="3">通信联络系统</td>
										<td>1.有线通信 矿井必须装备调度交换机，调度交换机应支持通过数字中继或通过IP语音网关实现联网功能，并应与上一级调度总机、矿区行政电话、井下移动通信等系统互联互通。 下井电话必须经过安全耦合器，电话机应是本质安全型。
											井下采掘工作面、变电所、上下山绞车房、主要水泵房、井底车场、运输调度站、带式输送机及中国控制硐室、永久避难硐室、井下水平和采取最高点、主要硐室等重要工作地点应设置调度电话。
											地面矿领导值班室、科队值班室、地面变电站（所）、通风机房、瓦斯集中抽放站、主副井绞车房、水泵房、、井口急救站、小车队应设置调度电话</td>
										<td rowspan="14">查阅资料 现场检查</td>
										<td>调度交换机未于上一级调度部门总机联网扣0.5分；未与矿区行政电话联网扣0.5分； 井下电话未经过安全耦合器，电话机不是本质安全型，本项不得分。每少一部要求的调度电话（电话不通视同未安装）扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>2.井下应急广播系统
											矿井必须装备井下应急广播通信系统并确保正常运行：①主机与广播端应设置在调度台；②在井下人员相对集中位置如采掘工作面、井底车场、井底换装站、主要候车点、变电所、永久避难硐室等地点应设置有广播终端；③系统及广播终端应为本质安全型并具有MA标志证书；④语音应清晰，最大音量应不小于90分贝；⑤支持在断电情况下连续播放不小于2小时；⑥应实现于调度双向对讲、区域对讲、监听等功能；⑦与矿井调度电话系统互联互通
										</td>
										<td>无井下广播通信系统不得分；主机及广播端为设置在调度台扣0.5分；井下一处不能正常运行扣0.3分；一处人员集中位置未设置广播终端扣0.5分；音量不达标扣0.3分；在断电情况下连续播放不足2小时扣0.5分；其它功能缺失一项扣0.5分。
											系统及广播终端不是本质安全型或不具有MA标志证书，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>3.井下移动通信系统
											矿井必须装备井下移动通信系统，并与矿井调度、行政电话、井下广播通信系统互联互通；井上下主要安全生产工作人员应配备移动通信电话；系统支持在断电情况下连续工作不小于2小时；井下移动通信系统应具备有录音、放音、扩音、群呼、组呼、短信等功能；系统级移动通信终端应为本质安全型，并具有MA标志证书</td>
										<td>无井下移动通信系统不得分；功能缺失一项扣0.2分。系统及移动通信电话不是本质安全型或不具有MA标志证书，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="2">视频系统</td>
										<td>4.视频会议系统 矿井应装备视频会议系统，系统应与上级管理部门（集团公司、子分公司、主题企业）之间联网；保证系统24小时随时使用；确保系统画面、语音清晰连贯、能正常使用</td>
										<td>无视频会议系统或系统为联网，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>5.工业视频系统
											煤矿应装备工业视频系统，在井下主要胶带运输机头，盘区皮带转载点，井底车场、井下变电所、主副井井口、主通风机房、永久避难硐室等和井上主要工作场所如洗煤场、外运装车点、工业广场、办公楼等地点安装工业视频摄像头，并在调度大屏幕显示；图像储存时间应不小于7天，图像信号应能上传上一级调度室</td>
										<td>无工业电视系统不得分；工业电视摄像头不按要求安装或不按规定提供上传信号扣0.5分；图像储存时间达不到要求的扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="4">监测监控系统</td>
										<td>6.安全监控系统 矿井必须安装安全监控系统，调度室应有安全监控系统专用显示终端；设备的安装使用管理应符合国家标准要求</td>
										<td>调度室未设置系统专用显示终端，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>7.井下作业人员管理系统 矿井必须安装井下作业人员管理系统，调度室应有系统显示终端，准确显示井下总人数及重点区域人员分布情况，设备的安装使用管理应符合国家标准要求</td>
										<td>调度室未设置系统专用显示终端，不得分；不能准确显示井下总人数及重点区域人员分布情况，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>8.煤炭产量监控系统 矿井必须安装煤炭产量监控系统及配套视频装置，调度室应有系统显示终端，设备的安装使用管理应符合国家标准要求</td>
										<td>调度室未设置系统专用显示终端，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>9.安全监管执法与决策支持系统 矿井必须安装安全监管执法与决策支持系统，调度室应配备系统显示终端，数据采集应符合山西省煤炭工业厅有关要求</td>
										<td>调度室未设置系统专用显示终端，不得分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>井下工业环网</td>
										<td>10.为保证井下各系统传输数率和传输的稳定性，矿井应安装井下工业环网</td>
										<td>核定生产能力在90万吨/年及以上的矿未安装，不得分；发现中断6小时以下一次扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td rowspan="2">安全生产调度管理系统</td>
										<td>11.应用计算机处理日常报表 矿井要能够使用计算机处理日常报表：①生产日报；②旬、月汇报；③外运日报；④矿领导逐月带班下井统计表；⑤生产事故统计表；⑥采煤队工作面衔接情况表；⑦开掘队工作面衔接情况表；⑧初（末）次放顶及巷道贯通预报表；⑨达标规划、培训计划；⑩工作总结及专题汇报材料
										</td>
										<td>一项未使用计算机处理扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>12.调度管理信息系统 矿井应编制能适应调度管理工作需要的调度管理信息系统，并应能与上一级调度部门联网，实现生产、安全、营运、外运等日常报表信息的计算机处理并实现实时传输，满足调度业务管理和使用要求</td>
										<td>无调度信息管理系统，不得分。未与上一级调度部门联网减1分；系统功能不全或功能不正常，每有一项扣0.5分；汇报不及时，上报不准确，一次扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>生产过程动态监测</td>
										<td>13.应将矿井提升、运输、供电、通风、主排水、抽放、压风、采掘工作面设备运转 等系统运行情况在计算机或大屏幕上动态模拟显示</td>
										<td>矿井提升、运输、供电、通风、主排水、抽放、压风、采掘工作面设备运转等系统运行情况动态模拟显示，每缺一处扣0.2分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>综合信息管理平台</td>
										<td>14.矿井应建立综合信息管理平台。平台内容包括:①日常工作所使用的各种台账、报表；②集成的监测监控子系统；③集成的生产过程动态监控子系统等</td>
										<td>核定生产能力在300万吨/年及以上矿井无综合信息 管理平台，不得分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">25</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td rowspan="3">六、工作场所及环境文明(4)</td>
										<td>调度室、视频会议室</td>
										<td>1.调度室面积不小于60平方米，有满足中层及以上领导参加的视频会议室，调度室、视频会议室设备、设施的安装符合有关规程要求并保持整洁</td>
										<td rowspan="3">现场检查</td>
										<td>调度室面积小于60平方米的，扣1分；其它每项不合格的各扣0.5分</td>
										<td align="center">2</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>工作环境</td>
										<td>2.调度室、视频会议室、办公室应清洁整齐，物放有序，图纸、资料、文件摆放整齐，图表规范统一</td>
										<td>图表、资料等杂乱无章、不规范统一，一项扣0.3分；环境卫生差扣0.2分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td>文明办公</td>
										<td>3.调度人员统一着装并佩戴胸牌，恪尽职守，工作认真负责，做到文明用语、不卑不亢，礼貌待人</td>
										<td>根据现场情况掌握，用语不文明、工作不认真等，有一项次扣0.3分</td>
										<td align="center">1</td>
										<td></td>
										<td align="center"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">小计</td>
										<td align="center">4</td>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td colspan="5" align="center">合计</td>
										<td align="center">100</td>
										<td colspan="2"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/quality/dispatch/index');
	</script>
</body>
</html>
