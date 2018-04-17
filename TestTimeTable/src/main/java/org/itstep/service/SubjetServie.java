package org.itstep.service;

import java.util.List;

import org.itstep.model.Subject;

public interface SubjetServie {
	Subject save(Subject subject);

	Subject update(Subject subject);

	Subject get(String subjectName);

	void delete(Subject subject);

	List<Subject> findAllByCourse(String course);
}
