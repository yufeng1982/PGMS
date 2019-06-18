/**
 * 
 */
package com.photo.pgm.core.utils;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.photo.bas.core.utils.ResourceUtils;


/**
 * @author FengYu
 *
 */
public class RemoteAccessUtils {

	private static final Logger logger = LoggerFactory.getLogger(RemoteAccessUtils.class);
	
	private static final String URI = "http.remote.uri";
	
	public static String getReponseContents(Map<String, String> paramsMap) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String remoteURI = "";
		try {
        	if (paramsMap.containsKey("URL")) {
        		remoteURI = (String) paramsMap.get("URL");
        	} else {
        		remoteURI = ResourceUtils.getAppSetting(URI);
        	}
        	HttpPost httpPost = new HttpPost(remoteURI);
        	List <NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (paramsMap.size() != 0) {
            	Set<String> keySet = paramsMap.keySet();
            	for (String key : keySet) {
            		if (key.equals("URL")) continue;
            		nvps.add(new BasicNameValuePair(key, (String) paramsMap.get(key)));
            	}
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            logger.info("****************************************");
            logger.info("executing request to " + httpPost);
            
            CloseableHttpResponse rsp = httpclient.execute(httpPost);
            try {
            	HttpEntity entity = rsp.getEntity();
                
                logger.info("****************************************");
                logger.info(rsp.getStatusLine().toString());
                logger.info("****************************************");

                if (entity != null) {
                	String contents = EntityUtils.toString(entity);
                	logger.info(contents);
                	logger.info("****************************************");
                	return contents;
                }
			} finally {
				rsp.close();
			}
        } catch(Exception ce){
        	throw new ConnectException();
        } finally {
            httpclient.close();
        }
        return "[{}]";
	}
	
}
