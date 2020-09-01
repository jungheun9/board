<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jdbc.connection.ConnectionProvider" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>연결 테스트</title>
</head>
<body>
<%
	// 자바7부터 사용가능한 try-with-resource 기능을 사용함
	try (Connection conn = ConnectionProvider.getConnection()) {
		out.println("커넥션 연결 성공함");
	} catch(SQLException ex) {
		out.println("커넥션 연결 실패함 : " + ex.getMessage());
		application.log("커넥션 연결 실패", ex);
	}
%>
</body>
</html>