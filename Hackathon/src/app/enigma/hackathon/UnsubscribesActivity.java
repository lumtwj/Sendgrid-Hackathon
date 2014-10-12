package app.enigma.hackathon;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.ListView;
import app.enigma.hackathon.adapter.UnsubscribesLvAdapter;
import app.enigma.hackathon.api.Unsubscribes;
import app.enigma.hackathon.object.Unsubscribe;

public class UnsubscribesActivity extends Activity {
	SwipeRefreshLayout swipeLayout;
	ListView lvUnsubscribes;
	ArrayList<Unsubscribe> alUnsubscribes;
	UnsubscribesLvAdapter aaUnsubscribes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unsubscribes);

		ActionBar ab = this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		lvUnsubscribes = (ListView) findViewById(R.id.list);

		alUnsubscribes = new ArrayList<Unsubscribe> ();
		aaUnsubscribes = new UnsubscribesLvAdapter(this, alUnsubscribes);
		lvUnsubscribes.setAdapter(aaUnsubscribes);

		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, 
				android.R.color.holo_green_light, 
				android.R.color.holo_orange_light, 
				android.R.color.holo_red_light);

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Unsubscribes(UnsubscribesActivity.this, swipeLayout, alUnsubscribes, aaUnsubscribes).execute();
			}
		});

		new Unsubscribes(this, swipeLayout, alUnsubscribes, aaUnsubscribes).execute();
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
