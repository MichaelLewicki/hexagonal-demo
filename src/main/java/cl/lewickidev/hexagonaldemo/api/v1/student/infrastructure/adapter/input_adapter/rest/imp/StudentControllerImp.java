package cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.adapter.input_adapter.rest.imp;

import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.dto.ResponseDTO;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Constant;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Errors;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Wrapper;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.adapter.input_adapter.rest.StudentController;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.port.input_port.StudentInputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class StudentControllerImp implements StudentController {

    @Autowired
    StudentInputPort studentInputPort;

    public ResponseEntity<?> save(Student student) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Student studentCreated = studentInputPort.createStudent(student);
            if (studentCreated != null) {
                Wrapper result = new Wrapper();
                result.setData(studentCreated);
                result.setHttpCode(201);
                result.setDescripcion(Constant.CONSULTA_EXITOSA);
                response = ResponseEntity.status(HttpStatus.CREATED).body(result);
            }
        } catch (HandledException he) {
            he.printStackTrace();
            log.error(he.getMessage());
            response = Errors.handleException(he);
        }
        return response;
    }

    public ResponseEntity<?> getById(Long idStudent) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Student studentFound = studentInputPort.getStudentById(idStudent);
            if (studentFound != null) {
                Wrapper result = new Wrapper();
                result.setData(studentFound);
                result.setHttpCode(200);
                result.setDescripcion(Constant.CONSULTA_EXITOSA);
                response = ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (HandledException he) {
            he.printStackTrace();
            log.error(he.getMessage());
            response = Errors.handleException(he);
        }
        return response;
    }

    public ResponseEntity<?> getAll() {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            List<Student> studentListFound = studentInputPort.getAllStudents();
            if (studentListFound != null) {
                Wrapper result = new Wrapper();
                result.setData(studentListFound);
                result.setHttpCode(200);
                result.setDescripcion(Constant.CONSULTA_EXITOSA);
                response = ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (HandledException he) {
            he.printStackTrace();
            log.error(he.getMessage());
            response = Errors.handleException(he);
        }
        return response;
    }

    public ResponseEntity<?> updateById(Long idStudent, Student student) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Student studentModified = studentInputPort.updateStudentById(idStudent, student);
            if (studentModified != null) {
                Wrapper result = new Wrapper();
                result.setData(student);
                result.setHttpCode(200);
                result.setDescripcion(Constant.CONSULTA_EXITOSA);
                response = ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (HandledException he) {
            he.printStackTrace();
            log.error(he.getMessage());
            response = Errors.handleException(he);
        }
        return response;
    }

    public ResponseEntity<?> deleteById(Long idStudent) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            studentInputPort.deleteStudentById(idStudent);
            Wrapper result = new Wrapper();
            result.setData(new ResponseDTO("Estudiante eliminado."));
            result.setHttpCode(200);
            result.setDescripcion(Constant.CONSULTA_EXITOSA);
            response = ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (HandledException he) {
            he.printStackTrace();
            log.error(he.getMessage());
            response = Errors.handleException(he);
        }
        return response;
    }


}
