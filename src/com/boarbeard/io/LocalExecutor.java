package com.boarbeard.io;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentResolver;
import android.content.res.XmlResourceParser;

import com.boarbeard.io.XmlHandler.HandlerException;

public class LocalExecutor<T> {
	private static final String TAG = "LocalExecutor";
	
	private static XmlPullParserFactory sFactory;
	private final ContentResolver mContentResolver;

	public LocalExecutor(ContentResolver contentResolver) {
		mContentResolver = contentResolver;
	}

	public T execute(InputStream inputStream, XmlHandler<T> handler) throws HandlerException {			
		try {
			XmlPullParser parser = createPullParser(inputStream);
			return handler.parseAndHandle(parser, mContentResolver);
		} catch (XmlPullParserException e) {
			throw new HandlerException("", e);
		} 		
	}
	
	public T execute(XmlResourceParser xmlResourceParser, XmlHandler<T> handler) throws HandlerException {
		try {
			return handler.parseAndHandle(xmlResourceParser, mContentResolver);
		}  catch (HandlerException e) {
			throw new HandlerException("", e);
		}
	}

	private static XmlPullParser createPullParser(InputStream inputStream) throws XmlPullParserException {
		if (sFactory == null) {
			sFactory = XmlPullParserFactory.newInstance();
		}
		final XmlPullParser parser = sFactory.newPullParser();
		parser.setInput(inputStream, null);
		return parser;
	}


}
