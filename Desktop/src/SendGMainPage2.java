import java.awt.Button;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.GridLayout;
import javax.swing.UIManager;

public class SendGMainPage2 extends JFrame {

	private JPanel contentPane;

	static InputStream is = null;
	static JSONArray jObj = null;
	static String json = "";
	private static final String STATUS = "status";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	static String un = "";
	static String pw = "";
	static SendG a = new SendG();
	JSONArray user = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					SendGMainPage2 frame = new SendGMainPage2();
					frame.setVisible(true);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public SendGMainPage2() throws IOException {
		setTitle("Menu");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Daemian\\Desktop\\SendGrid\\SendGrid2\\images\\icon1.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 703, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 122, 472);
		contentPane.add(panel);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		scrollPane.setBounds(122, 0, 563, 472);
		contentPane.add(scrollPane);

		final JLabel lblResult = new JLabel("");
		lblResult.setBounds(162, 0, 56, 16);
		scrollPane.add(lblResult);
		ImageIcon blockimage = new ImageIcon("C:/Users/Daemian/Desktop/SendGrid/SendGrid2/images/blocked.png");	
		JButton btnBlocked = new JButton(blockimage);
		btnBlocked.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				un = a.getUsername();
				pw = a.getPassword();

				try {
					JSONArray object = getJSONFromUrl("https://sendgrid.com/api/blocks.get.json?api_user="
							+ un + "&api_key=" + pw);

					String combineWords = "";

					for (int i = 0; i < object.length(); ++i) {
						JSONObject rec = object.getJSONObject(i);
						String status = rec.getString("status");
						String reason = rec.getString("reason");
						String email = rec.getString("email");

						combineWords = combineWords.concat("<html>Status: "
								+ status + ",");
						combineWords = combineWords.concat("Reason: " + reason
								+ ",");
						combineWords = combineWords.concat("Email: " + email
								+ ",,");
					}
					combineWords = combineWords.replaceAll(",", "<br/>");
					lblResult.setText(combineWords);

				} finally {
				}
				
			}
		});
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		panel.add(btnBlocked);
		
		
		ImageIcon bounceimage = new ImageIcon("C:/Users/Daemian/Desktop/SendGrid/SendGrid2/images/bounce.png");	
		JButton btnBounce = new JButton(bounceimage);
		btnBounce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				un = a.getUsername();
				pw = a.getPassword();

				try {
					JSONArray object = getJSONFromUrl("https://sendgrid.com/api/bounces.get.json?api_user="
							+ un + "&api_key=" + pw);

					String combineWords = "";

					for (int i = 0; i < object.length(); ++i) {
						JSONObject rec = object.getJSONObject(i);
						String status = rec.getString("status");
						String reason = rec.getString("reason");
						String email = rec.getString("email");

						combineWords = combineWords.concat("<html>Status: "
								+ status + ",");
						combineWords = combineWords.concat("Reason: " + reason
								+ ",");
						combineWords = combineWords.concat("Email: " + email
								+ ",,");
					}
					combineWords = combineWords.replaceAll(",", "<br/>");
					lblResult.setText(combineWords);
				} catch (JSONException ex) {
					// e.printStackTrace();
					System.out.println(ex);
				}
				
			}
		});
		panel.add(btnBounce);
		
		ImageIcon spamImage = new ImageIcon("C:/Users/Daemian/Desktop/SendGrid/SendGrid2/images/spam.png");
		JButton btnSpam = new JButton(spamImage);
		btnSpam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				un = a.getUsername();
				pw = a.getPassword();

				try {
					JSONArray object = getJSONFromUrl("https://sendgrid.com/api/spamreports.get.json?api_user="
							+ un + "&api_key=" + pw);

					String combineWords = "";

					for (int i = 0; i < object.length(); ++i) {
						JSONObject rec = object.getJSONObject(i);
						String ip = rec.getString("ip");
						String email = rec.getString("email");

						combineWords = combineWords.concat("<html>ip: " + ip
								+ ",");
						combineWords = combineWords.concat("Email: " + email
								+ ",,");
					}
					combineWords = combineWords.replaceAll(",", "<br/>");
					lblResult.setText(combineWords);

				} finally {
				}
				
			}
		});
		panel.add(btnSpam);
		
		ImageIcon invalidImage = new ImageIcon("C:/Users/Daemian/Desktop/SendGrid/SendGrid2/images/invalid.png");
		JButton btnInvalid = new JButton(invalidImage);
		btnInvalid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				un = a.getUsername();
				pw = a.getPassword();

				try {
					JSONArray object = getJSONFromUrl("https://sendgrid.com/api/invalidemails.get.json?api_user="
							+ un + "&api_key=" + pw);

					String combineWords = "";

					for (int i = 0; i < object.length(); ++i) {
						JSONObject rec = object.getJSONObject(i);
						String reason = rec.getString("reason");
						String email = rec.getString("email");

						combineWords = combineWords.concat("<html>Reason: "
								+ reason + ",");
						combineWords = combineWords.concat("Email: " + email
								+ ",,");
					}
					combineWords = combineWords.replaceAll(",", "<br/>");
					lblResult.setText(combineWords);

				} finally {
				}
				
			}
		});
		panel.add(btnInvalid);
		
		
		ImageIcon unsubImage = new ImageIcon("C:/Users/Daemian/Desktop/SendGrid/SendGrid2/images/unsub.png");
		JButton btnUnsub = new JButton(unsubImage);
		btnUnsub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				un = a.getUsername();
				pw = a.getPassword();

				try {
					JSONArray object = getJSONFromUrl("https://sendgrid.com/api/unsubscribes.get.json?api_user="
							+ un + "&api_key=" + pw);

					String combineWords = "";

					for (int i = 0; i < object.length(); ++i) {
						JSONObject rec = object.getJSONObject(i);
						String email = rec.getString("email");

						combineWords = combineWords.concat("<html>Email: "
								+ email + ",,");
					}
					combineWords = combineWords.replaceAll(",", "<br/>");
					lblResult.setText(combineWords);

				} finally {
				}
				
			}
		});
		panel.add(btnUnsub);
		
		ImageIcon emailImage = new ImageIcon("C:/Users/Daemian/Desktop/SendGrid/SendGrid2/images/email.png");
		JButton btnNewButton = new JButton(emailImage);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SendGMail mail = new SendGMail();
				mail.setVisible(true);
				
			}
		});
		
		/*ImageIcon graphImage = new ImageIcon("C:/Users/Daemian/Desktop/SendGrid/SendGrid2/images/graph.png");
		JButton btnGraph = new JButton(graphImage);
		btnGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}
		});*/
		//panel.add(btnGraph);
		panel.add(btnNewButton);
		
		
		
		
		

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
