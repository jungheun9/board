package article.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleNotFoundException;
import article.service.DeleteArticleService;
import article.service.DeleteRequest;
import article.service.PermissionDeniedException;
import auth.service.User;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {

	DeleteArticleService deleteService = new DeleteArticleService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			return null;			
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
		
			User authUser = (User)req.getSession().getAttribute("authUser");
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);
			
			DeleteRequest deleteReq = new DeleteRequest(authUser.getId(), no);
			
			deleteService.delete(deleteReq);
			
			return "/WEB-INF/view/deleteSuccess.jsp";
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		
		return null;
	}
}
