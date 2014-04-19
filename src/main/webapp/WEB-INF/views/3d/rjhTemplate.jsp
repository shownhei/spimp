<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="rjhCommand-template" class="accordion-style1">
   <div class="panel panel-default ">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse_r">人员情况</a>
            </h4>
        </div>
        <div class="panel-collapse in collapse" id="collapse_r" style="height: auto;">
            <table class="table table-striped table-bordered table-hover" >
              <thead><tr>
              <th style="width:40px;">姓名</th>
              <th style="width:40px;">部门</th>
              <th style="width:40px;">岗位</th>
              <th class="hidden-480">工种</th>
              <th class="hidden-480">班组</th>
              </tr></thead>
              <tbody>
            <c:forEach items="${PERSON}" var="data" varStatus="status">
              <tr>
              <td>${ data.name}</td>
              <td>${ data.department}</td>
              <td>${ data.jobName}</td>
              <td>${ data.jobType}</td>
              <td>${ data.troopName}</td>
              
              </tr>
            </c:forEach>
              </tbody></table>
        </div>
    </div>

    <div class="panel panel-default ">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse_j">设备情况</a>
            </h4>
        </div>
        <div class="panel-collapse in collapse" id="collapse_j" style="height: auto;">
            <table class="table table-striped table-bordered table-hover">
              <thead><tr>
              <th style="width:40px;">名称</th>
              <th class="hidden-480">设备编号</th>
              <th class="hidden-480">设备类型</th>
              <th class="hidden-480">安装位置</th>
              </tr></thead>
              <tbody>
            <c:forEach items="${EQIPMENT.electr_equipments}" var="data" varStatus="status">
              <tr>
              <td>${ data.deviceName}</td>
              <td>${ data.deviceNumber}</td>
              <td>${ data.deviceType}</td>
              <td>${ data.deviceArea}</td>
              </tr>
            </c:forEach>
            
            <c:forEach items="${EQIPMENT.electr_transform_equipments}" var="data" varStatus="status">
              <tr>
              <td>${ data.deviceName}</td>
              <td>${ data.equipmentNumber}</td>
              <td>${ data.deviceModel}</td>
              <td>${ data.location}</td>
              </tr>
            </c:forEach>
             <c:forEach items="${EQIPMENT.electr_wind_water_equipments}" var="data" varStatus="status">
              <tr>
              <td>${ data.windAmount}</td>
              <td>${ data.equipmentCode}</td>
              <td>${ data.windCycle}</td>
              <td>${ data.location}</td>
              </tr>
            </c:forEach>
             <c:forEach items="${EQIPMENT.electr_fire_fighting_equipments}" var="data" varStatus="status">
              <tr>
              <td>${ data.sandBoxCapacity}</td>
              <td>${ data.equipmentCode}</td>
              <td>消防斧${ data.fireAxe}/消防钩${data.fireHook}</td>
              <td>${ data.location}</td>
              </tr>
            </c:forEach>
             </tbody></table>
        </div>
    </div>

    <div class="panel panel-default ">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse_h">环境信息</a>
            </h4>
        </div>
        <div class="panel-collapse in collapse" id="collapse_h" style="height: auto;">
            <table class="table table-striped table-bordered table-hover">
              <thead><tr>
              <th style="width:40px;">设备编号</th>
              <th class="hidden-480">设备类型</th>
              <th class="hidden-480">检测值</th>
              <th class="hidden-480">是否超限</th>
              <th class="hidden-480">安放地点</th>
              </tr></thead>
              <tbody>
            <c:forEach items="${ENRIROMENT}" var="data" varStatus="status">
              <tr>
              <td>${ data[0]}</td>
              <td>${ data[1]}</td>
              <td>${ data[2]}</td>
              <td>${ data[3]}</td>
              <td>${ data[4]}</td>
              </tr>
            </c:forEach>
             </tbody></table>
        </div>
    </div>
</div>