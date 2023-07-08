package cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.adapter.input_adapter.rest;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CourseController {

    @RequestMapping(value = "api/v1/hexagonal/course/{idStudent}", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Course course, @PathVariable Long idStudent);

    @RequestMapping(value = "api/v1/hexagonal/course/{idCourse}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String idCourse);

    @RequestMapping(value = "api/v1/hexagonal/course", method = RequestMethod.GET)
    public ResponseEntity<?> getAll();

    @RequestMapping(value = "api/v1/hexagonal/course/{idCourse}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateById(@PathVariable String idCourse, @RequestBody Course course);

    @RequestMapping(value = "api/v1/hexagonal/course/{idCourse}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable String idCourse);

}
