package com.shopizer.restpopulators.references;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.references.Country;
import com.shopizer.restentity.references.RESTCountry;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class CountryPopulator implements DataPopulator<RESTCountry, Country> {

	@Override
	public Country populateModel(RESTCountry source, Locale locale) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RESTCountry populateWeb(Country source, Locale locale) throws Exception {
		RESTCountry target = new RESTCountry();
		target.setCode(source.getCode());
		target.setId(source.getId());
		
		String lang = locale.getLanguage();
		for(Description d : source.getDescriptions()) {
			if(d.getLang().equals(lang)) {
				target.setLang(lang);
				target.setName(d.getName());
			}
		}
		
		
		//TODO zones
		return target;
	}

}
