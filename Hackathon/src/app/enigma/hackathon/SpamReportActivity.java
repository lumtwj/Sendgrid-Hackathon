package app.enigma.hackathon;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.ListView;
import app.enigma.hackathon.adapter.SpamReportLvAdapter;
import app.enigma.hackathon.api.SpamReports;
import app.enigma.hackathon.object.SpamReport;

public class SpamReportActivity extends Activity {
	SwipeRefreshLayout swipeLayout;
	ListView lvSpamReport;
	ArrayList<SpamReport> alSpamReport;
	SpamReportLvAdapter aaSpamReport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spam_report);

		ActionBar ab = this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		lvSpamReport = (ListView) findViewById(R.id.list);

		alSpamReport = new ArrayList<SpamReport> ();
		aaSpamReport = new SpamReportLvAdapter(this, alSpamReport);
		lvSpamReport.setAdapter(aaSpamReport);

		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, 
				android.R.color.holo_green_light, 
				android.R.color.holo_orange_light, 
				android.R.color.holo_red_light);

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new SpamReports(SpamReportActivity.this, swipeLayout, alSpamReport, aaSpamReport).execute();
			}
		});

		new SpamReports(this, swipeLayout, alSpamReport, aaSpamReport).execute();
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
