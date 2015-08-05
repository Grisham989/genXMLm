package com.di.genXMLm;

import javax.xml.datatype.DatatypeConfigurationException;

public final class OrderFactory {
	static OrderType makeOrder(ObjectFactory objectFactory, String customerCode, String bookCode) throws DatatypeConfigurationException {
		OrderType orderType = objectFactory.createOrderType();

		orderType.setOrderDate(Utils.generateRandomDate());
		orderType.setCustomerCode(customerCode);

		orderType.setBookCode(bookCode);

		String status = Utils.randomString(32, false);
		orderType.setStatus(status);

		return orderType;
	}
}
