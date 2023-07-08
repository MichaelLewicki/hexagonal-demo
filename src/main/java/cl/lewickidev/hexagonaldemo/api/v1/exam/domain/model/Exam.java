package cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.Digits;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exam {

    private Long idExam;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;
    private String courseUnit;
    @Digits(integer=1, fraction=2)
    private Double grade;

    @JsonIgnoreProperties({"exams", "students"})
    private Course course;

    @JsonIgnoreProperties({"exams", "courses"})
    private Student student;

}
