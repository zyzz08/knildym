package hello.world.mydlink;

import hello.world.mydlink.model.WebCamara;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class NetUtil {
	
	public static String getRedirect(String email,String password) throws IOException{
		String data = URLEncoder.encode("email", "UTF-8") + "="
				+ URLEncoder.encode(email, "UTF-8") + "&"
				+ URLEncoder.encode("password", "UTF-8") + "="
				+ URLEncoder.encode(password);
		URL url = new URL(Constant.HOSTSITE+Constant.SIGNIN_URLPATH);
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
		redirect=redirect.substring(redirect.indexOf(',')+1, redirect.length());
		return redirect.trim();
	}
	
	public static List<WebCamara> getDeviceList(String redirect,String email,String password) throws IOException {
		String data = URLEncoder.encode("email", "UTF-8") + "="
				+ URLEncoder.encode(email, "UTF-8") + "&"
				+ URLEncoder.encode("password", "UTF-8") + "="
				+ URLEncoder.encode(password);
		URL url = new URL(redirect+Constant.SIGNIN_URLPATH);
		System.out.println("...."+(redirect+Constant.SIGNIN_URLPATH).equals("https://twqa.mydlink.com/m/index.php?signin"));
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
		String[] res=result.split("\n");
		
		int count=Integer.parseInt(res[0].substring(res[0].indexOf(',')+1, res[0].length()));
		System.out.println(count);
		
		ArrayList<WebCamara> list=new ArrayList<WebCamara>();
		for(int i=0;i<count;i++){
			System.out.println(res[i+1]);
			WebCamara camara=new WebCamara(res[i+1]);
			list.add(camara);
		}
		
		// 设置屏幕显示
		return list;
	}
}
