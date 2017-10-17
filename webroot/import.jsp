<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test</title>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#submit").click(function() { 
            var formData = new FormData();
            formData.append("files",$("#files")[0].files[0]);
            $.ajax({ 
	            url : "http://localhost:8080/ROOT/import", 
	            type : 'POST', 
	            data : formData, 
	            // 告诉jQuery不要去处理发送的数据
	            processData : false, 
	            // 告诉jQuery不要去设置Content-Type请求头
	            contentType : false,
	            beforeSend:function(){
	            	console.log("正在进行，请稍候");
	            },
	            success : function(responseStr) { 
		            console.log("成功"+JSON.stringify(responseStr));
		            $("#font").text(JSON.stringify(responseStr));
	            }, 
		            error : function(responseStr) { 
		            console.log("error");
	            } 
            });
            
		});
	})
</script>
</head>
<body>
	<form id="form" name="uploadForm" method="POST" enctype="multipart/form-data" action="http://localhost:8080/ROOT/import"> 
         	上传文件:<input type="file" id="files" /> 
        <input type="button" name="submit" id="submit" value="提交"> 
        <input type="reset" name="reset" value="重置">
        <br>
        <font id="font"></font>
    </form>

</body>
</html>
