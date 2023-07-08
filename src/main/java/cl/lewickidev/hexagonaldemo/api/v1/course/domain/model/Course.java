package cl.lewickidev.hexagonaldemo.api.v1.course.domain.model;

import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private String idCourse;
    private String name;

    @JsonIgnoreProperties({"courses", "exams"})
    private List<Student> students = new ArrayList<>();

    @JsonIgnoreProperties({"courses"})
    private List<Exam> exams = new ArrayList<>();

    public Course(String idCourse, String name) {
        this.idCourse = idCourse;
        this.name = name;
    }

}
