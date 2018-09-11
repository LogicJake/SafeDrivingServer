package com.scy.driving.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

public class UtilityTest {
	
	@Test
	public void test() {
		List<Pattern> URL_REGEX_PATTERN_OPEN = new ArrayList<Pattern>();
		String[] URL_WITHOUT_CLAIMS = { "\\/avatar\\/.*"};
		for (String openUrl : URL_WITHOUT_CLAIMS) {
			System.out.println(openUrl);
			URL_REGEX_PATTERN_OPEN.add(Pattern.compile(openUrl));
		}
		Boolean res = Utility.matchPattern("/avatar/1.jpg", URL_REGEX_PATTERN_OPEN);
		System.out.println(res);
	}
	
}
