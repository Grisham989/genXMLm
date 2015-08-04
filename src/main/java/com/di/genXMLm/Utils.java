package com.di.genXMLm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
		int number = (int) Math.round(Math.random() * firstNameList.size())-1;
		return firstNameList.get(number);

	}

	public static String randomLastName() throws IOException {
		if (firstNameList.isEmpty()) {
			loadLastNames();
		}
		int number = (int) Math.round(Math.random() * firstNameList.size())-1;
		return firstNameList.get(number);

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
}
