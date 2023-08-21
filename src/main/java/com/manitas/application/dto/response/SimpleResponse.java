package com.manitas.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SimpleResponse {

	private Boolean success;
	private String error;
	private Integer code;

	public SimpleResponse() {
		this.success = false;
		this.error = "";
		this.code = null;
	}

	public SimpleResponse(String error) {
		this.success = false;
		this.error = error;
		this.code = null;
	}

	public SimpleResponse(Boolean success, String error) {
		this.success = success;
		this.error = error;
		this.code = null;
	}

	public SimpleResponse(Boolean success, Integer code, String error) {
		this.success = success;
		this.code = code;
		this.error = error;
	}
}
