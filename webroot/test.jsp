<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test</title>
</head>
<body>
<button onclick="httpFun()" >测试一下 git trigger 5min</button>
</body>
<script type="text/javascript">

var httpFun = function() {
	
	var data = {'filterName':'测试filter二', 'uid':11011,'openId':'sssss', 'sourceId':'sssss', 'level':3 };
	var lucenefilterStr = JSON.stringify(data);
	var url = 'http://localhost:8080/search?_callback=JSON_CALLBACK&custFilter=' + lucenefilterStr;
	
}
//////
</script>
</html>
