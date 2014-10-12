package app.enigma.hackathon.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import app.enigma.hackathon.object.GeneralStatistic;
import app.enigma.hackathon.object.User;
import app.enigma.hackathon.util.Hackathon;

import com.google.gson.Gson;

public class PieGraph extends AsyncTask<String, Void, GeneralStatistic[]> {
	public static final String STATS = Hackathon.API_ROOT + "stats.get.json?";
	public static final String DAYS = "days";

	Activity activity;
	LinearLayout chartContainer;

	public PieGraph(Activity activity, LinearLayout chartContainer) {
		this.activity = activity;
		this.chartContainer = chartContainer;
	}

	@Override
	protected GeneralStatistic[] doInBackground(String... arg0) {
		GeneralStatistic[] list = null;

		User user = Hackathon.getLogin(activity);

		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

		params.add(new BasicNameValuePair(Hackathon.USERNAME, user.getUsername()));
		params.add(new BasicNameValuePair(Hackathon.PASSWORD, user.getPassword()));
		params.add(new BasicNameValuePair(DAYS, "2"));

		String uri = STATS + URLEncodedUtils.format(params, "utf-8");

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet();

			request.setURI(new URI(uri));
			HttpResponse response = httpclient.execute(request);

			String json = EntityUtils.toString(response.getEntity());

			Gson gson = new Gson();
			list = gson.fromJson(json, GeneralStatistic[].class);
		} 
		catch (URISyntaxException e) {e.printStackTrace();} 
		catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}

		return list;
	}

	@Override
	protected void onPostExecute(GeneralStatistic[] list) {
		int repeat_bounces = 0, unique_clicks = 0, spamreports = 0, 
				delivered = 0, unsubscribes = 0, repeat_unsubscribes = 0, 
				unique_opens = 0, opens = 0, bounces = 0, spam_drop = 0, requests = 0, 
				invalid_email = 0, clicks = 0, blocked = 0, repeat_spamreports = 0;

		for (GeneralStatistic gs : list) {
			repeat_bounces += gs.getRepeat_bounces();
			unique_clicks += gs.getUnique_clicks();
			spamreports += gs.getSpamreports();
			delivered += gs.getDelivered();
			unsubscribes += gs.getUnsubscribes();
			repeat_unsubscribes += gs.getRepeat_unsubscribes();
			unique_opens += gs.getUnique_opens();
			opens += gs.getOpens();
			bounces += gs.getBounces();
			spam_drop += gs.getSpam_drop();
			requests += gs.getRequests();
			invalid_email += gs.getInvalid_email();
			clicks += gs.getClicks();
			blocked += gs.getBlocked();
			repeat_spamreports += gs.getRepeat_spamreports();
		}

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		openChart(
				df.format(delivered*100.0f/requests),
				df.format(opens*100.0f/delivered),
				df.format(unique_opens*100.0f/delivered),
				df.format(clicks*100.0f/delivered),
				df.format(unique_clicks*100.0f/delivered),
				df.format(unsubscribes*100.0f/delivered),
				df.format(bounces*100.0f/requests)
				);
	}

	private void openChart(String delivered, String opens, String uniqueOpens, String clicks, String uniqueClicks, String unsubscribes, String bounce){
		chartContainer.removeAllViews();

		int []colors = new int[]{Color.GREEN, Color.parseColor("#FF794D"), Color.YELLOW, Color.BLUE, Color.GRAY, Color.parseColor("#3300CC"), Color.MAGENTA};
		final CategorySeries series = new CategorySeries("pie"); // adding series to charts. //collect 3 value in array. therefore add three series.
		series.add("Delivered", Double.parseDouble(delivered));            
		series.add("Opens", Double.parseDouble(opens));            
		series.add("Unique opens", Double.parseDouble(uniqueOpens));            
		series.add("Clicks", Double.parseDouble(clicks));            
		series.add("Unique clicks", Double.parseDouble(uniqueClicks));            
		series.add("Unsubscribes", Double.parseDouble(unsubscribes));            
		series.add("Bounce", Double.parseDouble(bounce));            
		// add three colors for three series respectively          
		// set style for series
		final DefaultRenderer renderer = new DefaultRenderer();
		for(int color : colors){
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			r.setDisplayBoundingPoints(true);

			r.setDisplayChartValuesDistance(5);
			r.setDisplayChartValues(true);
			r.setChartValuesTextSize(15);
			renderer.addSeriesRenderer(r);
		}
		renderer.isInScroll();
		renderer.setZoomButtonsVisible(true);   //set zoom button in Graph
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.BLACK); //set background color
		renderer.setChartTitle("% distribution");
		renderer.setChartTitleTextSize(30.0f);
		renderer.setShowLabels(true);  
		renderer.setLabelsTextSize(35);
		renderer.setLegendTextSize(35);
		renderer.setDisplayValues(true);
		renderer.setMargins(new int[] {80, 80, 80, 80});
		renderer.setClickEnabled(true);
		renderer.setSelectableBuffer(10);

		final GraphicalView mPie = (GraphicalView)ChartFactory.getPieChartView(activity, series, renderer);

		mPie.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SeriesSelection seriesSelection = mPie.getCurrentSeriesAndPoint();

				if (seriesSelection == null) {
					Toast
					.makeText(activity, "No chart element was clicked",
							Toast.LENGTH_SHORT)
							.show();
				} else {

					for (int i = 0; i <series.getItemCount(); i++) {
						renderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
					}
					mPie.repaint();


					Toast.makeText(
							activity,
							series.getCategory(seriesSelection.getPointIndex()) + ": " + series.getValue(seriesSelection.getPointIndex()) + "%",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		// Getting a reference to LinearLayout of the MainActivity Layout
		chartContainer.addView(mPie);
	}
}
