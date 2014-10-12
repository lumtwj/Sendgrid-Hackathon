package app.enigma.hackathon.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import android.widget.ViewFlipper;
import app.enigma.hackathon.GraphActivity;
import app.enigma.hackathon.object.User;
import app.enigma.hackathon.util.Hackathon;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Login extends AsyncTask<String, Void, User[]> {
	public static final String PROFILE = Hackathon.API_ROOT + "profile.get.json?";

	Activity activity;
	ViewFlipper vfSwitchView;
	String username, password;

	public Login(Activity activity, ViewFlipper vfSwitchView) {
		this.activity = activity;
		this.vfSwitchView = vfSwitchView;
	}

	@Override
	protected void onPreExecute() {
		vfSwitchView.setDisplayedChild(1);
	}

	@Override
	protected User[] doInBackground(String... login) {
		User[] list = null;

		username = login[0];
		password = login[1];

		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

		params.add(new BasicNameValuePair(Hackathon.USERNAME, username));
		params.add(new BasicNameValuePair(Hackathon.PASSWORD, password));

		String uri = PROFILE + URLEncodedUtils.format(params, "utf-8");

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet();

			request.setURI(new URI(uri));
			HttpResponse response = httpclient.execute(request);

			String json = EntityUtils.toString(response.getEntity());

			Gson gson = new Gson();
			list = gson.fromJson(json, User[].class);
		} 
		catch (URISyntaxException e) {e.printStackTrace();} 
		catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		catch (JsonSyntaxException e) {e.printStackTrace();}

		return list;
	}

	@Override
	protected void onPostExecute(User[] list) {
		if (list != null) {
			Hackathon.setLogin(activity, new User(username, password));
			activity.finish();
			activity.startActivity(new Intent(activity, GraphActivity.class));
			Toast.makeText(activity, "Login successfully", Toast.LENGTH_SHORT).show();
		}
		else {
			vfSwitchView.setDisplayedChild(0);
			Toast.makeText(activity, "Unable to authenticate user", Toast.LENGTH_LONG).show();
		}
	}
}
