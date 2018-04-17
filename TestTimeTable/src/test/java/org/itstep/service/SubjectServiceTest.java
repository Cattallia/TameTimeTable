package org.itstep.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.itstep.ApplicationRunner;
import org.itstep.dao.SubjectDAO;
import org.itstep.model.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationRunner.class })
public class SubjectServiceTest {

	@Autowired
	SubjectService subjectService;

	@MockBean
	SubjectDAO subjectDao;

	@Test
	public void testFindAllByName() {

		List<Subject> subjects = new ArrayList<Subject>();
		subjects.add(new Subject());
		String kj = "first";
		Mockito.when(subjectDao.findAllByName(kj)).thenReturn(subjects);

		List<Subject> subjectsFromDB = subjectService.findAllByName(kj);

		assertNotNull(subjectsFromDB);
	}

}
