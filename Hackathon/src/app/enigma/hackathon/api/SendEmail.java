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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.widget.ViewFlipper;
import app.enigma.hackathon.object.User;
import app.enigma.hackathon.util.Hackathon;

public class SendEmail extends AsyncTask<String, Void, String> {
	public static final String MAIL = Hackathon.API_ROOT + "mail.send.json?";

	Activity activity;
	ViewFlipper vfSwitchView;

	public SendEmail(Activity activity, ViewFlipper vfSwitchView) {
		this.activity = activity;
		this.vfSwitchView = vfSwitchView;
	}

	@Override
	protected void onPreExecute() {
		vfSwitchView.setDisplayedChild(1);
	}

	@Override
	protected String doInBackground(String... mail) {
		String msg = null;

		User user = Hackathon.getLogin(activity);

		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

		params.add(new BasicNameValuePair(Hackathon.USERNAME, user.getUsername()));
		params.add(new BasicNameValuePair(Hackathon.PASSWORD, user.getPassword()));
		params.add(new BasicNameValuePair("to", mail[0]));
		params.add(new BasicNameValuePair("subject", mail[1]));
		params.add(new BasicNameValuePair("from", mail[2]));
		params.add(new BasicNameValuePair("text", mail[3]));

		String uri = MAIL + URLEncodedUtils.format(params, "utf-8");

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet();

			request.setURI(new URI(uri));
			HttpResponse response = httpclient.execute(request);

			msg = EntityUtils.toString(response.getEntity());
			Log.d(activity.getPackageName(), msg);
		} 
		catch (URISyntaxException e) {e.printStackTrace();} 
		catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}

		return msg;
	}

	@Override
	protected void onPostExecute(String msg) {
		if (msg.equals("{\"message\":\"success\"}")) {
			activity.finish();
			Toast.makeText(activity, "Message sent!", Toast.LENGTH_SHORT).show();
		}
		else {
			vfSwitchView.setDisplayedChild(0);
			Toast.makeText(activity, "Message not sent!", Toast.LENGTH_LONG).show();
		}
	}
}
