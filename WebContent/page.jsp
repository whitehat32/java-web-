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
<c:if test = "${msg != null }">
<script type="text/javascript">
alert("${msg}");
<% request.removeAttribute("msg"); %>
</script>
</c:if>
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
	      <a class="navbar-brand" href="#">又一个令人头秃测试页面</a>
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
		  <li><a href="<%=basePath %>Contentseverlet">首页</a></li> 
		  
	</ol>
</div>
    <!-- 使用Bootstrap table样式 -->
    <table class="table table-striped">
    	<!-- tr创建一行 -->
    	<tr>
    		<!-- th用于创建表头 -->
    		<th width="50%"> <strong> 标题: </strong> </th>
    		<th width="10%"> <strong> 作者: </strong> </th>
    		<th width="20%"> <strong> 发表时间: </strong> </th>
    		<th width="20%"> <strong> 操作: </strong> </th>
    	</tr>
    	<!-- choose标签相当于Java里的switch case -->
    	<c:choose>
    	<%--when标签相当于swutch 中的case --%>
    	<c:when test="${not empty main }">
    		<!-- forEach相当于Java代码里的循环 -->
    		<!-- 属性items为要迭代的元素 -->
    		<!-- 属性varStatus为迭代状态 -->
    		<c:forEach items="${main }" var="item" varStatus="vs">
    			<tr>
    				<td>
    				<!-- 该a标签指向具体帖子链接，点击打开 -->
    				<a href="<%=basePath%>Userseverlet?pid=${item.id}">
    					<img src="<%=basePath %>image/pin_1.gif" id="${item.id}" />
    					[精华帖]&nbsp;&nbsp;
    					<!-- 获取标题 -->
    					${item.title}
    				</a>
    				</td>
    				<!-- 获取发帖人 -->
    				<td>
    				${item.username}
    				${item.userid}
    				</td>
    				<td>
    				${item.createtime}
    				</td>
    				<td>
    				<c:if test="${not empty flag || item.userid == id}">
    				<a href="<%=basePath%>Userseverlet?pid=shantie&uid=${item.id}">
    				删除
    				
    				</a>
    				</c:if>
    				</td>
    			</tr>
    		</c:forEach>
    	</c:when>
    	</c:choose>
    </table>
    <div class="row">
    	<div class="col-xs-7">
    	
    	</div>
    	<div class="col-xs-5 text-nowrap">
    		<!-- 获取分页 -->
    		${pageHtml}
    	</div>
    </div>
	<form action="Contentseverlet" method="post" id="test1">
		<!--  label标签为input表单定义标注  -->
	<input type="hidden" name="action" value="fatie"/>
	<label for="biaoti"> 帖子标题：</label> 
	<!--  input标签用于收集用户信息  -->
	<input type="text" name="mainTitle" placeholder="最大长度80个汉字" style="width: 360px;" > 
	<!--  button标签放置一个按钮，type属性设置为submit用于提交表单  -->
     <button type="submit" class="btn btn-primary btn-xs text-right"> 
        发表帖子
     </button> 
        <!-- 加载编辑器的容器 -->
        <div style="padding: 0px;margin: 0px;width: 100%;height: 100%;" >
	        <script id="container" name="content" type="text/plain">
            
        	</script>
        </div>
    </form>
    
    <script type="text/javascript">
    	var vis = '<%=session.getAttribute("id")%>';
    	if (vis == "null" || vis == "") {
    		document.getElementById("test1").style.display="none";
    	}
    </script>
        <!-- 配置文件 -->
    <script type="text/javascript" src="<%=basePath %>uedit/js/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="<%=basePath %>uedit/js/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
   		 var editor = UE.getEditor('container');
    </script>
    <!-- end富文本 -->
</body>
</html>