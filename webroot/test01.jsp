<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
</head>


<script type="text/javascript">
	/*  $(function(){
		$.ajax({
   			url:"fenlei.do",
			type:"POST",
			dataType:"json",
			async: false,
			data:{
				
				'level':"1"
			},
			success:function(data){
				
			}
			
		});
	});  */
	 function test(){
			$.ajax({
	   			url:"fenlei.do",
				type:"POST",
				dataType:"json",
				contentType: "application/json; charset=utf-8",
				async: false,
				data:{
					
					'level':"1"
				},
				success:function(data){
					
				}
				
			});
	 }
</script>
<body>
	<form action="fenlei.do" method="POST">  
     	<table align="center" cellpadding="0" cellspacing="0" border="1" width="250">
     		<caption><h1>产品</h1></caption>
     		<!-- <tr>
     			<td>id:<input type="text" name="cid" id="cid"></td>
     		</tr> -->
     		<tr>
     			<td>类型:
     				<input type="text" name="level" id="level">
     				<input type="submit" value="提交"/>
     			</td>
     		</tr>
     		
     	</table>    
     </form> 
     
    <%--  <table width="400" border="1" cellpadding="0" cellspacing="0" align="center">  
      		<caption><h1>&nbsp;信息表</h1></caption>
            <thead>  
            	
                <tr>  
                    <th>id</th>  
                    <th>名字 </th>
                   
                </tr>  
            </thead>  
            <tbody id="goaler">             
                <c:forEach items="${listCategory}" var="li">
                    <tr align="center">  
                        <td>  
                            ${li.cid}
                        </td>  
                         <td>  
                            ${li.cname}  
                        </td>
                       
                    </tr>
                </c:forEach> 
               
            </tbody>  
        </table>   --%>
</body>
</html>