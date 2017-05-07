package com.shopizer.restpopulators;

import java.util.Locale;

public interface DataPopulator<Source, Target> {
	
	public Target populateModel(Source source, Locale locale) throws Exception;
	public Source populateWeb(Target source, Locale locale) throws Exception;

}
