package com.footballwest.football;

public class NewsModel
{

	private String title;
	private String date;
	private String content;
	private String url;
	
	public NewsModel(String title, String date, String content, String url)
	{
		this.title = title;
		this.date = date;
		this.content = content;
		this.url = url;
		
	}
	
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	
}