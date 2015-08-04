package com.di.genXMLm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

public class App {
	private static final Logger logger = Logger.getLogger("App");

	public static void main(String[] args) throws JAXBException, DatatypeConfigurationException, IOException {
		Logger logger = Logger.getLogger("logger");

		System.out.println("Wprowadz liczbe autorow: ");
		Scanner read = new Scanner(System.in);
		int liczba_autorow = read.nextInt();

		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		// SETUP//
		counts.put("authors", liczba_autorow);
		counts.put("customers", 5 * liczba_autorow);
		counts.put("books", 10 * liczba_autorow);
		counts.put("order", 25 * liczba_autorow);
		// SETUP - end//

		ObjectFactory objectFactory = new ObjectFactory();

		AuthorsType authorsType = objectFactory.createAuthorsType();
		for (int authorsIt = 0; authorsIt < counts.get("authors"); authorsIt++) {
			if (authorsIt % 10000 == 0) {
				StringBuilder sb = new StringBuilder("a: ");
				sb.append(authorsIt).append("/").append(counts.get("authors"));
				logger.info(sb.toString());
			}
			authorsType.getAuthor().add(AuthorFactory.makeAuthor(objectFactory, authorsIt));
		}

		logger.info("Generowanie autorow zakonczone");

		CustomersType customersType = objectFactory.createCustomersType();
		for (int customersIt = 0; customersIt < counts.get("customers"); customersIt++) {
			if (customersIt % 10000 == 0) {
				StringBuilder sb = new StringBuilder("c: ");
				sb.append(customersIt).append("/").append(counts.get("customers"));
				logger.info(sb.toString());
				;
			}

			CustomerType customerType = objectFactory.createCustomerType();
			String code = Utils.randomNumber(15, true);
			StringBuilder sb = new StringBuilder(code);
			sb.append(customersIt);
			customerType.setCode(sb.toString());

			String barCode = Utils.randomNumber(32, true);
			sb = new StringBuilder(barCode);
			sb.append(customersIt);
			customerType.setBarCode(sb.toString());

			customerType.setFirstName(Utils.randomFirstName());
			customerType.setLastName(Utils.randomLastName());
			String pesel = Utils.randomNumber(10 - (Integer.toString(customersIt).length()), true);
			sb = new StringBuilder(code);
			sb.append(customersIt);
			customerType.setPesel(sb.toString());

			String phone = Utils.randomNumber(9 - (Integer.toString(customersIt).length()), true);
			sb = new StringBuilder(code);
			sb.append(customersIt);
			customerType.setPhone(sb.toString());
			customerType.setAddress(Utils.randomString(32, false));
			customersType.getCustomer().add(customerType);
		}
		logger.info("Generowanie klientow zakonczone");

		BooksType booksType = objectFactory.createBooksType();
		for (int it_books = 0; it_books < counts.get("books"); it_books++) {
			if (it_books % 10000 == 0) {
				StringBuilder sb = new StringBuilder("c: ");
				sb.append(it_books).append("/").append(counts.get("books"));
				logger.info(sb.toString());
			}

			BookType bookType = objectFactory.createBookType();
			int authorID = (int) Math.round(Math.random() * (counts.get("authors") - 1));
			bookType.setAuthorCode(authorsType.getAuthor().get(authorID).getCode());

			String code = Utils.randomString(32, true);
			StringBuilder sb = new StringBuilder(code);
			sb.append(it_books);
			bookType.setCode(sb.toString());
			bookType.setDescription(Utils.randomString(255, false));

			String isbn = Utils.randomString(32, true);
			sb = new StringBuilder(isbn);
			sb.append(it_books);
			bookType.setISBN(sb.toString());

			int price = 10 + (int) Math.round(Math.random() * 190);
			bookType.setPrice(Integer.toString(price));

			bookType.setTitle(Utils.randomString(32, false));
			booksType.getBook().add(bookType);
		}
		logger.info("Generowanie ksiazek zakonczone");

		OrdersType ordersType = objectFactory.createOrdersType();
		for (int it_customers = 0; it_customers < counts.get("customers"); it_customers++) {
			if (it_customers % 10000 == 0) {
				StringBuilder sb = new StringBuilder("o: ");
				sb.append(it_customers).append("/").append(counts.get("customers"));
				logger.info(sb.toString());
			}
			for (int cust_ord = 0; cust_ord < 5; cust_ord++) {
				OrderType orderType = objectFactory.createOrderType();

				// generowanie losowej daty
				XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
				Random rand = new Random();
				cal.setYear(1950 + rand.nextInt(64));
				cal.setMonth(rand.nextInt(11) + 1);
				cal.setDay(rand.nextInt(30) + 1);
				cal.setHour(rand.nextInt(23) + 1);
				cal.setMinute(rand.nextInt(59) + 1);
				cal.setSecond(rand.nextInt(59) + 1);
				orderType.setOrderDate(cal);

				orderType.setCustomerCode(customersType.getCustomer().get(it_customers).getCode());

				int bookID = (int) Math.round(Math.random() * (counts.get("books") - 1));
				orderType.setBookCode(booksType.getBook().get(bookID).getCode());

				String status = Utils.randomString(32, false);
				orderType.setStatus(status);
				ordersType.getOrder().add(orderType);
			}
		}
		logger.info("Generowanie zamowien zakonczone");

		BookStoreType bookStoreType = objectFactory.createBookStoreType();
		bookStoreType.setAuthors(authorsType);
		bookStoreType.setBooks(booksType);
		bookStoreType.setCustomers(customersType);
		bookStoreType.setOrders(ordersType);

		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		JAXBElement<BookStoreType> element = objectFactory.createBookStore(bookStoreType);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);

		FileOutputStream os = new FileOutputStream(new File("wynik.xml"));
		marshaller.marshal(element, os);
	}
}
