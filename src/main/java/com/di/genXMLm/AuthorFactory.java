package com.di.genXMLm;

import java.io.IOException;

public final class AuthorFactory {
	public static AuthorType makeAuthor(ObjectFactory objectFactory, int authorNumber) throws IOException
	{
		AuthorType authorType = objectFactory.createAuthorType();
		authorType.setFirstName(Utils.randomFirstName());
		authorType.setLastName(Utils.randomLastName());
		authorType.setPseudonym(Utils.randomString(10, false));

		int numberLength = Integer.toString(authorNumber).length();
		String code = Utils.randomNumber(32-numberLength, true);
		StringBuilder sb = new StringBuilder(code);
		sb.append(authorNumber);
		authorType.setCode(sb.toString());
		
		return authorType;
	}
}
