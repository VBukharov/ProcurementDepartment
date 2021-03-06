package org.bukharov.procurementDepartment.logic;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.bukharov.procurementDepartment.model.entity.Author;
import org.bukharov.procurementDepartment.model.service.AuthorService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-logic-config.xml")
@Transactional
public class AuthorServiceTest {

	@Autowired
	AuthorService service;


	@Test
	public void testCreateAuthor() {
		Author author = new Author("testValue", "testValue", "testValue", "testValue");
		Author result = service.save(author);
		System.out.println(result.getName() + " " + result.getSecondName());
		assertEquals(author, result);
	}

	@Test
	public void testUpdateAuthor() {
		String name = "testValue";
		String surname = "testValue";
		String secondName = "testValue";
		Author author = new Author(name, surname, secondName, "");
		service.save(author);
		Author result = service.findByNameAndSurnameAndSecondName(name, surname, secondName).get(0);
		result.setName("new value");
		result.setSecondName("new value");
		result.setSurname("new value");
		Author toCompare = service.findByNameAndSurnameAndSecondName("new value", "new value", "new value").get(0);
		assertEquals(toCompare, result);
	}

	@Test
	public void testDeleteAuthor() {
		String name = "testValue";
		String surname = "testValue";
		String secondName = "testValue";
		String biography = "testValue";
		int beforeSize = service.findByNameAndSurnameAndSecondName(name, surname, secondName).size();
		Author author = new Author(name, surname, secondName, biography);
		service.save(author);
		service.delete(author);
		assertEquals(beforeSize, service.findByNameAndSurnameAndSecondName(name, surname, secondName).size());
	}

	@Test
	public void testGetAllAuthors() {
		int addition = 5;
		int beforeSize = service.findAll().size() + addition;
		for (int i = 0; i < addition; i++) {
			service.save(new Author("testValue" + i, "testValue" + i, "testValue" + i, "testValue" + i));
		}
		int afterSize = service.findAll().size();
		assertEquals(beforeSize, afterSize);
	}

}
