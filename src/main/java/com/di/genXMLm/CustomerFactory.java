package com.di.genXMLm;

import java.io.IOException;

public final class CustomerFactory {
	static CustomerType makeCustomer(ObjectFactory objectFactory, int customerNumber) throws IOException
	{
		CustomerType customerType = objectFactory.createCustomerType();
		String code = Utils.randomNumber(15, true);
		StringBuilder sb = new StringBuilder(code);
		sb.append(customerNumber);
		customerType.setCode(sb.toString());

		String barCode = Utils.randomNumber(32, true);
		sb = new StringBuilder(barCode);
		sb.append(customerNumber);
		customerType.setBarCode(sb.toString());

		customerType.setFirstName(Utils.randomFirstName());
		customerType.setLastName(Utils.randomLastName());
		String pesel = Utils.randomNumber(10 - (Integer.toString(customerNumber).length()), true);
		sb = new StringBuilder(pesel);
		sb.append(customerNumber);
		customerType.setPesel(sb.toString());

		String phone = Utils.randomNumber(9 - (Integer.toString(customerNumber).length()), true);
		sb = new StringBuilder(phone);
		sb.append(customerNumber);
		customerType.setPhone(sb.toString());
		customerType.setAddress(Utils.randomString(32, false));
		
		return customerType;
	}
}
