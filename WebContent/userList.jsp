<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/jspHead.jsp" %>
<!-- 分页样式 -->
	<style type="text/css">
	.page{
		display:inline-block; /*内联对象*/
		border: 1px solid; /*1边框像素*/
		font-size: 20px;
		width: 30px;
		height: 30px;
		background-color: #1faeff;
		text-align: center; /*居中对齐*/
	}
	a,a:hover{ text-decoration:none; color:#333}
</style>
</head>
<body>
		<!-- 导航路径 -->
<div class="container-fluid" >
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="#">再一个令人头秃测试页面</a>
	    </div>
	
	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	        <li><a href="<%=basePath%>Contentseverlet">首页 </a></li>
	      </ul>
	      <ul class="nav navbar-nav navbar-right">
	      	<c:choose>
	      		<c:when test="${not empty id}">
	      			<li><a href="javascript:void(0)" onclick="alert('会员')">会员:${name}</a></li>
	      			<c:if test="${not empty flag }">
	      			<li><a href="<%=basePath%>Userseverlet?pid=mgt">管理</a></li>
	      			</c:if> 
	      			<li><a href="<%=basePath%>exit.jsp">退出</a></li>
	      		</c:when>
	      		<c:otherwise>
	      			<li><a href="<%=basePath %>index.jsp">登录</a></li>
	      		</c:otherwise>
	      	</c:choose>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>
	<ol class="breadcrumb">
		  <li><a href="<%=basePath %>Contentseverlet">用户管理</a></li> 
	</ol>
	    <table class="table table-striped">
    	<!-- tr创建一行 -->
    	<tr>
    		<!-- th用于创建表头 -->
    		<th width="20%"> <strong> 用户: </strong> </th>
    		<th width="20%"> <strong> 状态:(1表示没有被禁言) </strong> </th>
    		<th width="20%"> <strong> 创建时间:</strong> </th>
    		<th width="10%"> <strong> 班级:</strong>
    		<th width="30%"> <strong> 操作:</strong> </th>
    	</tr>
    	<!-- choose标签相当于Java里的switch case -->
    	<c:choose>
    	<%--when标签相当于swutch 中的case --%>
    	<c:when test="${not empty userlist }">
    		<!-- forEach相当于Java代码里的循环 -->
    		<!-- 属性items为要迭代的元素 -->
    		<!-- 属性varStatus为迭代状态 -->
    		<c:forEach items="${userlist }" var="item" varStatus="vs">
    			<tr>
    				<td>
    				<!-- 该a标签指向具体帖子链接，点击打开 -->
    					<img src="<%=basePath %>image/pin_1.gif" id="${item.id}" />
    					&nbsp;&nbsp;
    					<!-- 获取标题 -->
    					${item.username}
    				</td>
    				<td>
    				${item.status}
    				</td>
    				<!-- 获取创建时间 -->
    				<td>
    				${item.createtime}
    				</td>
    				<td>
    				${item.classname}
    				</td>
    				<td>
    				<c:if test="${item.status == 1 }">
    				<a href = "?pid=jin&uid=${item.id}">禁言</a>
    				</c:if>
    				<c:if test="${item.status == 0 }">
    				<a href = "?pid=jjin&uid=${item.id}">解除禁言</a>
    				</c:if>
    				<a href = "?pid=shan&uid=${item.id}">删除</a>
    				</td>
    				
    			</tr>
    		</c:forEach>
    	</c:when>
    	</c:choose>
    </table>
</body>
</html>