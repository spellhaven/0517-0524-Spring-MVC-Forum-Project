<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 글보기</title>
</head>
<body>
	<h2>오천 원만 (안) 내면 글 내용 보여 주는 놈</h2>
	<hr>
	<table width = "600" cellpadding = "0" cellspacing = "0" border = "1">
		<tr>
			<td>글번호</td>
			<td>${content.bid}</td>
		</tr>
		<tr>
			<td>조회수</td>
			<td>${content.bhit}</td>
		</tr>
		<tr>
			<td>글 쓴 친구</td>
			<td>${content.bname}</td>
		</tr>
		<tr>
			<td>글 제 목</td>
			<td>${content.btitle}</td>
		</tr>
		<tr>
			<td>내 용</td>
			<td>${content.bcontent}</td>
		</tr>
		<tr>
			<td colspan = "2">
				<a href = "reply_view?bid=${content.bid}">답변</a> &nbsp; &nbsp;
				<a href = "modify_view?bid=${content.bid}">수정</a> &nbsp; &nbsp;
				<a href = "delete?bid=${content.bid}">삭제</a> &nbsp; &nbsp;
				<a href = "list">글 목록 보기</a>
			</td>
		</tr>
</body>
</html>