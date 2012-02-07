package hello.world.mydlink.exception;

public class MydlinkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5128552324397841945L;
	private int errCode;

	public MydlinkException(int errorCode) {
		errCode = errorCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public int getErrCode() {
		return errCode;
	}

	@Override
	public String toString() {
		return "errCode: " + errCode + super.toString();
	}
}
