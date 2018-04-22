package org.itstep.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

import org.itstep.ApplicationRunner;
import org.itstep.model.Group;
import org.itstep.model.Lesson;
import org.itstep.model.Subject;
import org.itstep.model.Teacher;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LessonDAOTest {

	Lesson lessonInDB_1;
	Lesson lessonInDB_2;
	Lesson lessonInDB_3;

	Subject subjectInDB;
	Teacher teacherInDB;
	Group groupInDB;

	@Autowired
	LessonDAO lessonDAO;

	@Autowired
	SubjectDAO subjectDAO;

	@Autowired
	TeacherDAO teacherDAO;

	@Autowired
	GroupDAO groupDAO;

	@Before
	public void setPreData() {

		Subject testSubject = new Subject();
		testSubject.setName("JavaQA");

		subjectInDB = subjectDAO.save(testSubject);

		Teacher teacher = new Teacher();
		teacher.setLogin("Ignatenko2207");
		teacher.setPassword("123456");
		teacher.setFirstName("Alex");
		teacher.setSecondName("Ignatenko");
		teacher.setSubject(subjectInDB);

		teacherInDB = teacherDAO.save(teacher);

		Group group = new Group();
		group.setName("ST21");
		group.setCourse("1");
		group.setSpecialization("JavaQA");

		groupInDB = groupDAO.save(group);

		Lesson lesson1 = new Lesson();
		lesson1.setSubject(subjectInDB);
		lesson1.setTeacher(teacherInDB);
		lesson1.setGroup(groupInDB);
		lesson1.setCabinet("cabinet3");
		lesson1.setStartTime(54321L);

		lessonInDB_1 = lessonDAO.save(lesson1);

		Lesson lesson2 = new Lesson();
		lesson2.setSubject(subjectInDB);
		lesson2.setTeacher(teacherInDB);
		lesson2.setGroup(groupInDB);
		lesson2.setCabinet("cabinet5");
		lesson2.setStartTime(87654321L);

		lessonInDB_2 = lessonDAO.save(lesson2);

		Lesson lesson3 = new Lesson();
		lesson3.setSubject(subjectInDB);
		lesson3.setTeacher(teacherInDB);
		lesson3.setGroup(groupInDB);
		lesson3.setCabinet("cabinet7");
		lesson3.setStartTime(987654322L);

		lessonInDB_3 = lessonDAO.save(lesson3);
	}

	@Test
	public void testFindAllByStartTime() {

		List<Lesson> lessons1 = lessonDAO.findAllByStartTime(654321L, 987654321L);

		assertNotNull(lessons1);

		assertEquals(1, lessons1.size());

		assertEquals(lessons1.get(0).getTeacher().getLogin(), "Ignatenko2207");

		assertEquals(lessons1.get(0).getSubject().getName(), "JavaQA");

		assertEquals(lessons1.get(0).getCabinet(), "cabinet5");

		List<Lesson> lessons3 = lessonDAO.findAllByStartTime(321L, 9987654325L);

		assertNotNull(lessons3);

		assertEquals(3, lessons3.size());
	}

	@After
	public void cleanDB() {
		lessonDAO.delete(lessonInDB_1);
		lessonDAO.delete(lessonInDB_2);
		lessonDAO.delete(lessonInDB_3);
		groupDAO.delete(groupInDB);
		teacherDAO.delete(teacherInDB);
		subjectDAO.delete(subjectInDB);
	}
}