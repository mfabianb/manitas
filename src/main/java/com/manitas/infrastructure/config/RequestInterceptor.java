package com.manitas.infrastructure.config;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class RequestInterceptor extends HandlerInterceptorAdapter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@PostConstruct
	public void init() {
		log.debug("Init component {}", this.getClass().getName());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long inicio = (long) request.getAttribute("inicio");
		logger.debug(String.format("Finaliza petici\u00f3n, URL: %s, tiempo total: %s ms",
				request.getRequestURL().toString(), System.currentTimeMillis() - inicio));
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("inicio", System.currentTimeMillis());
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		logger.debug(String.format("Iniciando petici\u00f3n, URL: %s", request.getRequestURL().toString()));
		logger.debug(String.format("Invocacion desde URL: %s", ipAddress));
		return super.preHandle(request, response, handler);
	}

}
