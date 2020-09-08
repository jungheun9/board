package comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comment.model.Comment;
import comment.model.Writer;
import jdbc.JdbcUtil;

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
	
	public List<Comment> select(Connection conn, int article_no, int startRow, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from comment where article_no = ? "
					+ "order by comment_no desc limit ?,?");
			pstmt.setInt(1, article_no);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();
			List<Comment> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertComment(rs));
			}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int selectCount(Connection conn, Integer article_no) throws SQLException {
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select count(*) from comment where article_no = ?");
			pstmt.setInt(1, article_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private Comment convertComment(ResultSet rs) throws SQLException {
		return new Comment(
				rs.getInt("comment_no"),
				new Writer(rs.getString("writer_id"), rs.getString("writer_name")),
				rs.getString("content"),
				toDate(rs.getTimestamp("regdate")),
				toDate(rs.getTimestamp("moddate")));
	}
	
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
}
