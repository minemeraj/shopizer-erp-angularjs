package com.shopizer.business.services.messaging.emails;


public interface HtmlEmailSender {
	
	public void send(final Email email) throws Exception;

}
