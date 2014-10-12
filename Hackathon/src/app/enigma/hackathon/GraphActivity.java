package app.enigma.hackathon;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import app.enigma.hackathon.adapter.NavigationDrawableLvAdapter;
import app.enigma.hackathon.api.LineGraph;
import app.enigma.hackathon.api.PieGraph;
import app.enigma.hackathon.object.NavigationDrawerItem;
import app.enigma.hackathon.object.User;
import app.enigma.hackathon.util.Hackathon;
import app.enigma.hackathon.util.ShakeDetector;
import app.enigma.hackathon.util.ShakeDetector.OnShakeListener;

public class GraphActivity extends Activity {
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	LinearLayout chartContainer;

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);

		loadActionBar();

		chartContainer = (LinearLayout) findViewById(R.id.chart_container);


		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		//		ShakeDetector initialization
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector();
		mShakeDetector.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
				//				Toast.makeText(getBaseContext(), "This is the " + count + " time(s) you have shake me!", Toast.LENGTH_SHORT).show();

				startActivity(new Intent(GraphActivity.this, SendEmailActivity.class));
			}
		});		
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle your other action bar items...
		switch (item.getItemId()) {
		case R.id.action_send_email:
			startActivity(new Intent(this, SendEmailActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@SuppressLint("NewApi") 
	private void loadActionBar() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerList.setAdapter(new NavigationDrawableLvAdapter(this));

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mDrawerLayout.closeDrawers();

				switch(position) {
				case NavigationDrawerItem.BLOCK:
					startActivity(new Intent(GraphActivity.this, BlockActivity.class));
					break;
				case NavigationDrawerItem.BOUNCE:
					startActivity(new Intent(GraphActivity.this, BounceActivity.class));
					break;
				case NavigationDrawerItem.INVALID_EMAIL:
					startActivity(new Intent(GraphActivity.this, InvalidEmailActivity.class));
					break;
				case NavigationDrawerItem.UNSUBSCRIBES:
					startActivity(new Intent(GraphActivity.this, UnsubscribesActivity.class));
					break;
				case NavigationDrawerItem.SPAM_REPORT:
					startActivity(new Intent(GraphActivity.this, SpamReportActivity.class));
					break;
				case NavigationDrawerItem.LOGOUT:
					Hackathon.setLogin(GraphActivity.this, new User(null, null));
					finish();
					startActivity(new Intent(GraphActivity.this, LoginActivity.class));
					break;
				}
			}
		});

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, R.string.app_name,
				R.string.app_name);

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (Build.VERSION.SDK_INT >= 18) {
			getActionBar().setHomeAsUpIndicator(
					getResources().getDrawable(R.drawable.ic_navigation_drawer));
		}

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_dropdown_item,
				new String[] {"Line graph", "Pie chart"});
		dataAdapter
		.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

		getActionBar().setListNavigationCallbacks(dataAdapter,
				new OnNavigationListener() {
			@Override
			public boolean onNavigationItemSelected(int itemPosition,
					long itemId) {
				switch (itemPosition) {
				case 0:
					new LineGraph(GraphActivity.this, chartContainer).execute();
					break;
				case 1:
					new PieGraph(GraphActivity.this, chartContainer).execute();
					break;
				}
				return false;
			}
		});

	}
}