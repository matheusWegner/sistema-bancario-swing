package br.com.tads.sistemaBancario.models;

public class Result {
	private Object data;
	private String message;
	private boolean status;
	
	public Result() {
		
	}

	public Result(boolean status , String message) {
		this.status = status;
		this.message = message;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
