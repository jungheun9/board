<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

댓글
<form action="write.do" method="post">
<input type="hidden" name="article_no" value="${article_no }">
<table>
	<tr>
		<td>댓글 내용</td>
	</tr>
	<tr>
		<td><textarea name="content" cols="30" rows="10"></textarea></td>
	</tr>
	<tr>
		<td><input type="submit" value="댓글 쓰기"></td>
	</tr>
</table>
</form>
<table>
	<tr>
		<td>작성자</td>
		<td>작성일자</td>
		<td>내용</td>
	</tr>
	<c:if test="${commentPage.hasNoComments() }">
	<tr>
		<td colspan="3">댓글이 없습니다.</td>
	</tr>
	</c:if>
	<c:forEach var="comment" items="${commentPage }">
	<tr>
		<td>${comment.writerName }</td>
		<td>${comment.regDate }</td>
		<td>${comment.content }</td>
	</tr>
	</c:forEach>
</table>

</body>
</html>