package com.manitas.application.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataResponse<T> extends SimpleResponse {

	private T data;

	public DataResponse() {
		this.setSuccess(false);
		this.setError("");
	}

	public DataResponse(String error) {
		this.setSuccess(false);
		this.setError(error);
	}

	public DataResponse(Boolean success, String error, T data) {
		this.setSuccess(success);
		this.setError(error);
		this.data = data;
	}

	public DataResponse(Boolean success, String error, Integer code, T data) {
		this.setSuccess(success);
		this.setError(error);
		this.setCode(code);
		this.setData(data);
	}
}
