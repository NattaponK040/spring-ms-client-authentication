package com.authentication.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Order(1)
@Component
public class MDCFilter implements Filter {

	private final Logger LOGGER = LoggerFactory.getLogger(MDCFilter.class);
	private final String X_REQUEST_ID = "X-Request-ID";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			String ipAddress = req.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			addXRequestId(req);
			String lmsg = "{";
			lmsg = "StatusCode: " + res.getStatus() + ", ";
			lmsg += "Method: " + req.getMethod() + ", ";
			lmsg += "URL: " + req.getRequestURI() + ", ";
			lmsg += "IPAddress" + ipAddress + "}";

			LOGGER.info(lmsg);

			res.setHeader(X_REQUEST_ID, MDC.get(X_REQUEST_ID));
			chain.doFilter(request, response);
		} finally {
//			LOGGER.info("statusCode {}, path: {}, method: {}, query {}", res.getStatus(), req.getRequestURI(),
//					req.getMethod(), req.getQueryString());
			MDC.clear();
		}
	}

	private void addXRequestId(HttpServletRequest request) {
		String xRequestId = request.getHeader(X_REQUEST_ID);
		if (xRequestId == null) {
			MDC.put(X_REQUEST_ID, UUID.randomUUID().toString());
		} else {
			MDC.put(X_REQUEST_ID, xRequestId);
		}
	}

//	private String getResponsePayload(HttpServletResponse response) {
//		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
//				ContentCachingResponseWrapper.class);
//		if (wrapper != null) {
//
//			byte[] buf = wrapper.getContentAsByteArray();
//			if (buf.length > 0) {
//				int length = Math.min(buf.length, 5120);
//				try {
//					return new String(buf, 0, length, wrapper.getCharacterEncoding());
//				} catch (UnsupportedEncodingException ex) {
//					// NOOP
//				}
//			}
//		}
//		return "[unknown]";
//	}
//
//	private void updateResponse(HttpServletResponse response) throws IOException {
//		ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response,
//				ContentCachingResponseWrapper.class);
//		responseWrapper.copyBodyToResponse();
//	}

}