package cl.lewickidev.hexagonaldemo.api.v1.exam.infrastructure.input_port;

import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;

import java.util.List;

public interface ExamInputPort {

    public Exam createExamByCourse(String idCourse, Exam exam) throws HandledException;

    public Exam getExamById(Long idExam) throws HandledException;

    public List<Exam> getAllExams() throws HandledException;

    public Exam updateExamById(Long idExam, Exam exam) throws HandledException;

    public void deleteExamById(Long idExam) throws HandledException;

}
