package cl.lewickidev.hexagonaldemo.api.v1.exam;

import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
import cl.lewickidev.hexagonaldemo.api.v1.exam.aplication.ExamUseCase;
import cl.lewickidev.hexagonaldemo.api.v1.exam.domain.model.Exam;
import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port.ChildEntityRepository;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExamUseCaseTest {

    @Mock
    private ChildEntityRepository childEntityRepository;

    @InjectMocks
    private ExamUseCase examUseCase;

    @DisplayName("Dado un examen que queremos crear dentro de un curso" +
            "Cuando llamamos a 'createExamByCourse' " +
            "Esperamos que el examen se haya creado relacionado con un curso ")
    @Test
    public void createExamTest() throws HandledException {
        // Preparar test objects
        Exam examSimulado = new Exam(null, "Funciones", null, "Unidad1",6.7,null,null);
        Course courseSimulado = new Course(UUID.randomUUID().toString(), "Maths");
        Exam examEsperado = new Exam(1L, "Funciones", new Date(), "Unidad1",6.7, courseSimulado,null);

        // simulamos el comportamiento del repositorio usando When y el valor esperado con ThenReturn
        when(childEntityRepository.saveByParentId(eq(examSimulado), eq(courseSimulado.getIdCourse()))).thenReturn(examEsperado);

        // ejecutamos el método al que le deseamos hacer la prueba
        final Exam result = examUseCase.createExamByCourse(courseSimulado.getIdCourse(), examSimulado);

        // verificamos que el método del repo haya sido llamado correctamente
        verify(childEntityRepository).saveByParentId(eq(examSimulado), eq(courseSimulado.getIdCourse()));

        // verificamos los resultados esperados
        assertNotNull(result.getIdExam());
        assertNotSame(examSimulado, result);
        assertEquals(examEsperado, result);
    }

    @DisplayName("Dado un examen que queremos obtener, " +
            "Cuando llamamos a 'getExamById', " +
            "Esperamos que el examen sea retornado. "
    )
    @Test
    public void getExamByIdTest () throws HandledException {
        Course courseSimulado = new Course(UUID.randomUUID().toString(), "Maths");
        Long idExam = 1L;
        Exam examEsperado = new Exam(idExam, "Funciones", new Date(), "Unidad1",6.7, courseSimulado,null);

        when(childEntityRepository.getById(idExam.toString())).thenReturn(examEsperado);

        final Exam result = examUseCase.getExamById(idExam);

        verify(childEntityRepository).getById(idExam.toString());

        assertEquals(examEsperado, result);

    }



    @DisplayName("Dado que queremos obtener todos los exámenes, " +
            "Cuando llamamos a 'getAllExams', " +
            "Esperamos que todos los exámenes sean retornados. "
    )
    @Test
    public void getAllExamsTest () throws HandledException {
        // Preparar objetos simulados
        Course courseSimulado = new Course(UUID.randomUUID().toString(), "Maths");
        Long idExam = 1L;
        Exam examEsperado = new Exam(idExam, "Funciones", new Date(), "Unidad1",6.7, courseSimulado,null);
        List<Exam> listaEsperada = Collections.singletonList(examEsperado);
        // Simular el comportamiento del repositorio
        when(childEntityRepository.getAll()).thenReturn(Collections.singletonList(examEsperado));
        // Ejecutar el método que se está probando
        final List<Exam> result = examUseCase.getAllExams();
        // Verificar el comportamiento esperado
        verify(childEntityRepository).getAll();
        assertEquals(listaEsperada, result);
    }


    @DisplayName("Dado un examen existente que queremos actualizar, " +
            "Cuando llamamos a 'updateById', " +
            "Esperamos que el examen se haya actualizado. ")
    @Test
    public void updateExamByIdTest() throws HandledException {
        // Preparar test objects
        Long idExam = 1L;
        Course courseSimulado = new Course(UUID.randomUUID().toString(), "Maths");
        Student studentSimulado = new Student(1L, "Horacio", "Andrés", "González", "Contreras", null, null);
        Exam examEsperado = new Exam(idExam, "Funciones", new Date(), "Unidad1",6.7, courseSimulado,null);
        Exam examActualizado = new Exam(idExam, "Álgebra", new Date(), "Unidad2",5.3, courseSimulado, studentSimulado);

        // Simular el comportamiento del repositorio
        when(childEntityRepository.getById(idExam.toString())).thenReturn(examEsperado);
        when(childEntityRepository.updateById(idExam.toString(), examEsperado)).thenReturn(examActualizado);

        // Ejecutar el método que se está probando
        final Exam examSimuladoResult = examUseCase.getExamById(idExam);
        final Exam result = examUseCase.updateExamById(idExam, examSimuladoResult);

        // Verificar el comportamiento esperado
        verify(childEntityRepository).getById(idExam.toString());
        verify(childEntityRepository).updateById(idExam.toString(), examEsperado);
        assertEquals(examActualizado, result);
    }

    @DisplayName("Dado un examen existente que queremos eliminar, " +
            "Cuando llamamos a 'deleteById', " +
            "Esperamos que el examen se haya eliminado. ")
    @Test
    public void deleteExamByIdTest() throws HandledException {
        // Preparar test objects
        Long idExam = 1L;
        // Ejecutar el método que se está probando
        examUseCase.deleteExamById(idExam);
        // Verificar el comportamiento esperado
        verify(childEntityRepository).deleteById(idExam.toString());
    }

}
