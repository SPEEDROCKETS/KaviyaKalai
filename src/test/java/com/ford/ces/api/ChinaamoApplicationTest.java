package com.ford.ces.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest(classes=ChinaamoApplicationTest.class)
class ChinaamoApplicationTest {
	@Test
	void contextLoads() {
		try {
		} catch (Exception exception) {
			fail("Spring Boot container failed to boot up...");
		}
	}
}