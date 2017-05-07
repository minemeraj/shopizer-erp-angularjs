package com.shopizer.restpopulators.references;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.references.Zone;
import com.shopizer.restentity.references.RESTZone;
import com.shopizer.restpopulators.DataPopulator;

@Component
public class ZonePopulator implements DataPopulator<RESTZone, Zone> {



	@Override
	public Zone populateModel(RESTZone source, Locale locale) throws Exception {
		
		
		return null;
	}

	@Override
	public RESTZone populateWeb(Zone source, Locale locale) throws Exception {
		
		RESTZone target = new RESTZone();
		target.setCode(source.getCode());
		target.setId(source.getId());
		
		String lang = locale.getLanguage();
		for(Description d : source.getDescriptions()) {
			if(d.getLang().equals(lang)) {
				target.setLang(lang);
				target.setName(d.getName());
			}
		}

		return target;
		
		
	}

}
