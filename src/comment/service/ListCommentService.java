package comment.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import comment.dao.CommentDao;
import comment.model.Comment;
import jdbc.connection.ConnectionProvider;

public class ListCommentService {
	
	private CommentDao commentDao = new CommentDao();
	private int size = 10; // 한 번에 보여줄 comment 수
	
	public CommentPage getCommentPage(int article_no, int pageNum) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = commentDao.selectCount(conn, article_no);
			List<Comment> content = commentDao.select(
					conn, article_no, (pageNum - 1) * size, size);
			return new CommentPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
