package com.mesan.android.demo.model.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mesan.android.demo.model.application.Application;

public class FlickrService {

	private static final String FLICKR_SEARCH_API_URL = "http://api.flickr.com/services/rest/?method=flickr.photos.search&nojsoncallback=1&format=json&api_key=6b3b39e81d8f4b5f527250506e146d4b&sort=interestingness-asc&extras=url_s&per_page=10&tags=";

	public ArrayList<URL> getImagesFromFlickr(String keyword){
		
		// Execute the request
		HttpResponse response = Application.sendGetRequestForUrl(FLICKR_SEARCH_API_URL + keyword);

		StatusLine status = response.getStatusLine();
		
		if (status.getStatusCode() == 200) {
			try {
				return parseJson(EntityUtils.toString(response.getEntity()), keyword);
			} catch (ParseException pex) {
				Log.e(TwitterService.class.getSimpleName(), pex.getMessage(), pex);
			} catch (IOException ioex) {
				Log.e(TwitterService.class.getSimpleName(), ioex.getMessage(), ioex);
			}
		}
		
		return null;
	}
	

	private ArrayList<URL> parseJson(String json, String keyword){
		ArrayList<URL> urlList = new ArrayList<URL>();
		
		try {
			
			JSONObject shipmentObject = new JSONObject(json);						
			JSONObject photos = shipmentObject.optJSONObject("photos");
			JSONArray photoArray = photos.optJSONArray("photo");
			
			int photoArrSize = photoArray.length();
			
			JSONObject photo = null;

			for(int i = 0; i<photoArrSize; i++){
				photo = photoArray.optJSONObject(i);
				
				try {
					urlList.add(new URL(photo.optString("url_s").replaceAll("\\\\", "")));
				} catch (MalformedURLException murlex) {
					Log.i(FlickrService.class.getSimpleName(), "unparseable url", murlex);
				}
			}
			
		} catch (JSONException e) {
			Log.e(TwitterService.class.getSimpleName(), e.getMessage());
		}
		return urlList;
	}
}