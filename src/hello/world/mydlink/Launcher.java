package hello.world.mydlink;

import java.io.IOException;

import hello.world.mydlink.exception.MydlinkException;
import hello.world.mydlink.model.Mydlink;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Launcher extends Activity implements OnClickListener, Callback {
	private TextView viewEmail;
	private TextView viewPassword;
	private CheckBox viewStaySignIn;
	private String email, password;
	private boolean staySignIn;
	private Button signin;
	private LinearLayout signinfield;
	private Handler mHandle = new Handler(this);

	private Mydlink mydlink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		signinfield = (LinearLayout) findViewById(R.id.signinfield);
		viewEmail = (TextView) findViewById(R.id.email);
		viewPassword = (TextView) findViewById(R.id.password);
		viewStaySignIn = (CheckBox) findViewById(R.id.staysignedin);
		signin = (Button) findViewById(R.id.signin);
		signin.setOnClickListener(this);

		mydlink = new Mydlink(this);
		if (mydlink.isStaySignIn()) {

		} else {
		}
		viewEmail.setText(mydlink.getEmail());
		viewPassword.setText(mydlink.getPassword());
		viewStaySignIn.setChecked(mydlink.isStaySignIn());

		init();
	}

	private void init() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					mHandle.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	@Override
	protected void onResume() {
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onResume();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.signin:
			email = viewEmail.getText().toString();
			password = viewPassword.getText().toString();
			staySignIn = viewStaySignIn.isChecked();
			mydlink.reload(email, password, staySignIn);
			try {
				mydlink.signin();
				startActivity(new Intent(this, Cameras.class));
				finish();
			} catch (MydlinkException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean handleMessage(Message arg0) {
		signinfield.setVisibility(View.VISIBLE);
		Animation a = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.push_up_in);
		signinfield.startAnimation(a);
		return false;
	}
}
