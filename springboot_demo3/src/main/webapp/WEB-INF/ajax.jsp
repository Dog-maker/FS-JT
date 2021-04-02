<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>您好Springboot</title>

	<script src="../js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript">
		$(function (){
			$.get("Ajax", {id:1},function (data){


				//for循环：案例1
				// for (let i= 0;i < data.length;i++){
				// 	let user = data[i];
				// 	console.log(user)
				// }

				//案例2:
				// for(let index in data){
				// 	let user = data[index];
				// 	console.log(user)
				// }

				//案例3:
				for (var user of data){
					var tr = "<tr><td>"+user.id+"</td><td>"+user.name+"</td><td>"+user.age+"</td><td>"+user.sex+"</td></tr>"
					$("#massage").append(tr)
				}
			})
		})
	</script>
</head>
<body>
	<table id = "massage" border="1px" width="65%" align="center">
		<tr>
			<td  colspan="6" align="center"><h3>学生信息</h3></td>
		</tr>
		<tr>
			<th>编号</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>性别</th>
		</tr>

	</table>
</body>
</html>