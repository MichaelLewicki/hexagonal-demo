package cl.lewickidev.hexagonaldemo.api.v1.course.aplication.usecase;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.port.input_port.CourseInputPort;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port.ChildEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseUseCase implements CourseInputPort {

    @Autowired
    @Qualifier("courseRepositoryImp")
    ChildEntityRepository childEntityRepository;

    @Override
    public Course createCourse(Course course, Long idStudent) throws HandledException {
        return childEntityRepository.saveByParentId(course, String.valueOf(idStudent));
    }

    @Override
    public Course getCourseById(String idCourse) throws HandledException {
        return childEntityRepository.getById(idCourse);
    }

    @Override
    public List<Course> getAllCourses() throws HandledException {
        return childEntityRepository.getAll();
    }

    @Override
    public Course updateCourseById(String idCourse, Course course) throws HandledException {
        return childEntityRepository.updateById(idCourse, course);
    }

    @Override
    public void deleteCourseById(String idCourse) throws HandledException {
        childEntityRepository.deleteById(idCourse);
    }

}
