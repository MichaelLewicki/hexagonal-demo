package cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.adapter.input_adapter.imp;

import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.adapter.input_adapter.ExamController;
import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.input_port.ExamInputPort;
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
public class ExamComtrollerImp implements ExamController {

    @Autowired
    ExamInputPort examInputPort;

    public ResponseEntity<?> saveByCourseId(String idCourse, Exam exam) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Exam examCreated = examInputPort.createExamByCourse(idCourse, exam);
            if (examCreated != null) {
                Wrapper result = new Wrapper();
                result.setData(examCreated);
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

    public ResponseEntity<?> getById(Long idExam) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Exam examFound = examInputPort.getExamById(idExam);
            if (examFound != null) {
                Wrapper result = new Wrapper();
                result.setData(examFound);
                result.setHttpCode(200);
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

    public ResponseEntity<?> getAll() {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            List<Exam> examsListFound = examInputPort.getAllExams();
            if (examsListFound != null) {
                Wrapper result = new Wrapper();
                result.setData(examsListFound);
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

    public ResponseEntity<?> updateById(Long idExam, Exam exam) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            Exam examModified = examInputPort.updateExamById(idExam, exam);
            if (examModified != null) {
                Wrapper result = new Wrapper();
                result.setData(examModified);
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

    public ResponseEntity<?> deleteById(Long idExam) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
        try {
            examInputPort.deleteExamById(idExam);
            Wrapper result = new Wrapper();
            result.setData(new ResponseDTO("Examen eliminado."));
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
