package hello.world.mydlink.model;


public class WebCamara {
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

	public WebCamara(String mydlinkNo, String macAdress, String lastAccessTime,
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

	public WebCamara(String RawDataOfDevice) {
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

}
