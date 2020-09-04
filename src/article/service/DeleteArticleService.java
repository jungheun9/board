package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class DeleteArticleService {

	ArticleDao articleDao = new ArticleDao();
	ArticleContentDao contentDao = new ArticleContentDao();
	
	public void delete(DeleteRequest req) {
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Article article = articleDao.selectById(conn, req.getArticleNumber());
			if (article == null) {
				throw new ArticleNotFoundException();
			}
			if (!PermissionChecker.canAccess(req.getUserId(), article)) {
				throw new PermissionDeniedException();
			}
			contentDao.delete(conn, req.getArticleNumber());
			articleDao.delete(conn, req.getArticleNumber());
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
