package cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.adapter.output_adapter.jpa.repository;

import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {

}
