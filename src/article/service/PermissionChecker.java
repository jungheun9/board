package article.service;

import article.model.Article;

public class PermissionChecker {
	public static boolean canAccess(String userId, Article article) {
		return article.getWriter().getId().equals(userId);
	}
}
