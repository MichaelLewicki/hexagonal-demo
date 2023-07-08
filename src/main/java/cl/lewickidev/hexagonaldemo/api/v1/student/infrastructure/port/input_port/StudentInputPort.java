package cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.port.input_port;

import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;

import java.util.List;

//inferfaz de service
public interface StudentInputPort {

    public Student createStudent(Student student) throws HandledException;

    public Student getStudentById(Long idStudent) throws HandledException;

    public List<Student> getAllStudents() throws HandledException;

    public Student updateStudentById(Long idStudent,Student student) throws HandledException;

    public void deleteStudentById(Long idStudent) throws HandledException;

}
