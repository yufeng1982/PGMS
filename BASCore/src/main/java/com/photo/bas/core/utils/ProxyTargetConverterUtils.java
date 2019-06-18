/**
 * 
 */
package com.photo.bas.core.utils;

import org.hibernate.proxy.HibernateProxy;

/**
 * @author FengYu
 *
 */
public class ProxyTargetConverterUtils {

	private static ProxyTargetConverterUtils proxyTargetConverter = null;
	
	private ProxyTargetConverterUtils(){}
	
	public static synchronized ProxyTargetConverterUtils instance() {
		if (proxyTargetConverter == null) {
			proxyTargetConverter = new ProxyTargetConverterUtils();
		}
		return proxyTargetConverter;
	}
	
	public static Object getProxyImplementationObject(Object proxyObject) {
		if(proxyObject == null) return null;
		if(proxyObject instanceof HibernateProxy) {
			return ((HibernateProxy) proxyObject).getHibernateLazyInitializer().getImplementation();
		} else {
			return proxyObject;
		}
	}
}
