package comment.service;

import java.util.Map;

import comment.model.Writer;

public class WriteRequest {

	private Writer writer;
	private String content;
	private Integer articleNumber;
	
	public WriteRequest(Writer writer, String content, Integer articleNumber) {
		this.writer = writer;
		this.content = content;
		this.articleNumber = articleNumber;
	}

	public Writer getWriter() {
		return writer;
	}

	public String getContent() {
		return content;
	}

	public Integer getArticleNumber() {
		return articleNumber;
	}
	
	public void validate(Map<String, Boolean> errors) {
		if (content == null || content.trim().isEmpty()) {
			errors.put("content", Boolean.TRUE);
		}
	}
}
