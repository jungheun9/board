package comment.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import article.dao.ArticleDao;
import article.model.Article;
import article.service.ArticleNotFoundException;
import comment.dao.CommentDao;
import comment.model.Comment;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class WriteCommentService {

	private CommentDao commentDao = new CommentDao();
	private ArticleDao articleDao = new ArticleDao();
	
	public void insert(WriteRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Article article = articleDao.selectById(conn, req.getArticleNumber());
			if (article == null) {
				throw new ArticleNotFoundException();
			}
			
			Comment comment = toComment(req);
			
			int saveCommentCnt = commentDao.insert(conn, comment, req.getArticleNumber());
			
			if (saveCommentCnt < 1) { 
				throw new RuntimeException("fail to insert comment");
			}
			
			articleDao.increaseCommentCount(conn, req.getArticleNumber());
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private Comment toComment(WriteRequest req) {
		return new Comment(null, req.getWriter(), req.getContent(), new Date(), new Date());
	}
}
