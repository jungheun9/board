package comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import comment.model.Comment;

public class CommentDao {

	public int insert(Connection conn, Comment comment, Integer article_no) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"insert into comment"
				+ "(writer_id, writer_name, content, regdate, moddate, article_no) "
				+ "values(?,?,?,?,?,?)")){
			pstmt.setString(1, comment.getWriter().getId());
			pstmt.setString(2, comment.getWriter().getName());
			pstmt.setString(3, comment.getContent());
			pstmt.setTimestamp(4, toTimestamp(comment.getRegDate()));
			pstmt.setTimestamp(5, toTimestamp(comment.getModDate()));
			pstmt.setInt(6, article_no);
			return pstmt.executeUpdate();
		}
	}
	
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
}
