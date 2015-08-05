package com.di.genXMLm;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;

public final class Generators {
	static Logger logger = Logger.getLogger("Generators");
	static void generateAuthors(ObjectFactory objectFactory, BookStoreType bookStoreType, HashMap<String, Integer> setup) throws IOException {
		AuthorsType authorsType = objectFactory.createAuthorsType();
		for (int authorsIt = 0; authorsIt < setup.get("authors"); authorsIt++) {
			if (authorsIt % 10000 == 0) {
				StringBuilder sb = new StringBuilder("a: ");
				sb.append(authorsIt).append("/").append(setup.get("authors"));
				logger.info(sb.toString());
			}
			authorsType.getAuthor().add(AuthorFactory.makeAuthor(objectFactory, authorsIt));
		}

		logger.info("Generowanie autorow zakonczone");

		bookStoreType.setAuthors(authorsType);
	}

	static void generateCustomers(ObjectFactory objectFactory, BookStoreType bookStoreType, HashMap<String, Integer> setup) throws IOException {
		CustomersType customersType = objectFactory.createCustomersType();
		for (int customersIt = 0; customersIt < setup.get("customers"); customersIt++) {
			if (customersIt % 10000 == 0) {
				StringBuilder sb = new StringBuilder("c: ");
				sb.append(customersIt).append("/").append(setup.get("customers"));
				logger.info(sb.toString());
			}
			customersType.getCustomer().add(CustomerFactory.makeCustomer(objectFactory, customersIt));
		}
		logger.info("Generowanie klientow zakonczone");

		bookStoreType.setCustomers(customersType);
	}

	static void generateBooks(ObjectFactory objectFactory, BookStoreType bookStoreType, HashMap<String, Integer> setup) {
		BooksType booksType = objectFactory.createBooksType();
		for (int booksIt = 0; booksIt < setup.get("books"); booksIt++) {
			if (booksIt % 10000 == 0) {
				StringBuilder sb = new StringBuilder("c: ");
				sb.append(booksIt).append("/").append(setup.get("books"));
				logger.info(sb.toString());
			}
			int authorID = (int) Math.round(Math.random() * (setup.get("authors") - 1));
			String authorCode = bookStoreType.getAuthors().getAuthor().get(authorID).getCode();
			booksType.getBook().add(BookFactory.makeBook(objectFactory, booksIt, authorCode));
		}
		logger.info("Generowanie ksiazek zakonczone");
		bookStoreType.setBooks(booksType);
	}

	static void generateOrders(ObjectFactory objectFactory, BookStoreType bookStoreType, HashMap<String, Integer> setup) throws DatatypeConfigurationException {
		OrdersType ordersType = objectFactory.createOrdersType();
		for (int customersIt = 0; customersIt < setup.get("customers"); customersIt++) {
			if (customersIt % 10000 == 0) {
				StringBuilder sb = new StringBuilder("o: ");
				sb.append(customersIt).append("/").append(setup.get("customers"));
				logger.info(sb.toString());
			}
			String customerCode = bookStoreType.getCustomers().getCustomer().get(customersIt).getCode();
			for (int cust_ord = 0; cust_ord < 5; cust_ord++) {
				int bookID = (int) Math.round(Math.random() * (setup.get("authors") - 1));
				String bookCode = bookStoreType.getBooks().getBook().get(bookID).getCode();
				ordersType.getOrder().add(OrderFactory.makeOrder(objectFactory, customerCode, bookCode));
			}
		}
		logger.info("Generowanie zamowien zakonczone");
		bookStoreType.setOrders(ordersType);
	}
}
