<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 작성</title>
</head>
<body>
<form action="/board/comment/write.do" method="post">
<input type="hidden" name="articleNo" value="${articleData.article.number }">
<table border="1" width="100%">
	<tr>
		<td>댓글 내용</td>
	</tr>
	<tr>
		<td>
		<textarea name="content" cols="30" rows="10">
			<c:if test="${errors.content}">댓글 내용을 입력해야 합니다.</c:if>
		</textarea>
		</td>
	</tr>
	<tr>
		<td><input type="submit" value="댓글 쓰기"></td>
	</tr>
</table>
</form>
</body>
</html>