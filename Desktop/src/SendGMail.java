import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.UIManager;
import java.awt.Toolkit;

public class SendGMail extends JFrame {

	private JPanel contentPane;
	static SendG a = new SendG();
	static ArrayList<String> addressBook = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SendGMail frame = new SendGMail();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SendGMail() {
		setTitle("Send Email");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Daemian\\Desktop\\SendGrid\\SendGrid2\\images\\icon1.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 508);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(37, 47, 56, 16);
		contentPane.add(lblTo);

		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(37, 84, 56, 16);
		contentPane.add(lblFrom);

		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(37, 126, 56, 16);
		contentPane.add(lblSubject);

		JLabel lblEnterText = new JLabel("Enter Text:");
		lblEnterText.setBounds(37, 171, 101, 16);
		contentPane.add(lblEnterText);

		final JTextArea txtTo = new JTextArea();
		txtTo.setBounds(122, 44, 442, 22);
		contentPane.add(txtTo);

		final JTextArea txtFrom = new JTextArea();
		txtFrom.setBounds(122, 81, 442, 22);
		contentPane.add(txtFrom);

		final JTextArea txtSubject = new JTextArea();
		txtSubject.setBounds(122, 123, 442, 22);
		contentPane.add(txtSubject);

		final JTextArea textArea = new JTextArea();
		textArea.setBounds(122, 171, 442, 193);
		contentPane.add(textArea);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();

			}
		});
		btnCancel.setBounds(122, 393, 97, 25);
		contentPane.add(btnCancel);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SendGrid sendgrid = new SendGrid(a.getUsername(), a
						.getPassword());

				SendGrid.Email email = new SendGrid.Email();
				
				if(addressBook.size() == 0)
				{
					addressBook.add(txtTo.getText());
				}
				for(int i = 0; i< addressBook.size(); i++)
				{
					email.addTo(addressBook.get(i));
					email.setFrom(txtFrom.getText());
					email.setSubject(txtSubject.getText());
					String emailtext = textArea.getText();
					System.out.println(emailtext);
					email.setText(emailtext);
					// email.setText("emailtext \nIf you would like to unsubscribes and stop receiving these emails click here: <% %>.");
					//email.setText("My first email with SendGrid Java! http://www.google.com");

					email.addHeader(
							"X-SMTPAPI",
							"{\"filters\":{\"subscriptiontrack\":{\"settings\":{\"enable\":1,\"text/html\":\"Unsubscribe <% %>\",\"text/plain\":\"Unsubscribe Here: <% unsubscribe %>\"}}}}");
					email.addHeader("X-SMTPAPI",
							"{\"filters\": {\"clicktrack\": {\"setting\": {\"enable\":1}}}}");

					try {
						SendGrid.Response response = sendgrid.send(email);
						System.out.println(response.getMessage());
						//JOptionPane.showMessageDialog(null, "Email Send");

					} catch (SendGridException e1) {
						System.err.println(e1);
					}
				}
			}
		});
		btnSend.setBounds(467, 393, 97, 25);
		contentPane.add(btnSend);

		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				int status = chooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					if (file == null) {
						return;
					}

					String fileName = chooser.getSelectedFile()
							.getAbsolutePath();
					try {
						BufferedReader in = new BufferedReader(new FileReader(
								fileName));
						String str;
						str = in.readLine();
						while ((str = in.readLine()) != null) {
							addressBook.add(str);
						}
						in.close();
						String add="";
						for(int i = 0; i < addressBook.size(); i++)
						{
							if(i != addressBook.size()-1)
							{
								add = add.concat(addressBook.get(i)+", ");
							}
							else
							{
								add = add.concat(addressBook.get(i));
							}
							
						}
						txtTo.setText(add);
					} catch (IOException e1) {
						System.out.println("File Read Error");
					}
				}
			}
		});
		btnImport.setBounds(576, 43, 81, 25);
		contentPane.add(btnImport);
	}
}
