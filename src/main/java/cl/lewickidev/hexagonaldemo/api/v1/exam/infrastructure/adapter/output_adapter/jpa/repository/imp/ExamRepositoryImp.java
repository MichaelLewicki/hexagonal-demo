package cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.adapter.output_adapter.jpa.repository.imp;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.adapter.output_adapter.jpa.repository.CourseRepository;
import cl.lewickidev.hexagonaldemo.api.v1.course.infrastructure.entity.CourseEntity;
import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.adapter.output_adapter.jpa.repository.ExamRepository;
import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.entity.ExamEntity;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.Errors;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port.ChildEntityRepository;
import cl.lewickidev.hexagonaldemo.api.v1.student.infrastructure.entity.StudentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamRepositoryImp implements ChildEntityRepository {

    @Autowired
    @Lazy
    private ExamRepository examRepository;

    @Autowired
    @Lazy
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public <T> T saveByParentId(T object, String idParent) throws HandledException {
        try {
            CourseEntity courseEntityFound = courseRepository.findById(idParent)
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Course no encontrado asociado al ID: " + idParent));
            if (object instanceof Exam) {
                Exam exam = (Exam) object;
                if (exam.getIdExam() != null) {
                    throw new HandledException(Errors.BAD_REQUEST, "El campo 'id' no debe ser incluido en el payload del objeto Exam.");
                } else {
                    ExamEntity examEntity = new ExamEntity();
                    examEntity = mapToEntity(exam);
                    examEntity.setCourse(courseEntityFound);
                    ExamEntity examEntityCreated = examRepository.save(examEntity);
                    if (examEntityCreated != null) {
                        exam = mapToModel(examEntityCreated);
                        return (T) exam;
                    }
                    return null;
                }
            } else {
                throw new IllegalArgumentException("El objeto proporcionado no es un objeto de Exam válido");
            }
        } catch (HandledException he) {
            throw he;
        }
    }

    @Override
    public <T> T getById(String idExam) throws HandledException {
        try {
            ExamEntity examEntity = examRepository.findById(Long.valueOf(idExam))
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Exam no encontrado asociado al ID: " + idExam));
            Exam exam = mapToModel(examEntity);
            return (T) exam;
        } catch (HandledException he) {
            throw he;
        }
    }

    @Override
    public <T> List<T> getAll() throws HandledException {
        try {
            List<ExamEntity> examEntitiesList = examRepository.findAll();
            if (!examEntitiesList.isEmpty()) {
                List<Exam> examList = new ArrayList<>();
                for (ExamEntity examEntity : examEntitiesList) {
                    Exam exam = new Exam();
                    exam = mapToModel(examEntity);
                    examList.add(exam);
                }
                return (List<T>) examList;
            } else {
                throw new HandledException(Errors.NO_CONTENT, "No se han encontrado registros de Exams en la base de datos");
            }
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new HandledException(Errors.CONFLICT, "Se produjo un error al intentar acceder a los datos");
        }
    }

    @Override
    public <T> T updateById(String id, T object) throws HandledException {
        try {
            ExamEntity examEntityFound = examRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Exam no encontrado asociado al ID: " + id));
            if (object instanceof Exam) {
                Exam exam = (Exam) object;
                //guardar id de la entidad
                Long idExamEntityFound = examEntityFound.getIdExam();
                examEntityFound = mapToEntity(exam);
                examEntityFound.setIdExam(idExamEntityFound);

                ExamEntity examEntityModified = examRepository.save(examEntityFound);
                if (examEntityModified != null) {
                    //exam.setIdExam(idExamEntityFound);
                    //List<Student> studentList = new ArrayList<>();
                    //courseEntityFound.getStudents().forEach(studentEntity -> studentList.add(mapStudentToModel(studentEntity)));
                    //course.setStudents(studentList);
                    exam = mapToModel(examEntityModified);
                    return (T) exam;
                } else {
                    return null;
                }
            } else {
                throw new IllegalArgumentException("El objeto proporcionado no es un objeto de Exam válido");
            }
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new HandledException("500", e.getMessage());
        }

    }

    @Override
    public void deleteById(String id) throws HandledException {
        try {
            ExamEntity examEntity = examRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new HandledException(Errors.NOT_FOUND, "Exam no encontrado asociado al ID: " + id));
            //quitar relación con curso
            CourseEntity courseEntity = examEntity.getCourse();
            courseEntity.getExams().remove(examEntity);
            //quitar relación con Examen
            StudentEntity studentEntity = examEntity.getStudent();
            studentEntity.getExams().remove(examEntity);
            //borrar
            examRepository.delete(examEntity);
        } catch (HandledException he) {
            throw he;
        }
    }

    private Course mapToCourseModel(CourseEntity courseEntity) {
        Course course = modelMapper.map(courseEntity, Course.class);
        return course;
    }

    private Exam mapToModel(ExamEntity examEntity) {
        Exam exam = modelMapper.map(examEntity, Exam.class);
        return exam;
    }

    private ExamEntity mapToEntity(Exam exam) {
        ExamEntity examEntity = modelMapper.map(exam, ExamEntity.class);
        return examEntity;
    }

}
