package comment.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.command.ReadArticleHandler;
import auth.service.User;
import comment.model.Writer;
import comment.service.WriteCommentService;
import comment.service.WriteRequest;
import mvc.command.CommandHandler;

public class WriteCommentHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/readArticle.jsp";
	WriteCommentService writeService = new WriteCommentService();
	ReadArticleHandler readArticleHandler = new ReadArticleHandler();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if(req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return null;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		User authUser = (User)req.getSession(false).getAttribute("authUser");
		WriteRequest writeRequest= createWriteRequest(authUser, req);
		writeRequest.validate(errors);
		
		if (!errors.isEmpty()) {
			return readArticleHandler.process(req, res);
		}
		
		writeService.insert(writeRequest);
		
		return readArticleHandler.process(req, res);
	}
	
	private WriteRequest createWriteRequest(User user, HttpServletRequest req) {
		String noVal = req.getParameter("articleNo");
		int no = Integer.parseInt(noVal);
		return new WriteRequest(
				new Writer(user.getId(), user.getName()),
				req.getParameter("content"),
				no);
	}
}
