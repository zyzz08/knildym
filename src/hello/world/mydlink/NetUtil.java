package hello.world.mydlink;

import hello.world.mydlink.model.WebCamara;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class NetUtil {

	public static String getRedirect(String email, String password)
			throws IOException {
		String data = URLEncoder.encode("email", "UTF-8") + "="
				+ URLEncoder.encode(email, "UTF-8") + "&"
				+ URLEncoder.encode("password", "UTF-8") + "="
				+ URLEncoder.encode(password);
		URL url = new URL(Constant.HOSTSITE + Constant.URLPATH_SIGNIN);
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
		String redirect = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
		redirect = redirect.substring(redirect.indexOf(',') + 1,
				redirect.length());
		return redirect.trim();
	}

	public static List<WebCamara> getDeviceList(String redirect, String email,
			String password) throws IOException {
		String data = URLEncoder.encode("email", "UTF-8") + "="
				+ URLEncoder.encode(email, "UTF-8") + "&"
				+ URLEncoder.encode("password", "UTF-8") + "="
				+ URLEncoder.encode(password);
		URL url = new URL(redirect + Constant.URLPATH_SIGNIN);
		System.out.println("...."
				+ (redirect + Constant.URLPATH_SIGNIN)
						.equals("https://twqa.mydlink.com/m/index.php?signin"));
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
		String[] res = result.split("\n");

		int count = Integer.parseInt(res[0].substring(res[0].indexOf(',') + 1,
				res[0].length()));
		System.out.println(count);

		ArrayList<WebCamara> list = new ArrayList<WebCamara>();
		for (int i = 0; i < count; i++) {
			System.out.println(res[i + 1]);
			WebCamara camara = new WebCamara(res[i + 1]);
			list.add(camara);
		}

		// 设置屏幕显示
		return list;
	}

	// MD5加密，32位
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		byte[] md5Bytes = md5.digest(str.getBytes());

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();
	}

	public static String MD5_16(String str) {
		return MD5(str).substring(8, 24);
	}

	public static byte[] MD5_(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		byte[] md5Bytes = md5.digest(str.getBytes());
		return md5Bytes;
	}

	public static String getSIGNATURE_(byte[] md5Bytes) {
		int[] result = new int[4];
		result[0] = md5Bytes[0] ^ md5Bytes[4] ^ md5Bytes[8] ^ md5Bytes[12];
		result[1] = md5Bytes[1] ^ md5Bytes[5] ^ md5Bytes[9] ^ md5Bytes[13];
		result[2] = md5Bytes[2] ^ md5Bytes[6] ^ md5Bytes[10] ^ md5Bytes[14];
		result[3] = md5Bytes[3] ^ md5Bytes[7] ^ md5Bytes[11] ^ md5Bytes[15];
		
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			int value = (result[i]) & 0xff;
			if (value < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(value));
		}
		return hexValue.toString().toUpperCase();
	}

	public static String getSIGNATURE(String val) {
		byte[] md5Bytes = val.getBytes();

		int[] result = new int[4];
		// result[0]=val.charAt(0)^val.charAt(4)^val.charAt(8)^val.charAt(12);
		// result[1]=val.charAt(1)^val.charAt(5)^val.charAt(9)^val.charAt(13);
		// result[2]=val.charAt(2)^val.charAt(6)^val.charAt(10)^val.charAt(14);
		// result[3]=val.charAt(3)^val.charAt(7)^val.charAt(11)^val.charAt(15);
		result[0] = md5Bytes[0] ^ md5Bytes[4] ^ md5Bytes[8] ^ md5Bytes[12];
		result[1] = md5Bytes[1] ^ md5Bytes[5] ^ md5Bytes[9] ^ md5Bytes[13];
		result[2] = md5Bytes[2] ^ md5Bytes[6] ^ md5Bytes[10] ^ md5Bytes[14];
		result[3] = md5Bytes[3] ^ md5Bytes[7] ^ md5Bytes[11] ^ md5Bytes[15];
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			int value = (result[i]) & 0xff;
			if (value < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(value));
		}
		return hexValue.toString().toUpperCase();
	}
}
