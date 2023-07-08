package cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.port.input_port;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;

import java.util.List;

public interface CourseInputPort {

    public Course createCourse(Course course, Long idStudent) throws HandledException;

    public Course getCourseById(String idCourse) throws HandledException;

    public List<Course> getAllCourses() throws HandledException;

    public Course updateCourseById(String idCourse, Course course) throws HandledException;

    public void deleteCourseById(String idCourse) throws HandledException;

}