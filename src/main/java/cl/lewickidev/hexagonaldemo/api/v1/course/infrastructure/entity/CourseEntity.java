package cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.entity;

import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.entity.ExamEntity;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.entity.StudentEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COURSE")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

    @Id
    @Column(name = "ID_COURSE", insertable = false)
    private String idCourse;
    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "COURSE_STUDENT",
            joinColumns = @JoinColumn(name = "ID_COURSE"),
            inverseJoinColumns = @JoinColumn(name = "ID_STUDENT"))
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnoreProperties({"courses", "exams"})
    private List<StudentEntity> students = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnoreProperties({"course"})
    private List<ExamEntity> exams = new ArrayList<>();

}
