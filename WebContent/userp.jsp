<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPEhtml> 
<html> 
<head> 
<%@include file="/jspHead.jsp" %>	
	<!--  分页样式  -->
 	<style type="text/css"> 
	.page{
		display:inline-block;			/*  内联对象  */
		border: 1px solid ;			/*  1像素边框  */
		font-size: 20px;				/*  文字大小20像素  */
		width: 30px;					/*  宽度30像素  */
		height: 30px;				/*  高度30像素  */
		background-color: #1faeff;	/*  设置背景色  */
		text-align: center;			/*  居中对齐  */
	}
	a,a:hover{ text-decoration:none; color:#333}
 	</style> 
</head> 
<body> 
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
	      <a class="navbar-brand" href="#">希望是最后一个令人头秃测试页面</a>
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
	<!--  以下代码使用JSTL标签迭代出主贴与跟帖  -->
	<c:if test = "${msg != null }">
	<script type="text/javascript">
	alert("${msg}");
	<% request.removeAttribute("msg"); %>
	</script>
	</c:if>
<div class="container-fluid" > 
	
	<c:forEach items = "${subpage}" var="subpage">
	<table class="table table-bordered"> 
		<tr> 
 		<!--  td标签，该单元格定义了发帖人信息与身份  -->
		<td class="tbl"> 
		<div style="text-align: center;"> 
		<p> 头秃测试</p> 
		<a> <img alt="" src="<%=basePath %>image/avatar_002.gif" /> </a> 
		</div>
		<!--  table标签，该表格用户展示发帖人信息  -->
		<table class="table" style="background-color:#e5edf2; "> 
		
			<tr> 
		<td> 昵称:</td> 
 		<!--  使用EL表达式获取发帖人  -->
		<td> ${subpage.username }</td> 
		</tr> 
		<tr> 
		<td> 性别:</td> 
		<td> 男</td> 
		</tr> 
		
		<tr> 
		<td> 年龄:</td> 
		<td> 18</td> 
		</tr> 
		</table> 
		</td> 
 		<!--  td标签，该单元格定义了帖子详细内容  -->
		<td class="tbr"> 
		<div style="height: 65px;padding-left: 20px;padding-top: 1px;"> 
		<h3>
 		<!--  使用EL表达式获取帖子标题  -->
 		<a style="color: #ifaeff"> ${subpage.title }</a>
 		</h3> 
		</div> 
 		<!-- 下面这是画出一条横线 --> 
 		<div style="width:98%;height:1px;margin-bottom:10px;
 			padding:0px;background-color:#D5D5D5;overflow:hidden;"> 
 		</div> 
		<p class="text-right" style="padding-right: 90px;"> 
		<span style="padding-right: 30px;"> 
 		<!--  EL表达式获取发帖时间  -->
		<a style="color: #78BA00;">
 		发表于:${subpage.sec_createtime }
 		</a> 
		</span> 
		<span> </span> 
		</p> 
 		<!-- 下面这是画出一条横线 --> 
 		<div style="width:98%;height:1px;margin-bottom:10px;
                        padding:0px;background-color:#D5D5D5;overflow:hidden;"> 
 		</div> 
		<div style="padding-top: 12px;min-height: 380px;"> 
 		<!--  EL表达式获取帖子内容  -->
		${subpage.content }
		</div> 
 		<!-- 下面这是画出一条横线 --> 
 		<div style="width:98%;height:1px;margin-bottom:10px;
                       padding:0px;background-color:#D5D5D5;overflow:hidden;"> 
 			</div> 
			<!--  上下间隙90像素  --> 
			<div style="padding-right: 90px;"> 		
			</div> 
			</td> 
			</tr> 
		
		</table>
		</c:forEach>
		<c:if test = "${not empty id }">
		<form action="Contentseverlet" method="post">
			<input type="hidden" name="action" value="huitie"/>
			<label for="biaoti"> 帖子标题：</label> 
			<!--  input标签用于收集用户信息  -->
			<input type="text" name="mainTitle" placeholder="最大长度80个汉字" style="width: 360px;" > 
	 		<button type="submit" class="btn btn-primary btn-xs text-right"> 
        		回复帖子
     		</button> 
			<input type="hidden" name="hid" value="${subpage[0].id}"/> 
			<div style="padding: 0px;margin: 0px;width: 100%;height: 100%;" >
	        	<script id="container" name="content" type="text/plain">
            
        		</script>
      		</div>
		</form>
		</c:if>
		
</div> 
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
