package comment.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.service.CommentPage;
import comment.service.ListCommentService;
import mvc.command.CommandHandler;

public class ListCommentHandler implements CommandHandler {

	private ListCommentService listService = new ListCommentService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String articleNoVal = req.getParameter("articleNo");
		int articleNo = Integer.parseInt(articleNoVal);
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		CommentPage commentPage = listService.getCommentPage(articleNo, pageNo);
		req.setAttribute("commentPage", commentPage);
		return "/WEB-INF/view/listComment.jsp";
	}
}
