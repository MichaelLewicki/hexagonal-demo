package cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.adapter.input_adapter;

import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ExamController {

    @RequestMapping(value = "api/v1/hexagonal/exam/{idCourse}", method = RequestMethod.POST)
    public ResponseEntity<?> saveByCourseId(@PathVariable String idCourse, @RequestBody Exam exam);

    @RequestMapping(value = "api/v1/hexagonal/exam/{idExam}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long idExam);

    @RequestMapping(value = "api/v1/hexagonal/exam", method = RequestMethod.GET)
    public ResponseEntity<?> getAll();

    @RequestMapping(value = "api/v1/hexagonal/exam/{idExam}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateById(@PathVariable Long idExam, @RequestBody Exam exam);

    @RequestMapping(value = "api/v1/hexagonal/exam/{idExam}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable Long idExam);

}
