package cl.lewickidev.hexagonaldemo.api.v1.student;

import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.adapter.input_adapter.rest.imp.StudentControllerImp;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.port.input_port.StudentInputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    StudentInputPort studentInputPort;

    @InjectMocks
    StudentControllerImp studentController;

    @Test
    public void testSaveStudent() throws HandledException {
        // given
        Student student = new Student();
        student.setIdStudent(1L);
        student.setFirstName("John");

        when(studentInputPort.createStudent(student)).thenReturn(student);

        // when
        ResponseEntity<?> responseEntity = studentController.save(student);

        // then
        verify(studentInputPort, times(1)).createStudent(student);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testGetStudentById() throws HandledException {
        // given
        Long studentId = 1L;
        Student student = new Student();
        student.setIdStudent(studentId);
        student.setFirstName("John");
        when(studentInputPort.getStudentById(studentId)).thenReturn(student);

        // when
        ResponseEntity<?> responseEntity = studentController.getById(studentId);

        // then
        verify(studentInputPort, times(1)).getStudentById(studentId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllStudents() throws HandledException {
        // given
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setIdStudent(1L);
        student1.setFirstName("John");
        students.add(student1);

        Student student2 = new Student();
        student2.setIdStudent(2L);
        student2.setFirstName("Jane");
        students.add(student2);

        when(studentInputPort.getAllStudents()).thenReturn(students);

        // when
        ResponseEntity<?> responseEntity = studentController.getAll();

        // then
        verify(studentInputPort, times(1)).getAllStudents();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateStudentById() throws HandledException {
        // given
        Long studentId = 1L;
        Student student = new Student();
        student.setIdStudent(studentId);
        student.setFirstName("Robert");

        when(studentInputPort.updateStudentById(studentId, student)).thenReturn(student);

        // when
        ResponseEntity<?> responseEntity = studentController.updateById(studentId, student);

        // then
        verify(studentInputPort, times(1)).updateStudentById(studentId, student);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteStudentById() throws HandledException {
        // given
        Long studentId = 1L;

        // when
        ResponseEntity<?> responseEntity = studentController.deleteById(studentId);

        // then
        verify(studentInputPort, times(1)).deleteStudentById(studentId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
