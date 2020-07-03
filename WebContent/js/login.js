function subRegister(){
	
	var username = $("#username").val();
	var password = $("#password").val();
	var repassword = $("#repassword").val();
	var sid = $("#sid").val();
	var classname = $("#classname").val();
	//var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if (username==""||username.length<2){
		alert("用户名不得为空且不能少于两位中英文加数字");
		return;
	}else if (password==null||password.length<5){
		alert("密码不得为空且不能少于五位英文加数字");
		return;
	}else if (password != repassword){
		alert("两次密码不一致");
		return;
	}else if (sid==""||sid.length>20){
		alert("学号不得大于20位");
		return;
	}else{
		$("#registerform").submit();
	}
}