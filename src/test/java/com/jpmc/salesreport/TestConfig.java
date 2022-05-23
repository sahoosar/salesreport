package com.jpmc.salesreport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public
class TestConfig {
    @Autowired
	private Environment environment;

	@Test
	public void test_Profile(){
		assertTrue(Arrays.asList(environment.getActiveProfiles()).contains("test"));
	}


}
