package client;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static ArrayList<String> parseTopics(String blogContent) {
		//needs modification of whole function body
		ArrayList<String> topics = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\#(.+?)\\#");
        Matcher matcher = pattern.matcher(blogContent);
        while(matcher.find()) {
            topics.add(matcher.group(1));
        }
		return topics;
	}
	
}
