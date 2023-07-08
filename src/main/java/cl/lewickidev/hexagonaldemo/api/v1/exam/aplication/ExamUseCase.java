package cl.lewickidev.hexagonaldemo.api.v1.exam.aplication;

import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.input_port.ExamInputPort;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port.ChildEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExamUseCase implements ExamInputPort {

    @Autowired
    @Qualifier("examRepositoryImp")
    ChildEntityRepository childEntityRepository;

    @Override
    public Exam createExamByCourse(String idCourse, Exam exam) throws HandledException {
       return childEntityRepository.saveByParentId(exam, idCourse);
    }

    @Override
    public Exam getExamById(Long idExam) throws HandledException {
        return childEntityRepository.getById(String.valueOf(idExam));
    }

    @Override
    public List<Exam> getAllExams() throws HandledException {
        return childEntityRepository.getAll();
    }

    @Override
    public Exam updateExamById(Long idExam, Exam exam) throws HandledException {
        return childEntityRepository.updateById(String.valueOf(idExam), exam);
    }

    @Override
    public void deleteExamById(Long idExam) throws HandledException {
        childEntityRepository.deleteById(String.valueOf(idExam));
    }
}
