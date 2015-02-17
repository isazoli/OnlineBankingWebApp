package com.isazoli.onlinebanking.account;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordGeneratorTest {
	//TODO: inject encoder from Spring configuration
	private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

	@Test
	public void testPersistedPassword() {
		assertTrue(passwordEncoder
				.matches(
						"qwe123",
						"fa8d685d3b2e00cff043680c8a2debd57a70d5611b6b9f551640cedbe9451c49ae4d3725064c5b69"));
		assertTrue(passwordEncoder
				.matches(
						"asd456",
						"ffc5b84443c59b2e19e6b7fa7f32a33e3d1ec3aa59aa3bf3a330a67c59a19a400be90750a143dcc3"));
		assertTrue(passwordEncoder
				.matches(
						"yxc789",
						"baf621b28c778c93acee002fc6815045458b9656145a8b589b992c9220596bb0cd7839eebd52cbad"));
		assertTrue(passwordEncoder
				.matches(
						"ert345",
						"baefb420b48e578a2093a610bd0f1fbd40b480d6a7ef0ea101069931b25057fe7045a2ae037fabeb"));
	}
}
