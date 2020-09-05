package comment.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import comment.dao.CommentDao;
import comment.model.Comment;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class WriteCommentService {

	private CommentDao commentDao = new CommentDao();
	
	public void insert(WriteRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			
			Comment comment = toComment(req);
			
			int saveCommentCnt = commentDao.insert(conn, comment, req.getArticleNumber());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private Comment toComment(WriteRequest req) {
		return new Comment(null, req.getWriter(), req.getContent(), new Date(), new Date());
	}
}
