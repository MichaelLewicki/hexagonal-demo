package cl.lewickidev.hexagonaldemo.api.v1.student.aplication.usecase;

import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port.ParentEntityRepository;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.port.input_port.StudentInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentUseCase implements StudentInputPort {

    @Autowired
    @Qualifier("studentRepositoryImp")
    ParentEntityRepository parentEntityRepository;

    @Override
    public Student createStudent(Student student) throws HandledException {
        return parentEntityRepository.save(student);
    }

    @Override
    public Student getStudentById(Long idStudent) throws HandledException {
        return parentEntityRepository.getById(String.valueOf(idStudent));
    }

    @Override
    public List<Student> getAllStudents() throws HandledException {
        return parentEntityRepository.getAll();
    }

    @Override
    public Student updateStudentById(Long idStudent, Student student) throws HandledException {
        return parentEntityRepository.updateById(String.valueOf(idStudent), student);
    }

    @Override
    public void deleteStudentById(Long idStudent) throws HandledException {
        parentEntityRepository.deleteById(String.valueOf(idStudent));
    }
}
