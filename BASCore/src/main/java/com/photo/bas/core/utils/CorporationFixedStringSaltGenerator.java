/**
 * 
 */
package com.photo.bas.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.salt.SaltGenerator;

import com.photo.bas.core.model.security.Corporation;

/**
 * @author FengYu
 *
 */
public class CorporationFixedStringSaltGenerator implements SaltGenerator {

    private static final String DEFAULT_CHARSET = "UTF-8";
    
    private static Map<String, byte[]> corporationSaltSources = new HashMap<>();

    public CorporationFixedStringSaltGenerator() {
        super();
    }
    
    /**
     * Return salt with the current corporation
     * 
     * @param lengthBytes length in bytes.
     * @return the generated salt. 
     */
    public byte[] generateSalt(final int lengthBytes) {

    	Corporation currentCorporation = ThreadLocalUtils.getCurrentCorporation();
    	if(currentCorporation == null) {
    		throw new EncryptionInitializationException("Salt has not been set");
    	}
    	byte[] generatedSalt = corporationSaltSources.get(currentCorporation.getId());
    	if(generatedSalt != null) return generatedSalt;
    	
        generatedSalt = new byte[lengthBytes];
    	String saltSource = currentCorporation.getSaltSource();
        if (Strings.isEmpty(saltSource)) {
            throw new EncryptionInitializationException("Salt has not been set");
        }
        
        byte[] saltSourceByte = null;
        try {
        	saltSourceByte = saltSource.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new EncryptionInitializationException("Invalid charset specified: " + DEFAULT_CHARSET);
        }
        if (saltSourceByte.length < lengthBytes) {
            throw new EncryptionInitializationException("Requested salt larger than set");
        }
        System.arraycopy(saltSourceByte, 0, generatedSalt, 0, lengthBytes);
        
        corporationSaltSources.put(currentCorporation.getId(), generatedSalt);
        
        return generatedSalt;
    }

    public boolean includePlainSaltInEncryptionResults() {
        return false;
    }

    

}
