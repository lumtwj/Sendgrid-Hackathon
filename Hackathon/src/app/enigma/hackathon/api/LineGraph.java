package app.enigma.hackathon.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
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

public class LineGraph extends AsyncTask<String, Void, GeneralStatistic[]> {
	public static final String STATS = Hackathon.API_ROOT + "stats.get.json?";
	public static final String DAYS = "days";

	Activity activity;
	LinearLayout chartContainer;

	public LineGraph(Activity activity, LinearLayout chartContainer) {
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
		try {
			openChart(list);
		} 
		catch (ParseException e) {e.printStackTrace();}
	}

	private void openChart(GeneralStatistic[] list) throws ParseException {
		chartContainer.removeAllViews();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

		// Creating TimeSeries for Visits
		TimeSeries delieveredSeries = new TimeSeries("Delivered");
		TimeSeries unsubscribesSeries = new TimeSeries("Unsubscribes");
		TimeSeries bouncesSeries = new TimeSeries("Bounces");
		TimeSeries uniqueClicksSeries = new TimeSeries("Unique clicks");
		TimeSeries clicksSeries = new TimeSeries("Clicks");
		TimeSeries opensSeries = new TimeSeries("Opens");
		TimeSeries uniqueOpensSeries = new TimeSeries("Unique ppens");
		TimeSeries requestsSeries = new TimeSeries("Requests");

		// Adding data to Visits and Views Series
		for (int i = 0; i < list.length; i++){
			delieveredSeries.add(sdf.parse(list[i].getDate()), list[i].getDelivered());
			unsubscribesSeries.add(sdf.parse(list[i].getDate()), list[i].getUnsubscribes());
			bouncesSeries.add(sdf.parse(list[i].getDate()), list[i].getBounces());
			uniqueClicksSeries.add(sdf.parse(list[i].getDate()), list[i].getUnique_clicks());
			clicksSeries.add(sdf.parse(list[i].getDate()), list[i].getClicks());
			opensSeries.add(sdf.parse(list[i].getDate()), list[i].getOpens());
			uniqueOpensSeries.add(sdf.parse(list[i].getDate()), list[i].getUnique_opens());
			requestsSeries.add(sdf.parse(list[i].getDate()), list[i].getRequests());
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		// Adding Visits Series to the dataset
		dataset.addSeries(delieveredSeries);
		dataset.addSeries(unsubscribesSeries);
		dataset.addSeries(bouncesSeries);
		dataset.addSeries(uniqueClicksSeries);
		dataset.addSeries(clicksSeries);
		dataset.addSeries(opensSeries);
		dataset.addSeries(uniqueOpensSeries);
		dataset.addSeries(requestsSeries);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

		multiRenderer.setChartTitle("Chart");
		multiRenderer.setXTitle("Days");
		multiRenderer.setYTitle("Count");
		multiRenderer.setZoomButtonsVisible(true);

		// Adding visitsRenderer and viewsRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(getLines(Color.GREEN));
		multiRenderer.addSeriesRenderer(getLines(Color.parseColor("#FF794D")));
		multiRenderer.addSeriesRenderer(getLines(Color.YELLOW));
		multiRenderer.addSeriesRenderer(getLines(Color.BLUE));
		multiRenderer.addSeriesRenderer(getLines(Color.GRAY));
		multiRenderer.addSeriesRenderer(getLines(Color.parseColor("#3300CC")));
		multiRenderer.addSeriesRenderer(getLines(Color.MAGENTA));
		multiRenderer.addSeriesRenderer(getLines(Color.RED));

		multiRenderer.setClickEnabled(true);
		multiRenderer.setSelectableBuffer(10);
		multiRenderer.setLabelsTextSize(30.0f);
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setBackgroundColor(Color.BLACK);
		multiRenderer.setLegendTextSize(30.0f);
		multiRenderer.setMargins(new int[] {80, 80, 80, 80});
		multiRenderer.setChartTitleTextSize(30.0f);
		multiRenderer.setAxisTitleTextSize(40.0f);
		multiRenderer.setPointSize(10.0f);
		//		multiRenderer.setLegendHeight(10);

		// Creating a Time Chart
		final GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(activity, dataset, multiRenderer,"dd-MMM-yyyy");



		// Setting a click event listener for the graph
		mChart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Format formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

				SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();

				if (seriesSelection != null) {
					int seriesIndex = seriesSelection.getSeriesIndex();
					String selectedSeries="Visits";
					if(seriesIndex==0)
						selectedSeries = "Visits";
					else
						selectedSeries = "Views";

					// Getting the clicked Date ( x value )
					long clickedDateSeconds = (long) seriesSelection.getXValue();
					Date clickedDate = new Date(clickedDateSeconds);
					String strDate = formatter.format(clickedDate);

					// Getting the y value
					int amount = (int) seriesSelection.getValue();

					// Displaying Toast Message
					Toast.makeText(
							activity,
							selectedSeries + " on "  + strDate + " : " + amount ,
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		// Adding the Line Chart to the LinearLayout
		chartContainer.addView(mChart);
	}

	private XYSeriesRenderer getLines(int c) {
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		renderer.setLineWidth(10);
		renderer.setDisplayChartValues(true);
		renderer.setColor(c);
		renderer.setDisplayChartValuesDistance(10);

		return renderer;
	}
}
