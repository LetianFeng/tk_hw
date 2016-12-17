package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageUtil {

	public static BlogMessage jsonToBlog(String message) {
		Gson gson = new GsonBuilder()
				.setDateFormat(ClientConfig.DEFAULT_DATE_FORMAT).create();
		BlogMessage bm = gson.fromJson(message, BlogMessage.class);
		return bm;
	}
	
	public static String blogToJson(BlogMessage bm) {
		Gson gson = new GsonBuilder()
				.setDateFormat(ClientConfig.DEFAULT_DATE_FORMAT).create();
		String json = gson.toJson(bm);
		return json;
	}
	
}
