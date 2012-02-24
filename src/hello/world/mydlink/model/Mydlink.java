package hello.world.mydlink.model;

import hello.world.mydlink.exception.MydlinkException;
import hello.world.mydlink.util.Constant;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Mydlink {
	private SharedPreferences sp;

	private String email;
	private String password;
	private boolean staySignIn;

	private String redirect;
	public List<Camara> camaras;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStaySignIn() {
		return staySignIn;
	}

	public void setStaySignIn(boolean staySignIn) {
		this.staySignIn = staySignIn;
	}

	public Mydlink(Context mContext) {
		super();
		sp = mContext.getSharedPreferences(Constant.SHAREDPREFERENCES,
				Context.MODE_PRIVATE);
		this.email = sp.getString("email", null);
		this.password = sp.getString("password", null);
		this.staySignIn = sp.getBoolean("staySignIn", false);
	}

	public void reload(String email, String password, boolean staySignIn) {
		if (!email.equals(this.email)) {
			Editor mEditor = sp.edit();
			mEditor.putString("redirect", null);
			mEditor.commit();
		}
		this.email = email;
		this.password = password;
		this.staySignIn = staySignIn;
		Editor mEditor = sp.edit();
		mEditor.putString("email", email);
		mEditor.putString("password", password);
		mEditor.putBoolean("staySignIn", staySignIn);
		mEditor.commit();
	}

	public void signin() throws MydlinkException, IOException {
		if (getRedirect() == null)
			setRedirect(Constant.HOSTSITE);
		System.out.println(redirect);
		String rawString = signin(redirect);
		setCameras(rawString);
	}

	public void signout() {

	}

	public List<Camara> getCameras() {

		return camaras;
	}

	private void setCameras(String rawString) {

	}

	private void setRedirect(String redirect) {
		Editor mEditor = sp.edit();
		mEditor.putString("redirect", redirect);
		mEditor.commit();
		this.redirect = redirect;
	}

	private String getRedirect() throws IOException {
		redirect = sp.getString("redirect", null);
		return redirect;
	}

	private String signin(String site) throws IOException {
		String data = URLEncoder.encode("email", "UTF-8") + "="
				+ URLEncoder.encode(email, "UTF-8") + "&"
				+ URLEncoder.encode("password", "UTF-8") + "="
				+ URLEncoder.encode(password);
		URL url = new URL(site + Constant.URLPATH_SIGNIN);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setAllowUserInteraction(true);
		OutputStreamWriter writer = new OutputStreamWriter(
				connection.getOutputStream());
		writer.write(data);
		writer.flush();
		writer.close();
		InputStream is = connection.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		// 用ByteArrayBuffer缓存
		ByteArrayBuffer baf = new ByteArrayBuffer(50);
		int current = 0;
		while ((current = bis.read()) != -1) {
			baf.append((byte) current);
		}
		// 将缓存的内容转化为String,用UTF-8编码
		String result = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
		if (result.startsWith("Invalid")) {
			System.out.println(result);
		} else if (result.startsWith("redirect")) {
			System.out.println(result);
			result = result.replace("redirect,", "").trim();
			setRedirect(result);
			signin(result);
		} else if (result.startsWith("success")) {
			System.out.println(result);
		}
		return result;
	}
}
