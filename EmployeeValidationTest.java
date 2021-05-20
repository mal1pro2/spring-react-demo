package com.moodys.demo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeValidationTest {

	private static ValidatorFactory validatorFactory;
	private static Validator validator;

	@BeforeClass
	public static void createValidator() {
		validatorFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@AfterClass
	public static void close() {
		validatorFactory.close();
	}

	@Test
	public void shouldDetectInvalidName() {
		// given too short name:
		Employee employee = new Employee();
		// employee.setName("Nasik");

		// when:
		Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

		// then:
		System.out.println(violations.size());
		assertEquals(violations.size(), 1);

	}
}
