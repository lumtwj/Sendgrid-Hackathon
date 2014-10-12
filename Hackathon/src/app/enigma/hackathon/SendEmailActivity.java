package app.enigma.hackathon;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ViewFlipper;
import app.enigma.hackathon.api.SendEmail;

public class SendEmailActivity extends Activity {
	ViewFlipper vfSwitchView;
	EditText etTo, etSubject, etFrom, etMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_email);

		ActionBar ab = this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		vfSwitchView = (ViewFlipper) findViewById(R.id.vfSwitchView);
		etTo = (EditText) findViewById(R.id.etTo);
		etSubject = (EditText) findViewById(R.id.etSubject);
		etFrom = (EditText) findViewById(R.id.etFrom);
		etMessage = (EditText) findViewById(R.id.etMessage);
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

	public void send(View v) {
		String to = etTo.getText().toString();
		String subject = etSubject.getText().toString();
		String from = etFrom.getText().toString();
		String message = etMessage.getText().toString();

		new SendEmail(this, vfSwitchView).execute(to, subject, from, message);
	}
}
