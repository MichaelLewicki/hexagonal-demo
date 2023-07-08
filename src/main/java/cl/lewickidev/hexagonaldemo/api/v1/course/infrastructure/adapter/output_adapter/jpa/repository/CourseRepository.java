package cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.adapter.output_adapter.jpa.repository;

import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.entity.CourseEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("courseRepository")
public interface CourseRepository extends JpaRepository<CourseEntity, String>  {



}