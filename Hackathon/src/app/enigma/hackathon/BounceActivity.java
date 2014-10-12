package app.enigma.hackathon;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.ListView;
import app.enigma.hackathon.adapter.BouncesLvAdapter;
import app.enigma.hackathon.api.Bounces;
import app.enigma.hackathon.object.Bounce;

public class BounceActivity extends Activity {
	SwipeRefreshLayout swipeLayout;
	ListView lvBounce;
	ArrayList<Bounce> alBounce;
	BouncesLvAdapter aaBounce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bounce);

		ActionBar ab = this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		lvBounce = (ListView) findViewById(R.id.list);

		alBounce = new ArrayList<Bounce> ();
		aaBounce = new BouncesLvAdapter(this, alBounce);
		lvBounce.setAdapter(aaBounce);

		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, 
				android.R.color.holo_green_light, 
				android.R.color.holo_orange_light, 
				android.R.color.holo_red_light);

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Bounces(BounceActivity.this, swipeLayout, alBounce, aaBounce).execute();
			}
		});

		new Bounces(this, swipeLayout, alBounce, aaBounce).execute();
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
