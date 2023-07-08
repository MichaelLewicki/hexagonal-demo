package cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.adapter.input_adapter.rest.imp;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.adapter.input_adapter.rest.CourseController;
import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.port.input_port.CourseInputPort;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.dto.ResponseDTO;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Constant;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Errors;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CourseControllerImp implements CourseController {

    @Autowired
    CourseInputPort courseInputPort;

    public ResponseEntity<?> save(Course course, Long idStudent) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Course courseCreated = courseInputPort.createCourse(course, idStudent);
            if (courseCreated != null) {
                Wrapper result = new Wrapper();
                result.setData(courseCreated);
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

    public ResponseEntity<?> getById(String idCourse) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Course courseFound = courseInputPort.getCourseById(idCourse);
            if (courseFound != null) {
                Wrapper result = new Wrapper();
                result.setData(courseFound);
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
            List<Course> courseList = courseInputPort.getAllCourses();
            if (courseList != null) {
                Wrapper result = new Wrapper();
                result.setData(courseList);
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

    public ResponseEntity<?> updateById(String idCourse, Course course) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Course courseModified = courseInputPort.updateCourseById(idCourse, course);
            if (courseModified != null) {
                Wrapper result = new Wrapper();
                result.setData(courseModified);
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

    public ResponseEntity<?> deleteById(String idCourse) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            courseInputPort.deleteCourseById(idCourse);
            Wrapper result = new Wrapper();
            result.setData(new ResponseDTO("Course eliminado."));
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
