package com.anahuac.quality.doubles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mayab.quality.doubles.Dependency;
import com.mayab.quality.doubles.SubDependency;

class DependecyTest {
	
	private SubDependency subdependency;
	private Dependency dependency;
	public static final int TEST_NUMBER = 3;

	@BeforeEach
	public void setupMock()
	{
		subdependency = mock(SubDependency.class);
		dependency = mock(Dependency.class);
	}
	
	@Test
	void getClassNameTest()
	{
		String name = dependency.getSubDependencyClassName();
		assertThat(null, is("SubDependency.class"));
		System.out.println(name);
	}
	
	@Test
	void addTwoTest()
	{
		int num = 10;
		int expected = 3;
		
		when(dependency.addTwo(anyInt())).thenReturn(TEST_NUMBER);
		int result = dependency.addTwo(1);
		
		assertThat(expected, is(result));
	}
	
	@Test
	public void testAnswer()
	{
		when(dependency.addTwo(anyInt())).thenAnswer(new Answer<Integer>() {
			
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				int arg = (Integer) invocation.getArguments()[0];
				return arg + 20;
			}
		});
		
		assertThat(30, is(dependency.addTwo(10)));
	}
	
	@Test
	public void testCallMethod()
	{
		when(dependency.addTwo(anyInt())).thenCallRealMethod();
		
		assertThat(30, is(dependency.addTwo(10)));
	}


}
