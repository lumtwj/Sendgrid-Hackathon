package app.enigma.hackathon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import app.enigma.hackathon.object.User;

public class Hackathon {
	public static final String API_ROOT = "https://api.sendgrid.com/api/";
	public static final String USERNAME = "api_user";
	public static final String PASSWORD = "api_key";

	public static void setLogin(Context context, User user) {
		SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		Editor editor = pref.edit();

		editor.putString(USERNAME, user.getUsername());
		editor.putString(PASSWORD, user.getPassword());

		editor.commit();
	}

	public static User getLogin(Context context) {
		SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		String username = pref.getString(USERNAME, null);
		String password = pref.getString(PASSWORD, null);

		User user = null;

		if (username != null && password != null) {
			user = new User(username, password);
		}

		return user;
	}
}
