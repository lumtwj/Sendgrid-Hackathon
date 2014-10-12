import java.awt.EventQueue;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sendgrid.SendGrid;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class SendG {

	private JFrame frmLoginPage;
	private JTextField txtUserName;

	static InputStream is = null;
	static JSONArray jObj = null;
	static String json = "";
	private static final String STATUS = "status";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	JSONArray user = null;
	private JPasswordField txtPassword;
	static String username;
	static String password;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SendG window = new SendG();
					window.frmLoginPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SendG() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginPage = new JFrame();
		frmLoginPage.getContentPane().setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		frmLoginPage.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Daemian\\Desktop\\SendGrid\\SendGrid2\\images\\icon1.png"));
		frmLoginPage.setTitle("Login Page");
		frmLoginPage.setResizable(false);
		frmLoginPage.setBounds(100, 100, 450, 300);
		frmLoginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginPage.getContentPane().setLayout(null);

		txtUserName = new JTextField();
		txtUserName.setBounds(207, 50, 116, 22);
		frmLoginPage.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(207, 92, 116, 22);
		frmLoginPage.getContentPane().add(txtPassword);

		final JLabel lblErrorMsg = new JLabel("Invalid Username/Password");
		lblErrorMsg.setForeground(Color.RED);
		lblErrorMsg.setBounds(139, 145, 170, 16);
		frmLoginPage.getContentPane().add(lblErrorMsg);
		lblErrorMsg.setVisible(false);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {

					JSONArray object = getJSONFromUrl("https://sendgrid.com/api/profile.get.json?api_user="
							+ txtUserName.getText()
							+ "&api_key="
							+ txtPassword.getText());
					for (int i = 0; i < object.length(); ++i) {
						JSONObject rec = object.getJSONObject(i);
						// int id = rec.getInt("status");
						String loc = rec.getString("username");
						System.out.println(loc);

						storeUserName(txtUserName.getText());
						storePassword(txtPassword.getText());
						SendGMainPage2 mp = new SendGMainPage2();
						mp.setVisible(true);
						frmLoginPage.hide();
						
						lblErrorMsg.setVisible(false);
					}
				} catch (JSONException e) {
					// e.printStackTrace();
					lblErrorMsg.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnLogin.setBounds(160, 174, 97, 25);
		frmLoginPage.getContentPane().add(btnLogin);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(111, 44, 72, 34);
		frmLoginPage.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(111, 95, 95, 16);
		frmLoginPage.getContentPane().add(lblPassword);

	}
	
	public String getUsername() {
		
		return username;
	}
	
	public void storeUserName(String username) {
		
		 this.username = username;
	}
	public String getPassword() {
			return this.password;
	}
	
	public void storePassword(String password) {
		this.password = password;
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
			//System.out.println(json.toString() + "sds");
		} catch (Exception e) {

		}

		// try parse the string to a JSON object

		jObj = new JSONArray(json);

		// return JSON String
		return jObj;

	}
}
