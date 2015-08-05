package com.di.genXMLm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

public class App {

	private static final Logger logger = Logger.getLogger("App");
	private static ObjectFactory objectFactory;
	private static HashMap<String, Integer> setup;
	private static BookStoreType bookStoreType;

	public static void main(String[] args) throws JAXBException, DatatypeConfigurationException, IOException {
		Logger logger = Logger.getLogger("logger");

		System.out.println("Wprowadz liczbe autorow: ");
		Scanner read = new Scanner(System.in);
		int authorsAmmount = read.nextInt();
		setup = Utils.generateSetup(authorsAmmount);
		objectFactory = new ObjectFactory();
		bookStoreType = objectFactory.createBookStoreType();

		generateData();
		logger.info("Generowanie danych zakonczone");

		saveToXML("wynik.xml");
		logger.info("Zapis zakonczony");
	}

	static void generateData() throws IOException, DatatypeConfigurationException {
		Generators.generateAuthors(objectFactory, bookStoreType, setup);
		Generators.generateCustomers(objectFactory, bookStoreType, setup);
		Generators.generateBooks(objectFactory, bookStoreType, setup);
		Generators.generateOrders(objectFactory, bookStoreType, setup);
	}

	static void saveToXML(String fileName) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		JAXBElement<BookStoreType> element = objectFactory.createBookStore(bookStoreType);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);

		FileOutputStream os = new FileOutputStream(new File("wynik.xml"));
		marshaller.marshal(element, os);
	}
}
