package cl.lewickidev.hexagonaldemo.api.v1.student;

import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;
import cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port.ParentEntityRepository;
import cl.lewickidev.hexagonaldemo.api.v1.student.aplication.usecase.StudentUseCase;
import cl.lewickidev.hexagonaldemo.api.v1.student.domain.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentUseCaseTest {

    @Mock
    private ParentEntityRepository parentEntityRepository;

    @InjectMocks
    private StudentUseCase studentUseCase;

    @DisplayName("Dado un estudiante que queremos crear, " +
            "Cuando llamamos a 'createStudent', " +
            "Esperamos que el estudiante se haya creado. ")
    @Test
    public void createStudentTest() throws HandledException {
        // Preparar test objects
        Student studentSimulado = new Student(null, "Horacio", "Andrés", "González", "Contreras", null, null);
        Student studentEsperado = new Student(1L, "Horacio", "Andrés", "González", "Contreras", null, null);
        // simulamos el comportamiento del repositorio usando When y el valor esperado con ThenReturn
        when(parentEntityRepository.save(eq(studentSimulado))).thenReturn(studentEsperado);

        // ejecutamos el método al que le deseamos hacer la prueba
        final Student result = studentUseCase.createStudent(studentSimulado);

        // verificamos que el método del repo haya sido llamado correctamente
        verify(parentEntityRepository).save(eq(studentSimulado));

        // verificamos los resultados esperados
        assertNotNull(result.getIdStudent());
        assertNotSame(studentSimulado, result);
        assertEquals(studentEsperado, result);
    }

    @DisplayName("Dado un estudiante que queremos obtener, " +
            "Cuando llamamos a 'getStudentById', " +
            "Esperamos que el estudiante sea retornado. "
    )
    @Test
    public void getCourseByIdTest () throws HandledException {
        Long idStudent = 1L;
        Student studentEsperado = new Student(idStudent, "Horacio", "Andrés", "González", "Contreras", null, null);

        when(parentEntityRepository.getById("1")).thenReturn(studentEsperado);

        final Student result = studentUseCase.getStudentById(idStudent);

        verify(parentEntityRepository).getById("1");

        assertEquals(studentEsperado, result);
    }

    @DisplayName("Dado que queremos obtener todos los estudiantes, " +
            "Cuando llamamos a 'getAllStudents', " +
            "Esperamos que todos los estudiantes sean retornados. "
    )
    @Test
    public void getAllCoursesTest () throws HandledException {
        Long idStudent = 1L;
        // Preparar objetos simulados
        Student studentEsperado = new Student(idStudent, "Horacio", "Andrés", "González", "Contreras", null, null);
        List<Student> listaEsperada = Collections.singletonList(studentEsperado);
        // Simular el comportamiento del repositorio
        when(parentEntityRepository.getAll()).thenReturn(Collections.singletonList(studentEsperado));
        // Ejecutar el método que se está probando
        final List<Student> result = studentUseCase.getAllStudents();
        // Verificar el comportamiento esperado
        verify(parentEntityRepository).getAll();
        assertEquals(listaEsperada, result);
    }

    @DisplayName("Dado un estudiante existente que queremos actualizar, " +
            "Cuando llamamos a 'updateById', " +
            "Esperamos que el estudiante se haya actualizado. ")
    @Test
    public void updateByIdTest() throws HandledException {
        // Preparar test objects
        Long idStudent = 1L;
        Student studentSimulado = new Student(idStudent, "Horacio", "Andrés", "González", "Contreras", null, null);
        Student studentModificado = new Student(idStudent, "Guillermo", "Martín", "Fernandez", "Tapia", null, null);
        Student studentEsperado = new Student(idStudent, "Guillermo", "Martín", "Fernandez", "Tapia", null, null);

        // Simular el comportamiento del repositorio
        when(parentEntityRepository.getById("1")).thenReturn(studentSimulado);
        when(parentEntityRepository.updateById(eq("1"), eq(studentModificado))).thenReturn(studentEsperado);

        // Ejecutar el método que se está probando
        final Student studentSimuladoResult = studentUseCase.getStudentById(1L);
        final Student result = studentUseCase.updateStudentById(idStudent, studentModificado);


        // Verificar el comportamiento esperado
        verify(parentEntityRepository).getById("1");
        verify(parentEntityRepository).updateById("1", studentModificado);
        assertEquals(studentEsperado, result);
    }

    @DisplayName("Dado un estudiante existente que queremos eliminar, " +
            "Cuando llamamos a 'deleteById', " +
            "Esperamos que el estudiante se haya eliminado. ")
    @Test
    public void deleteByIdTest() throws HandledException {
        // Preparar test objects
        String randomId = UUID.randomUUID().toString();
        Long idStudent = 1L;
        // Ejecutar el método que se está probando
        studentUseCase.deleteStudentById(idStudent);
        // Verificar el comportamiento esperado
        verify(parentEntityRepository).deleteById(idStudent.toString());
    }

}
