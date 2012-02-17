package hello.world.mydlink;

import hello.world.mydlink.model.WebCamara;
import hello.world.mydlink.util.Base64;
import hello.world.mydlink.util.Constant;
import hello.world.mydlink.util.NetUtil;

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
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DlinkTestActivity extends Activity implements OnClickListener {

	private TextView result;
	private ImageView img;
	private String email = "madduxtsai@gmail.com";;
	private String password = "software";
	private String redirect = "https://twqa.mydlink.com";
	private List<WebCamara> devicelist;
	private WebCamara device;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		findViewById(R.id.bt0).setOnClickListener(this);
		findViewById(R.id.bt1).setOnClickListener(this);
		findViewById(R.id.bt2).setOnClickListener(this);
		findViewById(R.id.bt3).setOnClickListener(this);
		findViewById(R.id.bt4).setOnClickListener(this);
		result = (TextView) findViewById(R.id.result);
		img = (ImageView) findViewById(R.id.img);
		// HttpConn();
		// httpsPost();
		// device list

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

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bt0:
			try {
				redirect = NetUtil.getRedirect(email, password);
				result.setText(redirect);
			} catch (IOException e) {
				result.setText(e.toString());
			}
			break;
		case R.id.bt1:
			try {
				devicelist = NetUtil.getDeviceList(redirect, email, password);
				device = devicelist.get(0);
				result.setText(device.getUPnPIp() + ":" + device.getUPnPPort()
						+ "," + device.getDevicePassword());
			} catch (IOException e) {
				result.setText(e.toString());
			}
			break;
		case R.id.bt2:
			
			connectDevice(device);
			break;
		case R.id.bt3:
			connectDevice_img(device);
			// connectSignature(device);

			break;
		case R.id.bt4:
			result.setText(NetUtil.MD5Str("software"));
			String temp = Base64.encode("software".getBytes());
			System.out.println(temp);
			System.out.println(new String(Base64.decode(temp)));

		default:
			break;
		}

	}

	private String connectSignature(WebCamara dev) {
		String info;
		try {

			String req = "no=" + dev.getMydlinkNo();

			String temp = req + dev.getAuthenticationKeyForSignaling();
			// String signature = NetUtil.getSIGNATURE_(NetUtil.MD5Str16(temp
			// ).getBytes());
			String signature = NetUtil.getSignature(NetUtil.MD5(temp));
			// 定义获取文件内容的URL
			URL url = new URL("http://" + dev.getSignalAddress()
					+ Constant.SIGNATURE + "?"
					+ URLEncoder.encode("no", "UTF-8") + "="
					+ URLEncoder.encode(dev.getMydlinkNo(), "UTF-8") + "&"
					+ URLEncoder.encode("s", "UTF-8") + "="
					+ URLEncoder.encode(signature));
			System.out.println(url);

			// 打开URL链接
			URLConnection connection = url.openConnection();

			// 使用InputStream，从URLConnection读取数据
			InputStream is = connection.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			// 用ByteArrayBuffer缓存
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			// 将缓存的内容转化为String,用UTF-8编码
			info = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
		} catch (Exception e) {
			info = e.getMessage();
		}
		result.setText(info);
		return info;
	}

	private String connectDevice(WebCamara dev) {
		String info;
		try {
			// 定义获取文件内容的URL
			// URL url = new URL("http://baidu.com");
			URL url = new URL("http://" + dev.getUPnPIp() + ":"
					+ dev.getUPnPPort() + Constant.CGIPATH_SYSTEM);
			// 打开URL链接
			URLConnection ucon = url.openConnection();

			String userPassword = "admin" + ":" + dev.getDevicePassword();
			System.out.println(userPassword);
			String encoding = Base64.encode(userPassword.getBytes());
			ucon.setRequestProperty("Authorization", "Basic " + encoding);
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
			info = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
		} catch (Exception e) {
			info = e.getMessage();
		}
		result.setText(info);
		return info;
	}

	private Bitmap connectDevice_img(WebCamara dev) {
		Bitmap info = null;
		try {
			// 定义获取文件内容的URL
			// URL url = new URL("http://baidu.com");
			URL url = new URL("http://" + dev.getUPnPIp() + ":"
					+ dev.getUPnPPort() + Constant.CGIPATH_IMAGE);
			// 打开URL链接
			URLConnection ucon = url.openConnection();

			String userPassword = "admin" + ":" + dev.getDevicePassword();
			System.out.println(userPassword);
			String encoding = Base64.encode(userPassword.getBytes());
			ucon.setRequestProperty("Authorization", "Basic " + encoding);
			// 使用InputStream，从URLConnection读取数据
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			info = BitmapFactory.decodeStream(is);
			img.setImageBitmap(info);
		} catch (Exception e) {
			e.getMessage();
		}
		return info;
	}

}