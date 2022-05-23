<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 이 taglib은 JSTL 구문 c:forEach 쓰려고 import함.
'글 목록'은 글 하나하나 당 for문 돌려서 가져와야 하니까.
계속 JSP 스크립틀릿으로 열고 닫고 하면서 for문 쓰려면 킹받잖아!  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>다른 사람들이 모두 에러 날 때, 나만 안 나면 우월감을 얻을 수 있다</title>
</head>
<body>
	<h2>자유 게시판 글 목록</h2>
	<hr>
	<table width = "1000" cellpadding = "0" cellspacing = "0" border = "1">
		<tr bgcolor = "lightBlue">
			<td align = "center">일련번호</td>
			<td align = "center">글쓴이</td>
			<td width = "500" align = "center">제목</td>
			<td align = "center">게시일</td>
			<td align = "center">조회수</td>
		</tr>
		
		<!-- 얘는 ModelAndView라서, model을 자동으로 받았다.
		그러니 model.list 이렇게 굳이 명시할 필요 없고 그냥 이름만 안 틀리게 EL 표기법으로 잘 쓰면 된다. 근데 var 이름은 또 계속 써야 하네..-->
		<c:forEach items = "${list}" var = "dto"> <!-- var 이름은 우리가 마음대로 정했다. 교) dto들이 들어 있으니까 이름을 dto라고 지을개여, ㅋ -->
			<tr>
				<td>${dto.bid}</td>
				<td>${dto.bname}</td>
				<td>${dto.btitle}</td>
				<td>${dto.bdate}</td>
				<td>${dto.bhit}</td>
			</tr>
		</c:forEach>
		
		<tr>
			<!-- a href로 하니까 못생겼다, 버튼으로 만들고 싶다. (그런데 그러면 colspan은 어떻게 해야...) -->
			<td colspan = "5" align = "right"><a href = "write_form">글 쓰기? 나는 글이 달기만 하다</a></td>
		</tr>
		
	
	</table>


</body>
</html>


























