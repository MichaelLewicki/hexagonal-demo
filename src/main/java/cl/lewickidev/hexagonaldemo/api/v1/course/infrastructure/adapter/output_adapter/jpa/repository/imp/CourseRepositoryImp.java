package cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.adapter.output_adapter.jpa.repository.imp;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.adapter.output_adapter.jpa.repository.CourseRepository;
import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.entity.CourseEntity;
import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.entity.ExamEntity;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Errors;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port.ChildEntityRepository;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.adapter.output_adapter.jpa.repository.StudentRepository;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.entity.StudentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CourseRepositoryImp implements ChildEntityRepository {

    @Autowired
    //@Qualifier("courseRepository")
    @Lazy
    private CourseRepository courseRepository;

    @Autowired
    //@Qualifier("studentRepository")
    @Lazy
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public <T> T saveByParentId(T object, String idParent) throws HandledException {
        try {
            StudentEntity studentEntityFound = studentRepository.findById(Long.valueOf(idParent))
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Student no encontrado asociado al ID: " + idParent));
            if (object instanceof Course) {
                Course course = (Course) object;
                if (course.getIdCourse() != null) {
                    throw new HandledException(Errors.BAD_REQUEST, "El campo 'id' no debe ser incluido en el payload del objeto Course.");
                } else {
                    CourseEntity courseEntity = new CourseEntity();
                    courseEntity = mapToEntity(course);
                    courseEntity.setIdCourse(UUID.randomUUID().toString());
                    courseEntity.getStudents().add(studentEntityFound);
                    CourseEntity courseEntityCreated = courseRepository.save(courseEntity);
                    if (courseEntityCreated != null) {
                        course = mapToModel(courseEntityCreated);
                        return (T) course;
                    }
                    return null;
                }
            } else {
                throw new IllegalArgumentException("El objeto proporcionado no es un objeto de Course válido");
            }
        } catch (HandledException he) {
            throw he;
        }
    }

    @Override
    @Transactional
    public <T> T getById(String idCourse) throws HandledException {
        try {
            CourseEntity courseEntity = courseRepository.findById(idCourse)
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Course no encontrado asociado al ID: " + idCourse));
            Course course = mapToModel(courseEntity);
            return (T) course;
        } catch (HandledException he) {
            throw he;
        }
    }

    @Override
    @Transactional
    public <T> List<T> getAll() throws HandledException {
        try {
            List<CourseEntity> courseEntitiesList = courseRepository.findAll();
            if (!courseEntitiesList.isEmpty()) {
                List<Course> courseList = new ArrayList<>();
                for (CourseEntity courseEntity : courseEntitiesList) {
                    Course course = new Course();
                    course = mapToModel(courseEntity);
                    courseList.add(course);
                }
                return (List<T>) courseList;
            } else {
                throw new HandledException(Errors.NO_CONTENT, "No se han encontrado registros de Courses en la base de datos");
            }
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new HandledException(Errors.CONFLICT, "Se produjo un error al intentar acceder a los datos");
        }
    }

    //RECORDAR MODIFICAR ESTE ENDPOINT CON EL MAP AL SETEAR EL OBJETO ENCONTRADO SÓLO SI FUNCIONA EN EXAMREPOSITORY
    @Override
    @Transactional
    public <T> T updateById(String id, T object) throws HandledException {
        try {
            CourseEntity courseEntityFound = courseRepository.findById(id)
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Course no encontrado asociado al ID: " + id));;
            if (object instanceof Course) {
                Course course = (Course) object;
                courseEntityFound.setName(course.getName());
                CourseEntity courseEntityModified = courseRepository.save(courseEntityFound);
                if (courseEntityModified != null) {
                    course.setIdCourse(courseEntityFound.getIdCourse());
                    List<Student> studentList = new ArrayList<>();
                    courseEntityFound.getStudents().forEach(studentEntity -> studentList.add(mapStudentToModel(studentEntity)));
                    course.setStudents(studentList);
                    return (T) course;
                } else {
                    return null;
                }
            } else {
                throw new IllegalArgumentException("El objeto proporcionado no es un objeto de Course válido");
            }
        } catch (HandledException he) {
            throw he;
        }
    }

    @Override
    @Transactional
    public void deleteById(String id) throws HandledException {
        try {
            CourseEntity courseEntity = courseRepository.findById(id)
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Course no encontrado asociado al ID: " + id));
            for (StudentEntity studentEntity : courseEntity.getStudents()) {
                studentEntity.getCourses().remove(courseEntity);
            }
            for (ExamEntity examEntity : courseEntity.getExams()) {
                examEntity.setCourse(null);
            }
            courseRepository.delete(courseEntity);
        } catch (HandledException he) {
            throw he;
        }
    }

    private Course mapToModel(CourseEntity courseEntity) {
        Course course = modelMapper.map(courseEntity, Course.class);
        return course;
    }

    private CourseEntity mapToEntity(Course course) {
        CourseEntity courseEntity = modelMapper.map(course, CourseEntity.class);
        return courseEntity;
    }

    private Student mapStudentToModel(StudentEntity studentEntity) {
        Student student = modelMapper.map(studentEntity, Student.class);
        return student;
    }

}
