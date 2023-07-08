package cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.adapter.output_adapter.jpa.repository.imp;


import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.entity.CourseEntity;
import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.entity.ExamEntity;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Errors;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port.ParentEntityRepository;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.entity.StudentEntity;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.adapter.output_adapter.jpa.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentRepositoryImp implements ParentEntityRepository {

    @Autowired
    @Lazy
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public <T> T save(T object ) throws HandledException {
        try {
            if (object instanceof Student) {
                Student student = (Student) object;
                if (student.getIdStudent() != null) {
                    throw new HandledException(Errors.BAD_REQUEST, "El campo 'id' no debe ser incluido en el payload del objeto Student.");
                } else {
                    StudentEntity studentEntity = new StudentEntity();
                    studentEntity = mapToEntity(student);
                    studentEntity = studentRepository.save(studentEntity);
                    if (studentEntity != null) {
                        student = mapToModel(studentEntity);
                        return (T) student;
                    }
                    return null;
                }
            } else {
                throw new IllegalArgumentException("El objeto proporcionado no es un objeto de estudiante válido");
            }
        } catch (HandledException he) {
            throw he;
        }
    }

    @Override
    @Transactional
    public <T> T getById(String id) throws HandledException {
        try {
            StudentEntity studentEntity = studentRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Student no encontrado asociado al ID: " + id));
            Student student = mapToModel(studentEntity);
            /*if (studentEntity.getExams() != null) {
                List<Exam> examList = new ArrayList<>();
                for (ExamEntity examEntityFound : studentEntity.getExams()) {
                    Exam examFound = mapExamEntityToModel(examEntityFound);
                    examList.add(examFound);
                }
                student.setExams(examList);
            }*/
            return (T) student;
        } catch (HandledException he) {
            throw he;
        }
    }

    @Override
    @Transactional
    public <T> List<T> getAll() throws HandledException {
        try {
            List<StudentEntity> studentEntitysList = studentRepository.findAll();
            if (!studentEntitysList.isEmpty()) {
                List<Student> studentList = new ArrayList<>();
                for (StudentEntity studentEntity : studentEntitysList) {
                    Student student = new Student();
                    student = mapToModel(studentEntity);
                    studentList.add(student);
                }
                return (List<T>) studentList;
            } else {
                throw new HandledException(Errors.NO_CONTENT, "No se han encontrado registros de Student en la base de datos");
            }
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new HandledException(Errors.CONFLICT, "Se produjo un error al intentar acceder a los datos");
        }
    }

    @Override
    @Transactional
    public <T> T updateById(String id, T object) throws HandledException {
        try {
            StudentEntity studentEntityFound = studentRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Student no encontrado asociado al ID: " + id));;
            if (object instanceof Student) {
                Student student = (Student) object;
                studentEntityFound.setFirstName(student.getFirstName());
                studentEntityFound.setMiddleName(student.getMiddleName());
                studentEntityFound.setLastName(student.getLastName());
                studentEntityFound.setSecondLastName(student.getSecondLastName());
                StudentEntity studentEntityModified = studentRepository.save(studentEntityFound);
                if (studentEntityModified != null) {
                    student.setIdStudent(studentEntityFound.getIdStudent());
                    return (T) student;
                } else {
                    return null;
                }
            } else {
                throw new IllegalArgumentException("El objeto proporcionado no es un objeto de estudiante válido");
            }
        } catch (HandledException he) {
            throw he;
        }
    }

    @Override
    @Transactional
    public void deleteById(String id) throws HandledException {
        try {
            StudentEntity studentEntity = studentRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Student no encontrado asociado al ID: " + id));
            for (CourseEntity courseEntity : studentEntity.getCourses()) {
                courseEntity.getStudents().remove(studentEntity);
            }
            for (ExamEntity examEntity : studentEntity.getExams()) {
                examEntity.setStudent(null);
            }
            studentRepository.delete(studentEntity);
        } catch (HandledException he) {
            throw he;
        }
    }

    private Exam mapExamEntityToModel(ExamEntity examEntity) {
        Exam exam = modelMapper.map(examEntity, Exam.class);
        return exam;
    }

    private Student mapToModel(StudentEntity studentEntity) {
        Student student = modelMapper.map(studentEntity, Student.class);
        return student;
    }

    private StudentEntity mapToEntity(Student student) {
        StudentEntity studentEntity = modelMapper.map(student, StudentEntity.class);
        return studentEntity;
    }
}
