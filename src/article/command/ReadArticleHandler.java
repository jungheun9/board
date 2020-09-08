package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleContentNotFoundException;
import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ReadArticleService;
import comment.service.CommentPage;
import comment.service.ListCommentService;
import mvc.command.CommandHandler;

public class ReadArticleHandler implements CommandHandler {

	ReadArticleService readService = new ReadArticleService();
	ListCommentService listCommentService = new ListCommentService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String noVal = req.getParameter("articleNo");
		int articleNum = Integer.parseInt(noVal);
		String commentPageVal = req.getParameter("commentPage");
		int commentPageNum = 
				commentPageVal == null ? 1 : Integer.parseInt(commentPageVal);
		try {
			ArticleData articleData = readService.getArticle(articleNum, true);
			req.setAttribute("articleData", articleData);
			CommentPage commentPage = listCommentService.getCommentPage(articleNum, commentPageNum);
			req.setAttribute("commentPage", commentPage);
			return "/WEB-INF/view/readArticle.jsp";
		} catch (ArticleNotFoundException | ArticleContentNotFoundException e) {
			req.getServletContext().log("no article", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}
