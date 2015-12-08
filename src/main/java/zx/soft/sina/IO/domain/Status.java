package zx.soft.sina.IO.domain;

public class Status {

	private String errorCode;
	private String errorMessage;

	public Status() {

	}

	public Status(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "Status [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

}
