package hello.world.mydlink.model;

import hello.world.mydlink.util.Base64;
import hello.world.mydlink.util.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Camara {
	private String mydlinkNo;
	private String macAdress;
	private String lastAccessTime;
	private String modelName;
	private String deviceNickName;
	private String authenticationKeyForSignaling;
	private String localIp;
	private String UPnPIp;
	private String UPnPPort;
	private String devicePassword;
	private String signalAddress;
	private String isOnline;// 1 for online, o for offline

	public String getMydlinkNo() {
		return mydlinkNo;
	}

	public String getMacAdress() {
		return macAdress;
	}

	public String getLastAccessTime() {
		return lastAccessTime;
	}

	public String getModelName() {
		return modelName;
	}

	public String getDeviceNickName() {
		return deviceNickName;
	}

	public String getAuthenticationKeyForSignaling() {
		return authenticationKeyForSignaling;
	}

	public String getLocalIp() {
		return localIp;
	}

	public String getUPnPIp() {
		return UPnPIp;
	}

	public String getUPnPPort() {
		return UPnPPort;
	}

	public String getDevicePassword() {
		return devicePassword;
	}

	public String getSignalAddress() {
		return signalAddress;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public Camara(String mydlinkNo, String macAdress, String lastAccessTime,
			String modelName, String deviceNickName,
			String authenticationKeyForSignaling, String localIp,
			String uPnPIp, String uPnPPort, String devicePassword,
			String signalAddress, String isOnline) {
		super();
		this.mydlinkNo = mydlinkNo;
		this.macAdress = macAdress;
		this.lastAccessTime = lastAccessTime;
		this.modelName = modelName;
		this.deviceNickName = deviceNickName;
		this.authenticationKeyForSignaling = authenticationKeyForSignaling;
		this.localIp = localIp;
		UPnPIp = uPnPIp;
		UPnPPort = uPnPPort;
		this.devicePassword = devicePassword;
		this.signalAddress = signalAddress;
		this.isOnline = isOnline;
	}

	public Camara(String RawDataOfDevice) {
		super();
		String[] res = RawDataOfDevice.split(",");
		mydlinkNo = res[0];
		macAdress = res[1];
		lastAccessTime = res[2];
		modelName = res[3];
		deviceNickName = res[4];
		authenticationKeyForSignaling = res[5];
		localIp = res[6];
		UPnPIp = res[7];
		UPnPPort = res[8];
		devicePassword = res[9];
		signalAddress = res[10];
		isOnline = res[11];// 1 for online, o for offline
	}

	public Bitmap getLiveImage() throws IOException {
		Bitmap info = null;
		// 定义获取文件内容的URL
		// URL url = new URL("http://baidu.com");
		URL url = new URL("http://" + UPnPIp + ":" + UPnPPort
				+ Constant.CGIPATH_IMAGE);
		// 打开URL链接
		URLConnection ucon = url.openConnection();
		String userPassword = "admin" + ":" + devicePassword;
		String encoding = Base64.encode(userPassword.getBytes());
		ucon.setRequestProperty("Authorization", "Basic " + encoding);
		// 使用InputStream，从URLConnection读取数据
		InputStream is = ucon.getInputStream();
		info = BitmapFactory.decodeStream(is);
		return info;
	}
}
