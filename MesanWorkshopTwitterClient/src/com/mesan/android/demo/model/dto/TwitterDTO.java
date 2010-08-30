package com.mesan.android.demo.model.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class TwitterDTO implements Comparable<TwitterDTO>, Serializable {
	private static final long serialVersionUID = 5723006892309338592L;
	private String keyword;
	private ArrayList<TweetDTO> tweets;
	private ArrayList<String> flickrImages;
	private Long timeInMillis;
	
	public TwitterDTO() {
		setTimeInMillis();
	}
	
	public TwitterDTO(String keyword) {
		setKeyword(keyword);
		setTimeInMillis();
	}
	
	public TwitterDTO(String keyword, ArrayList<TweetDTO> tweets, ArrayList<String> flickrImages) {
		setKeyword(keyword);
		setTweets(tweets);
		setFlickrImages(flickrImages);
		setTimeInMillis();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ArrayList<TweetDTO> getTweets() {
		return tweets;
	}

	public void setTweets(ArrayList<TweetDTO> tweets) {
		this.tweets = tweets;
	}

	public ArrayList<String> getFlickrImages() {
		return flickrImages;
	}

	public void setFlickrImages(ArrayList<String> flickrImages) {
		this.flickrImages = flickrImages;
	}

	public Long getTimeInMillis() {
		return timeInMillis;
	}

	public void setTimeInMillis() {
		timeInMillis = System.currentTimeMillis();
	}

	public int compareTo(TwitterDTO another) {
		return (getTimeInMillis() < another.getTimeInMillis()) ? 1 : -1;
	}
	
	

}
