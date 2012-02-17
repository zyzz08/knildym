package hello.world.mydlink.util;

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
		return list;
	}

	public static byte[] MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return md5.digest(str.getBytes());
	}

	public static String HexString(byte[] bytes) {
		if (null == bytes)
			return null;
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int val = ((int) bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();
	}

	// MD5加密，32位
	public static String MD5Str(String str) {
		return HexString(MD5(str));
	}

	// MD5加密，16位
	public static String MD5Str16(String str) {
		if (null == str)
			return null;
		return MD5Str(str).substring(8, 24);
	}

	public static String getSignature(byte[] md5Bytes) {
		byte[] result = new byte[4];
		result[0] = (byte) (md5Bytes[0] ^ md5Bytes[4] ^ md5Bytes[8] ^ md5Bytes[12]);
		result[1] = (byte) (md5Bytes[1] ^ md5Bytes[5] ^ md5Bytes[9] ^ md5Bytes[13]);
		result[2] = (byte) (md5Bytes[2] ^ md5Bytes[6] ^ md5Bytes[10] ^ md5Bytes[14]);
		result[3] = (byte) (md5Bytes[3] ^ md5Bytes[7] ^ md5Bytes[11] ^ md5Bytes[15]);
		return HexString(result);
	}

}
