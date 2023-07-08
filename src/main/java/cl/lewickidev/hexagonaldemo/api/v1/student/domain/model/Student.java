package cl.lewickidev.hexagonaldemo.api.v1.student.domain.model;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Long idStudent;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;

    @JsonIgnoreProperties({"students", "exams"}) //esto está funcionando OK y no hubo que agregar JSON IGNORE
    private List<Course> courses = new ArrayList<>();

    @JsonIgnoreProperties({"student"})
    private List<Exam> exams = new ArrayList<>();

    public Student(Long idStudent) {
        this.idStudent = idStudent;
    }

}
