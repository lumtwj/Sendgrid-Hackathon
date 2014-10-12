package app.enigma.hackathon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;
import app.enigma.hackathon.api.Login;
import app.enigma.hackathon.util.Hackathon;

public class LoginActivity extends Activity {
	ViewFlipper vfSwitchView;
	EditText etUsername, etPassword;
	Button btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (Hackathon.getLogin(this) != null) {
			finish();
			startActivity(new Intent(this, GraphActivity.class));
		}
		else {
			vfSwitchView = (ViewFlipper) findViewById(R.id.vfSwitchView);
			etUsername = (EditText) findViewById(R.id.etUsername);
			etPassword = (EditText) findViewById(R.id.etPassword);
			btnLogin = (Button) findViewById(R.id.btnLogin);

			etUsername.addTextChangedListener(validate());
			etPassword.addTextChangedListener(validate());

			etPassword.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() != KeyEvent.ACTION_DOWN) {
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);

						login(v);

						return true;
					}
					else {
						return false;
					}
				}
			});
		}
	}

	public void login(View v) {
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();

		new Login(this, vfSwitchView).execute(username, password);
	}

	public TextWatcher validate() {
		return new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (etUsername.getText().toString().length() == 0 || etPassword.getText().toString().length() == 0) {
					btnLogin.setEnabled(false);
				}
				else {
					btnLogin.setEnabled(true);
				}
			}
		};
	}
}
