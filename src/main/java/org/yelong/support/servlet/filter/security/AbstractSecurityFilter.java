/**
 * 
 */
package org.yelong.support.servlet.filter.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.yelong.support.servlet.wrapper.HttpServletResponseReuseWrapper;

/**
 * 抽象安全过滤器
 * 实现对请求参数、消息体、响应消息体进行加解密及数据完整性验证功能。
 * @author 彭飞
 * @date 2019年9月17日下午12:29:07
 * @version 1.2
 */
public abstract class AbstractSecurityFilter implements Filter{


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String contentType = request.getContentType();
		if( StringUtils.isNotBlank(contentType) && contentType.contains("multipart/form-data")) {
			chain.doFilter(request, response);
			return;
		}
		SecurityHttpServletRequestWrapper requestWrapper = new SecurityHttpServletRequestWrapper((HttpServletRequest)request);
		try {
			if(isParamDecrypt(requestWrapper)) {
				requestWrapper.setAfterDecryptParameterMap(decryptParam(requestWrapper,requestWrapper.getSourceParameterMap()));
			}
			if(isBodyDecrypt(requestWrapper)) {
				byte[] sourceBody = requestWrapper.getSourceBody();
				if( null != sourceBody) {
					requestWrapper.setAfterDecryptBody(decryptBody(requestWrapper,requestWrapper.getSourceBody()));
				}
			}
			if(isIntegrityValidation(requestWrapper)) {
				try {
					if(!integrityValidation(requestWrapper)) {
						throw new IntegrityValidationException();
					}
				} catch (IntegrityValidationException e) {
					integrityValidationExceptionProcessor(e, (HttpServletResponse)response);
					return;
				}
			}
		} catch (SecurityException e) {
			decryptExceptionProcessor(e,(HttpServletResponse)response);
			return;
		}
		HttpServletResponseReuseWrapper responseWrapper = new HttpServletResponseReuseWrapper((HttpServletResponse) response);
		chain.doFilter(requestWrapper, responseWrapper);
		//是否需要进行响应加密
		if( isResponseEncrypt((HttpServletRequest) request) ) {
			//结果进行加密
			byte [] content = responseEncrypt(responseWrapper.getContent());
			//清除原数据
			responseWrapper.reset();
			if( responseWrapper.isWriter() ) {
				responseWrapper.getWriter().write(new String(content));
				responseWrapper.getWriter().flush();
			} else {
				//写入加密后 数据
				responseWrapper.getOutputStream().write(content);
				responseWrapper.getOutputStream().flush();
			}
		}
		/* 
		/* 
		 * 调用真正的getOutputStream写入数据
		 * 注意，此处即使不加密也要调用，否则没有为response写入数据。会导致无响应
		 */
		responseWrapper.responseContent();
	}

	/**
	 * 是否进行参数解密
	 * @author 彭飞
	 * @date 2019年9月17日下午12:34:48
	 * @version 1.2
	 * @param request 
	 * @return <tt>true</tt> 进行解密
	 */
	public abstract boolean isParamDecrypt(HttpServletRequest request);

	/**
	 * 是否进行请求消息体解密
	 * @author 彭飞
	 * @date 2019年9月17日下午12:35:14
	 * @version 1.2
	 * @param request
	 * @return <tt>true</tt> 进行解密
	 */
	public abstract boolean isBodyDecrypt(HttpServletRequest request);

	/**
	 * 是否进行完整性验证
	 * @author 彭飞
	 * @date 2019年9月17日下午12:35:49
	 * @version 1.2
	 * @param request
	 * @return <tt>true</tt> 进行验证
	 */
	public abstract boolean isIntegrityValidation(HttpServletRequest request);

	/**
	 * 解密参数
	 * @author 彭飞
	 * @date 2019年9月17日下午3:24:15
	 * @version 1.2
	 * @param parameterMap 原参数
	 * @return 解密后的参数
	 */
	public abstract Map<String,String[]> decryptParam(HttpServletRequest request,Map<String,String[]> parameterMap) throws SecurityException;

	/**
	 * 解密消息体
	 * @author 彭飞
	 * @date 2019年9月17日下午3:24:32
	 * @version 1.2
	 * @param body 源消息体
	 * @return 解密后的消息体
	 */
	public abstract byte [] decryptBody(HttpServletRequest request,byte [] body) throws SecurityException;

	/**
	 * 完整性效验<br/>
	 * 完整性验证在解密之后执行<br/>
	 * 如果完整性效验失败则进入@see #integrityValidationExceptionProcessor()进行后置处理。之后将不进入以下过滤器
	 * @author 彭飞
	 * @date 2019年9月17日下午3:24:53
	 * @version 1.2
	 * @param request 
	 * @return <tt>true</tt> 完整性效验通过
	 * @throws IntegrityValidationException 完整性效验异常
	 */
	public abstract boolean integrityValidation(SecurityHttpServletRequestWrapper request) throws IntegrityValidationException;

	/**
	 * 完整性效验失败后处理
	 * @author 彭飞
	 * @date 2019年9月17日下午3:28:17
	 * @version 1.2
	 * @param e 异常信息
	 * @param response 
	 */
	public abstract void integrityValidationExceptionProcessor(IntegrityValidationException e,HttpServletResponse response) throws IOException ;

	/**
	 * 解密异常处理
	 * @author 彭飞
	 * @date 2019年9月17日下午4:10:15
	 * @version 1.2
	 * @param e
	 * @param response
	 */
	public abstract void decryptExceptionProcessor(SecurityException e , HttpServletResponse response) throws IOException ;



	/**
	 * 是否需要进行响应加密
	 * @author 彭飞
	 * @date 2019年9月23日上午9:40:27
	 * @version 1.2
	 * @param request
	 * @return <tt>true</tt>需要进行响应加密
	 */
	public abstract boolean isResponseEncrypt(HttpServletRequest request);




	/**
	 * 加密响应结果
	 * @author 彭飞
	 * @date 2019年9月23日上午9:42:26
	 * @version 1.2
	 * @param content 响应的结果上下文
	 * @return 加密后的响应结果
	 * @throws IOException
	 */
	public abstract byte [] responseEncrypt(byte [] content) throws IOException;


	@Override
	public void destroy() {
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	
	
}
