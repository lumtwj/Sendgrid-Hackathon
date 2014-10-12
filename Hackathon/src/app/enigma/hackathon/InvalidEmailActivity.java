package app.enigma.hackathon;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.ListView;
import app.enigma.hackathon.adapter.InvalidEmailsLvAdapter;
import app.enigma.hackathon.api.InvalidEmails;
import app.enigma.hackathon.object.InvalidEmail;

public class InvalidEmailActivity extends Activity {
	SwipeRefreshLayout swipeLayout;
	ListView lvInvalidEmail;
	ArrayList<InvalidEmail> alInvalidEmail;
	InvalidEmailsLvAdapter aaInvalidEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invalid_email);

		ActionBar ab = this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		lvInvalidEmail = (ListView) findViewById(R.id.list);

		alInvalidEmail = new ArrayList<InvalidEmail> ();
		aaInvalidEmail = new InvalidEmailsLvAdapter(this, alInvalidEmail);
		lvInvalidEmail.setAdapter(aaInvalidEmail);

		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, 
				android.R.color.holo_green_light, 
				android.R.color.holo_orange_light, 
				android.R.color.holo_red_light);


		swipeLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new InvalidEmails(InvalidEmailActivity.this, swipeLayout, alInvalidEmail, aaInvalidEmail).execute();
			}
		});

		new InvalidEmails(this, swipeLayout, alInvalidEmail, aaInvalidEmail).execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}

		return true;
	}
}
