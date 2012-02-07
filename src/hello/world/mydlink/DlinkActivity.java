package hello.world.mydlink;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DlinkActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// HttpConn();
		// httpsPost();
		// device list
		String email = "madduxtsai@gmail.com";
		String password = "software";
		try {
			String redirect = NetUtil.getRedirect(email, password);
			NetUtil.getDeviceList(redirect, email, password);
			TextView tv;
			tv = new TextView(this);
			tv.setText(redirect);
			this.setContentView(tv);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	private void getDeviceList() throws IOException {
		StringBuffer buffer = new StringBuffer();
		String data = URLEncoder.encode("email", "UTF-8") + "="
				+ URLEncoder.encode("madduxtsai@gmail.com", "UTF-8") + "&"
				+ URLEncoder.encode("password", "UTF-8") + "="
				+ URLEncoder.encode("software");
		URL url = new URL("https://www.mydlink.com/m/index.php?signin");
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
		String myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
		// 设置屏幕显示
		TextView tv = new TextView(this);
		tv.setText(myString);
		this.setContentView(tv);
	}

	public String httpsPost() {
		StringBuffer buffer = new StringBuffer();
		try {
			// String data = URLEncoder.encode("Email", "UTF-8") + "="
			// + URLEncoder.encode("xxxx@gmail.com", "UTF-8") + "&"
			// + URLEncoder.encode("Passwd", "UTF-8") + "="
			// + URLEncoder.encode("xxxx") + "&"
			// + URLEncoder.encode("service", "UTF-8") + "="
			// + URLEncoder.encode("reader", "UTF-8") + "&"
			// + URLEncoder.encode("source", "UTF-8") + "="
			// + URLEncoder.encode("RssReader", "UTF-8");
			// URL url = new URL("https://www.google.com/accounts/ClientLogin");
			URL url = new URL("https://www.mydlink.com/m/index.php?version");

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setAllowUserInteraction(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			// writer.write(data);
			writer.flush();
			writer.close();
			InputStream stream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			String response = reader.readLine();
			while (response != null) {
				buffer.append(response + "\n");
				response = reader.readLine();
			}
			stream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (buffer != null) {
			// 设置屏幕显示
			TextView tv = new TextView(this);
			tv.setText(buffer);
			this.setContentView(tv);
			return buffer.toString();
		} else {
			return null;
		}

	}

	private void HttpConn() {
		String myString;
		try {
			// 定义获取文件内容的URL
			// URL url = new URL("http://baidu.com");
			URL url = new URL("https://www.mydlink.com/m/index.php?version");
			// 打开URL链接
			URLConnection ucon = url.openConnection();
			// 使用InputStream，从URLConnection读取数据
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			// 用ByteArrayBuffer缓存
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			// 将缓存的内容转化为String,用UTF-8编码
			myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
		} catch (Exception e) {
			myString = e.getMessage();
		}
		// 设置屏幕显示
		TextView tv = new TextView(this);
		tv.setText(myString);
		this.setContentView(tv);
	}

}