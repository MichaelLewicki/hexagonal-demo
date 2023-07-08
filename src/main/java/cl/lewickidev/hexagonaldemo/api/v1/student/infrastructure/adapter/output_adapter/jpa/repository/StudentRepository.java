package cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.adapter.output_adapter.jpa.repository;

import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("studentRepository")
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

}
