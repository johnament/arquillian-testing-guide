package com.tad.arquillian.chapter9.test.str1;

import com.tad.arquillian.chapter9.test.api.StringProvider;

public class StringOne implements StringProvider {
	public String getString() {
		return getClass().getCanonicalName();
	}
}
