package cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.entity;

import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.entity.CourseEntity;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.entity.StudentEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "EXAM")
public class ExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EXAM", insertable = false)
    private Long idExam;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @UpdateTimestamp
    private Date date;
    @Column(name = "COURSEUNIT")
    private String courseUnit;
    @Column(name = "GRADE", precision = 1, scale = 2)
    @Digits(integer=1, fraction=2)
    @Max(7) @Min(1)
    private Double grade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_COURSE")
    @JsonIgnoreProperties({"exams", "students"})
    private CourseEntity course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_STUDENT")
    @JsonIgnoreProperties({"exams", "courses"})
    private StudentEntity student;

}
