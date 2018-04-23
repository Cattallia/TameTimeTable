package org.itstep.controller;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.itstep.ApplicationRunner;
import org.itstep.model.Student;
import org.itstep.model.Student;
import org.itstep.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

	@MockBean
	StudentService studentService;

	@Autowired
	TestRestTemplate restTemplate;

	private List<Student> students;

	@Before
	public void setUp() throws Exception {
		students = new ArrayList<Student>();

		// User users = new User();

		for (int i = 1; i <= 3; i++) {
			Student student = new Student();
			student.setLogin("Java");
			student.setPassword("54321");
			student.setFirstName("ST1");
			student.setSecondName("ST1_Test");

			students.add(student);
		}
	}

	@Test
	public void testSave() throws URISyntaxException {
		Mockito.when(studentService.save(Mockito.any(Student.class))).thenReturn(students.get(0));

		RequestEntity<Student> request = new RequestEntity<Student>(students.get(0), HttpMethod.POST,
				new URI("/group"));

		ResponseEntity<Student> response = restTemplate.exchange(request, Student.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		Mockito.verify(studentService, Mockito.times(1)).save(Mockito.any(Student.class));
	}

	@Test
	public void testUpdate() throws URISyntaxException {
		Mockito.when(studentService.update(Mockito.any(Student.class))).thenReturn(students.get(0));

		RequestEntity<Student> request = new RequestEntity<Student>(students.get(0), HttpMethod.PUT, new URI("/group"));

		ResponseEntity<Student> response = restTemplate.exchange(request, Student.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		Mockito.verify(studentService, Mockito.times(1)).update(Mockito.any(Student.class));
	}

	@Test
	public void testGetOne() throws URISyntaxException {
		Mockito.when(studentService.get(Mockito.anyString())).thenReturn(students.get(0));

		HttpHeaders headers = new HttpHeaders();
		headers.add("firstName", "ST1");
		headers.add("secondName", "ST1_Test");

		RequestEntity request = new RequestEntity(headers, HttpMethod.GET, new URI("/group/get-one"));
		ResponseEntity<Student> response = restTemplate.exchange(request, Student.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Mockito.verify(studentService, Mockito.times(1)).get(Mockito.anyString());
	}

	@Test
	public void testFindAllByGgoup() throws URISyntaxException {
		Mockito.when(studentService.findAllByGroup(Mockito.anyString())).thenReturn(students);

		HttpHeaders headers = new HttpHeaders();
		headers.add("password", "54321");

		RequestEntity request = new RequestEntity(headers, HttpMethod.GET, new URI("/group/get-by-password"));
		ResponseEntity<List> response = restTemplate.exchange(request, List.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Mockito.verify(studentService, Mockito.times(1)).findAllByGroup(Mockito.anyString());

		assertEquals(3, response.getBody().size());

	}

	@Test
	public void testDelete() throws URISyntaxException {
		Mockito.doNothing().when(studentService).delete(Mockito.any(Student.class));

		RequestEntity<Student> request = new RequestEntity<Student>(students.get(0), HttpMethod.DELETE,
				new URI("/group"));

		ResponseEntity<HttpStatus> response = restTemplate.exchange(request, HttpStatus.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		Mockito.verify(studentService, Mockito.times(1)).delete(Mockito.any(Student.class));
	}

}
