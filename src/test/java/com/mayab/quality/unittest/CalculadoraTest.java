package com.mayab.quality.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
	
	private Calculadora cal = null;
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("Before all test case");	
	}
	@AfterAll
	static void afterAll() {
		System.out.println("Afeter all test case");	
	}
	@BeforeEach
	void setup() {
		System.out.println("Before each test");	
		cal = new Calculadora();	
	}
	
	@AfterEach
	void cleanup() {
		System.out.println("After each test");
	}
	
	@Test
	void test2() {
	System.out.println("Hola testcase2");	
	}
	
	@Test
	void testSuma() {
		//Setup
		
		double num1 = 10;
		double num2 = 5;
		double expResult = 15;
		//Exercise
		double result = cal.suma(num2, num1);
		
		//Assertion
		assertThat(result, is (expResult));			
	}
	
	@Test
	void testSubs() {
		//Setup
		double num1 = 10;
		double num2 = 5;
		double expResult = 5;
		//Exercise
		double result = cal.resta(num1, num2);
		
		//Assertion
		assertThat(result, is (expResult));			
	}
}