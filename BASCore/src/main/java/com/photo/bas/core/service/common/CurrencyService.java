/**
 * 
 */
package com.photo.bas.core.service.common;

import java.util.ArrayList;
import java.util.List;

import org.jscience.economics.money.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
@Component
public class CurrencyService {

	@Autowired
	@Qualifier("coreAppSetting") 
	private MessageSource coreAppSetting;
	
	private Currency domesticCurrency;
	private List<Currency> availableCurrencies;
	
	public List<Currency> getAvailableCurrencies() {
		if(availableCurrencies == null) {
			availableCurrencies = new ArrayList<Currency>();
			String availables = coreAppSetting.getMessage("erp.app.currency.availables", new Object[0], ThreadLocalUtils.getCurrentLocale());
			String[] split = availables.split(",");
			for (int i = 0; i < split.length; i ++) {
				availableCurrencies.add(new Currency(split[i]));
			}
		}
		return availableCurrencies;
	}

	public Currency getDomesticCurrency() {
		if(domesticCurrency == null) {
			String domesticCurrencyCode = coreAppSetting.getMessage("erp.app.currency.domestic", new Object[0], ThreadLocalUtils.getCurrentLocale());
			domesticCurrency = new Currency(domesticCurrencyCode);
		}
		return domesticCurrency;
	}

}
