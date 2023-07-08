package cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.adapter.input_adapter.rest;

import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface StudentController {

    @RequestMapping(value = "api/v1/hexagonal/student", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Student student);

    @RequestMapping(value = "api/v1/hexagonal/student/{idStudent}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long idStudent);

    @RequestMapping(value = "api/v1/hexagonal/student", method = RequestMethod.GET)
    public ResponseEntity<?> getAll();

    @RequestMapping(value = "api/v1/hexagonal/student/{idStudent}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateById(@PathVariable Long idStudent, @RequestBody Student student);

    @RequestMapping(value = "api/v1/hexagonal/student/{idStudent}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable Long idStudent);

}
