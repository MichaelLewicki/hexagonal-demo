package cl.lewickidev.hexagonaldemo.api.v1.course;

import cl.lewickidev.hexagonaldemo.api.v1.course.aplication.usecase.CourseUseCase;
import cl.lewickidev.hexagonaldemo.api.v1.course.domain.model.Course;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //con esto verificamos que los test se inicien con mockito
public class CourseUseCaseTest {

    // Crear objetos simulados o "mocks" de las dependencias del objeto que se está probando
    // y necesita para su funcionamiento.
    @Mock
    private ChildEntityRepository childEntityRepository;

    // Inyectar los objetos simulados creados con @Mock en la clase que se está probando.
    // (Sirve para simular las dependencias y probar la lógica de la clase bajo prueba.)
    @InjectMocks
    private CourseUseCase courseUseCase;

    @DisplayName("Dado un curso que queremos crear " +
            "Cuando llamamos a 'createCourse' " +
            "Esperamos que el curso se haya creado ")
    @Test
    public void createCourseTest() throws HandledException {
        // Preparar test objects
        Course courseSimulado = new Course(null, "Maths", null, null);
        Course courseEsperado = new Course(UUID.randomUUID().toString(), "Maths", Collections.singletonList(new Student(1L)), null);
        // simulamos el comportamiento del repositorio usando When y el valor esperado con ThenReturn
        when(childEntityRepository.saveByParentId(eq(courseSimulado), eq("1"))).thenReturn(courseEsperado);
        // ejecutamos el método al que le deseamos hacer la prueba
        final Course result = courseUseCase.createCourse(courseSimulado, 1L);
        // verificamos que el método del repo haya sido llamado correctamente
        verify(childEntityRepository).saveByParentId(eq(courseSimulado), eq("1"));
        // verificamos los resultados esperados
        assertNotNull(result.getIdCourse());
        assertNotSame(courseSimulado, result);
        assertEquals(courseEsperado, result);
    }

    @DisplayName("Dado un curso que queremos obtener, " +
            "Cuando llamamos a 'getCourseById', " +
            "Esperamos que el curso sea retornado. "
    )
    @Test
    public void getCourseByIdTest () throws HandledException {
        String randomId = UUID.randomUUID().toString();
        Course courseEsperado = new Course(randomId, "Maths", null, null);
        when(childEntityRepository.getById(randomId)).thenReturn(courseEsperado);
        final Course result = courseUseCase.getCourseById(randomId);
        verify(childEntityRepository).getById(randomId);
        assertEquals(courseEsperado, result);
    }

    @DisplayName("Dado que queremos obtener todos los cursos, " +
            "Cuando llamamos a 'getAllCourses', " +
            "Esperamos que todos los cursos sean retornados. "
    )
    @Test
    public void getAllCoursesTest () throws HandledException {
        // Preparar objetos simulados
        Course courseEsperado = new Course("1", "Matemáticas", null, null);
        List<Course> listaEsperada = Collections.singletonList(courseEsperado);
        // Simular el comportamiento del repositorio
        when(childEntityRepository.getAll()).thenReturn(Collections.singletonList(courseEsperado));
        // Ejecutar el método que se está probando
        final List<Course> result = courseUseCase.getAllCourses();
        // Verificar el comportamiento esperado
        verify(childEntityRepository).getAll();
        assertEquals(listaEsperada, result);
    }

    @DisplayName("Dado un curso existente que queremos actualizar, " +
            "Cuando llamamos a 'updateById', " +
            "Esperamos que el curso se haya actualizado. ")
    @Test
    public void updateByIdTest() throws HandledException {
        // Preparar test objects
        String randomId = UUID.randomUUID().toString();
        Course courseSimulado = new Course(randomId, "Maths", null, null);
        Course courseActualizado = new Course(randomId, "Physics", null, null);

        // Simular el comportamiento del repositorio
        when(childEntityRepository.getById(randomId)).thenReturn(courseSimulado);
        when(childEntityRepository.updateById(randomId, courseSimulado)).thenReturn(courseActualizado);

        // Ejecutar el método que se está probando
        final Course courseSimuladoResult = courseUseCase.getCourseById(randomId);
        final Course result = courseUseCase.updateCourseById(randomId, courseSimuladoResult);

        // Verificar el comportamiento esperado
        verify(childEntityRepository).getById(randomId);
        verify(childEntityRepository).updateById(randomId, courseSimulado);
        assertEquals(courseActualizado, result);
    }

    @DisplayName("Dado un curso existente que queremos eliminar, " +
            "Cuando llamamos a 'deleteById', " +
            "Esperamos que el curso se haya eliminado. ")
    @Test
    public void deleteByIdTest() throws HandledException {
        // Preparar test objects
        String randomId = UUID.randomUUID().toString();
        // Ejecutar el método que se está probando
        courseUseCase.deleteCourseById(randomId);
        // Verificar el comportamiento esperado
        verify(childEntityRepository).deleteById(randomId);
    }

}