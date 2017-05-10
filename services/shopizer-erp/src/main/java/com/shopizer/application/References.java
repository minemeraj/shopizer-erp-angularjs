package com.shopizer.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.shopizer.business.entity.common.Currency;
import com.shopizer.business.entity.common.Description;
import com.shopizer.business.entity.order.OrderId;
import com.shopizer.business.entity.references.Country;
import com.shopizer.business.entity.references.Zone;
import com.shopizer.business.entity.user.User;
import com.shopizer.business.repository.common.CurrencyRepository;
import com.shopizer.business.repository.order.OrderIdRepository;
import com.shopizer.business.repository.references.CountryRepository;
import com.shopizer.business.repository.references.ZoneRepository;
import com.shopizer.business.repository.user.UserRepository;
import com.shopizer.business.services.order.OrderIdService;
import com.shopizer.constants.Constants;
import com.shopizer.utils.ZonesLoaderUtil;

@Component
public class References {
	
	@Inject
	private CurrencyRepository currencyRepository;
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private CountryRepository countryRepository;
	
	@Inject
	private ZoneRepository zoneRepository;
	
	@Inject
	private ZonesLoaderUtil zoneLoaderUtil;
	
	@Inject
	private OrderIdRepository orderIdRepository;
	
	@Value("${order.orderId.start}")
	Long initialOrderId;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(References.class);
	
	private static final HashMap<String, Locale> LOCALES = new HashMap<String, Locale>();
	
	public static final List<String> languages = new ArrayList<String>();
	
	@PostConstruct
	@Transactional
	void init() throws Exception {
		
		//add locales to global list of locales
		for (Locale locale : Locale.getAvailableLocales()) {
			//System.out.println(locale + " country " + locale.getDisplayCountry() + " country 2 " + locale.getCountry() + " country 3 " + locale.getDisplayCountry(Locale.CANADA) + " country 4 " + locale.getDisplayCountry(Locale.CANADA_FRENCH));
			LOCALES.put(locale.getCountry(), locale);
		}
		
		//initial load stuff
		createLanguages();

		//country
		createCountries();
		
		
		//zones
		createZones();
		
		createOrderSequenceId();

		/**
		 * ADD REQUIRED CURRENCY TO THE LIST
		 */
		//list of required currencies
		List<Currency> requiredCurrency = new ArrayList<Currency>();
		requiredCurrency.add(new Currency("CAD"));
		requiredCurrency.add(new Currency("USD"));
		
		//try to look if default currency are loaded
		List<Currency> currencies = currencyRepository.findAll();
		if(CollectionUtils.isEmpty(currencies)) {
			currencyRepository.save(requiredCurrency);
		} else {
			
			for(Currency c : requiredCurrency) {
				boolean found = false;
				for(Currency cc : currencies) {
					if(cc.getCode().equals(cc.getCode()))
						found = true;
				}
				if(!found) {
					currencyRepository.save(c);
				}
			}
		}
		/**
		 * END CURRENCY
		 */
		
		/**
		 * Default user
		 */
		
		User admin = userRepository.findByUserName("admin@shopizer.com");
		
		if(admin == null) {
			admin = new User();
			admin.setFirstName("admin");
			admin.setUserName("admin@shopizer.com");
			admin.setLastName("");
			
			List<String> permissions = new ArrayList<String>();
			permissions.add("admin");
			
			admin.setPermissions(permissions);
			
			ShaPasswordEncoder encoder = new ShaPasswordEncoder();
			String encodedPassword = encoder.encodePassword("password", null);
			admin.setPassword(encodedPassword);
			
			userRepository.save(admin);
		}

		
	}
	
	
	private void createCountries() throws Exception {
		LOGGER.info("Populating Countries");
		
		List<Country> countryList = countryRepository.findAll();
		//create a map from the country list
		Map<String,Country> countryMap = new HashMap<String,Country>();
		
		if(!CollectionUtils.isEmpty(countryList)) {
			for(Country c : countryList) {
				countryMap.put(c.getCode(), c);
			}
		}
		
		//list from constant file
		for(String code : Constants.COUNTRY_ISO_CODE) {
			
			if(!countryMap.containsKey(code)) {
			
				Locale locale = LOCALES.get(code);
				if (locale != null) {
					Country country = new Country(code);
	
					for (String language : languages) {//put language in locale fr_CA en_US ...
						//String name = locale.getDisplayCountry(new Locale(code));
						//TODO FRENCH or ENGLISH
						//new locale(language)
						String name = locale.getDisplayCountry(Locale.CANADA_FRENCH);
						Description description = new Description();
						description.setLang(language);
						description.setName(name);
						country.getDescriptions().add(description);
					}
					
					countryRepository.save(country);
				}
			
			}
		}
	}
	

	private void createLanguages() throws Exception {
		LOGGER.info("Populating Languages");
		for(String code : Constants.LANGUAGE_ISO_CODE) {
			languages.add(code);
		}
	}
	
	private void createZones() throws Exception {
		
		List<Zone> zns = zoneRepository.findAll();
		
		if(!CollectionUtils.isEmpty(zns)) {
			return;
		}
		
		
		Map<String, Zone> zones = zoneLoaderUtil.loadZones(languages);
		
		if(!CollectionUtils.isEmpty(zones)) {
			zoneRepository.insert(zones.values());
		}
		
	}
	
	private void createOrderSequenceId() throws Exception {
		

		OrderId orderId = orderIdRepository.findByIdentifier(Constants.ORDERIDIDENTIFIER);
		
		if(orderId!=null) {
			return;
		}
		
		orderId = new OrderId();
		orderId.setId(Constants.ORDERIDIDENTIFIER);
		orderId.setNextOrderNumber(initialOrderId);
		
		orderIdRepository.save(orderId);
	}
	
	public void status() {
		
	}

}
