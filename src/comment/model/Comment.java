package comment.model;

import java.util.Date;

public class Comment {
	
	private Integer number;
	private Writer writer;
	private String content;
	private Date regDate;
	private Date modDate;
	
	public Comment(Integer number, Writer writer, String content, Date regDate, Date modDate) {
		this.number = number;
		this.writer = writer;
		this.content = content;
		this.regDate = regDate;
		this.modDate = modDate;
	}
	
	public Integer getNumber() {
		return number;
	}
	public Writer getWriter() {
		return writer;
	}
	public String getContent() {
		return content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public Date getModDate() {
		return modDate;
	}
}
