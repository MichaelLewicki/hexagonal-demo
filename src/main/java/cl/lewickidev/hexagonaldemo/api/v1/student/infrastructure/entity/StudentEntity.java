package cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.entity;

import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.entity.CourseEntity;
import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.entity.ExamEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "STUDENT")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTUDENT", insertable = false)
    private Long idStudent;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "MIDDLENAME")
    private String middleName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "SECOND_LASTNAME")
    private String secondLastName;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "students")
    @JsonIgnoreProperties({"students", "exams"})
    private List<CourseEntity> courses = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnoreProperties({"student"})
    private List<ExamEntity> exams = new ArrayList<>();

}
