package com.tad.arquillian.chapter9.test.str2;

import com.tad.arquillian.chapter9.test.api.StringProvider;

public class StringTwo implements StringProvider {
	public String getString() {
		return getClass().getSimpleName();
	}
}
