import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SendGridGraph extends JFrame {

	private JPanel contentPane;
	static SendG g = new SendG();
	static InputStream is = null;
	static JSONArray jObj = null;
	static String json = "";
	private static final String STATUS = "status";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";

	static ArrayList<SendGridGraphObject> gO = new ArrayList<SendGridGraphObject>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SendGridGraph frame = new SendGridGraph();
					frame.setVisible(true);

					String un = g.getUsername();
					String pw = g.getPassword();

					try {
						// JSONArray object =
						// getJSONFromUrl("https://api.sendgrid.com/api/stats.get.json?api_user="+un+"&api_key="+pw+"&days=2");
						JSONArray object = getJSONFromUrl("https://api.sendgrid.com/api/stats.get.json?api_user=xiangjie&api_key=random1990&days=2");
						for (int i = 0; i < object.length(); ++i) {
							JSONObject rec = object.getJSONObject(i);
							SendGridGraphObject gobject = new SendGridGraphObject();

							gobject.setDate(rec.getString("date"));
							gobject.setdelivered(rec.getInt("delivered"));
							gobject.setunsubscribes(rec.getInt("unsubscribes"));
							gobject.setinvalidemail(rec.getInt("invalid_email"));
							gobject.setbounces(rec.getInt("bounces"));
							gobject.setrepeatunsubscribes(rec
									.getInt("repeat_unsubscribes"));
							gobject.setuniqueClicks(rec.getInt("unique_clicks"));
							gobject.setblocked(rec.getInt("blocked"));
							gobject.setspam_drop(rec.getInt("spam_drop"));
							gobject.setrepeatBounces(rec
									.getInt("repeat_bounces"));
							gobject.setrepeatSpamreports(rec
									.getInt("repeat_spamreports"));
							gobject.setrequest(rec.getInt("requests"));
							gobject.setspamreports(rec.getInt("spamreports"));
							gobject.setclicks(rec.getInt("clicks"));
							gobject.setopens(rec.getInt("opens"));
							gobject.setuniqueopens(rec.getInt("unique_opens"));

							gO.add(gobject);

						}

					} finally {
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SendGridGraph() {
		setTitle("Graph");
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						"C:\\Users\\Daemian\\Desktop\\SendGrid\\SendGrid2\\images\\icon1.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 300);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSelectYourChoice = new JLabel("Select your Choice:");
		lblSelectYourChoice.setBounds(12, 0, 174, 56);
		contentPane.add(lblSelectYourChoice);

		final JCheckBox cbDelivered = new JCheckBox("Delivered");
		cbDelivered.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbDelivered.setBounds(22, 52, 113, 25);
		contentPane.add(cbDelivered);

		final JCheckBox cbUnsub = new JCheckBox("Unsubscribes");
		cbUnsub.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbUnsub.setBounds(22, 82, 113, 25);
		contentPane.add(cbUnsub);

		JCheckBox cbInvalidEmail = new JCheckBox("Invalid Email");
		cbInvalidEmail.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbInvalidEmail.setBounds(22, 112, 113, 25);
		contentPane.add(cbInvalidEmail);

		JCheckBox cbBounce = new JCheckBox("Bounces");
		cbBounce.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbBounce.setBounds(22, 142, 113, 25);
		contentPane.add(cbBounce);

		JCheckBox cbUniqueClicks = new JCheckBox("Unique Clicks");
		cbUniqueClicks.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbUniqueClicks.setBounds(22, 202, 113, 25);
		contentPane.add(cbUniqueClicks);

		JCheckBox cbBlocked = new JCheckBox("Blocked");
		cbBlocked.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbBlocked.setBounds(178, 52, 113, 25);
		contentPane.add(cbBlocked);

		JCheckBox cbSpamDrop = new JCheckBox("Spam Drop");
		cbSpamDrop.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbSpamDrop.setBounds(178, 82, 113, 25);
		contentPane.add(cbSpamDrop);

		JCheckBox cbRepeatBounces = new JCheckBox("Repeat Bounces");
		cbRepeatBounces.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbRepeatBounces.setBounds(178, 112, 125, 25);
		contentPane.add(cbRepeatBounces);

		JCheckBox cbRequest = new JCheckBox("Request");
		cbRequest.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbRequest.setBounds(178, 172, 113, 25);
		contentPane.add(cbRequest);

		JCheckBox cbClicks = new JCheckBox("Clicks");
		cbClicks.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbClicks.setBounds(178, 202, 113, 25);
		contentPane.add(cbClicks);

		JCheckBox cbOpen = new JCheckBox("Open");
		cbOpen.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbOpen.setBounds(338, 52, 113, 25);
		contentPane.add(cbOpen);

		JCheckBox cbUniqueOpen = new JCheckBox("Unique Opens");
		cbUniqueOpen.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbUniqueOpen.setBounds(338, 82, 113, 25);
		contentPane.add(cbUniqueOpen);

		JCheckBox cbRepeatunSub = new JCheckBox("Repeat Unsubscribes");
		cbRepeatunSub.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbRepeatunSub.setBounds(22, 172, 147, 25);
		contentPane.add(cbRepeatunSub);

		JCheckBox cbRepeatSpam = new JCheckBox("Repeat Spam Reports");
		cbRepeatSpam.setBackground(UIManager
				.getColor("EditorPane.selectionBackground"));
		cbRepeatSpam.setBounds(178, 142, 161, 25);
		contentPane.add(cbRepeatSpam);

		JButton btnGraph = new JButton("Generate Graph");
		btnGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

					for (int i = 0; i < gO.size(); i++) {
			
							line_chart_dataset.addValue(gO.get(i).getdelivered(),
									"graph", gO.get(i).getDate());
						
						
							line_chart_dataset.addValue(gO.get(i).getunsubscribes(),
									"graph", gO.get(i).getDate());
						
						
						
						
						
					}

					JFreeChart lineChartObject = ChartFactory.createLineChart(
							"Send Grid Graph", "Year", "Count",
							line_chart_dataset, PlotOrientation.VERTICAL, true,
							true, false);

					int width = 640;
					int height = 480;
					File lineChart = new File(
							"C:/Users/Daemian/Desktop/SendGrid/SendGrid2/line_Chart_example.png");
					ChartUtilities.saveChartAsPNG(lineChart, lineChartObject,
							width, height);

					String path = "C:/Users/Daemian/Desktop/SendGrid/SendGrid2/line_Chart_example.png";
					File file = new File(path);
					BufferedImage image = ImageIO.read(file);
					JLabel label = new JLabel(new ImageIcon(image));
					JFrame f = new JFrame();
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.getContentPane().add(label);
					f.pack();
					f.setLocation(200, 200);
					f.setVisible(true);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
				}
			}
		});
		btnGraph.setBounds(344, 202, 134, 25);
		contentPane.add(btnGraph);

	}

	public static JSONArray getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// System.out.println(line);
			}
			is.close();
			json = sb.toString();
			// System.out.println(json.toString() + "sds");
		} catch (Exception e) {

		}

		// try parse the string to a JSON object

		jObj = new JSONArray(json);

		// return JSON String
		return jObj;

	}
}
