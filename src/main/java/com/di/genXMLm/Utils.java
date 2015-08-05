package com.di.genXMLm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public final class Utils {
	private static ArrayList<String> firstNameList = new ArrayList<String>();
	private static ArrayList<String> lastNameList = new ArrayList<String>();

	public Utils() {
		System.out.println("konstruktor");
	}

	public static String randomFirstName() throws IOException {
		if (firstNameList.isEmpty()) {
			loadFirstNames();
		}
		int number = (int) Math.round(Math.random() * firstNameList.size()-1);
		return firstNameList.get(number);

	}

	public static String randomLastName() throws IOException {
		if (lastNameList.isEmpty()) {
			loadLastNames();
		}
		int number = (int) Math.round(Math.random() * firstNameList.size()-1);
		return lastNameList.get(number);

	}

	private static void loadFirstNames() throws IOException {
		String str;
		BufferedReader br = new BufferedReader(new FileReader("imiona.txt"));
		do {
			str = br.readLine();
			if (str != null) {
				firstNameList.add(str);
			}
		} while (str != null);
	}

	private static void loadLastNames() throws IOException {
		String str;
		BufferedReader br = new BufferedReader(new FileReader("nazwiska.txt"));
		do {
			str = br.readLine();
			if (str != null) {
				lastNameList.add(str);
			}
		} while (str != null);
	}

	static String randomNumber(int length, boolean constantLength) {
		String chars = "0123456789";
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		if (!constantLength) {
			length = 5 + rand.nextInt(length - 5);
		}
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(rand.nextInt(chars.length())));
		}
		return sb.toString();
	}

	static String randomString(int length, boolean constantLength) {
		String chars = "abcdefghijklmnopqrstuvwxyz ";
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		if (!constantLength) {
			length = 5 + rand.nextInt(length - 5);
		}
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(rand.nextInt(chars.length())));
		}
		return sb.toString();
	}
	
	static HashMap<String, Integer> generateSetup(int authorsAmmount) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("authors", authorsAmmount);
		map.put("customers", 5 * authorsAmmount);
		map.put("books", 10 * authorsAmmount);
		map.put("order", 25 * authorsAmmount);
		return map;		
	}
	
	static XMLGregorianCalendar generateRandomDate() throws DatatypeConfigurationException
	{
		XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		Random rand = new Random();
		cal.setYear(1950 + rand.nextInt(64));
		cal.setMonth(rand.nextInt(11) + 1);
		cal.setDay(rand.nextInt(30) + 1);
		cal.setHour(rand.nextInt(23) + 1);
		cal.setMinute(rand.nextInt(59) + 1);
		cal.setSecond(rand.nextInt(59) + 1);
		return cal;
	}
}
