package com.di.genXMLm;

public final class BookFactory {
	static BookType makeBook(ObjectFactory objectFactory, int bookNumber, String authorCode) {
		BookType bookType = objectFactory.createBookType();
		bookType.setAuthorCode(authorCode);
		
		int numberLength = Integer.toString(bookNumber).length();
		String code = Utils.randomNumber(32-numberLength, true);
		StringBuilder sb = new StringBuilder(code);
		sb.append(bookNumber);
		bookType.setCode(sb.toString());
		bookType.setDescription(Utils.randomString(255, false));

		String isbn = Utils.randomNumber(32-numberLength, true);
		sb = new StringBuilder(isbn);
		sb.append(bookNumber);
		bookType.setISBN(sb.toString());

		int price = 10 + (int) Math.round(Math.random() * 190);
		bookType.setPrice(Integer.toString(price));

		bookType.setTitle(Utils.randomString(32, false));

		return bookType;
	}
}
