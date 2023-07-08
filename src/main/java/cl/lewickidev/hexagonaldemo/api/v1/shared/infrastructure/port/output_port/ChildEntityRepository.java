package cl.lewickidev.hexagonaldemo.api.v1.shared.infrastructure.port.output_port;

import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils.HandledException;

import java.util.List;

//Responsabilidad: Guardar entidades en base a entidades padre
public interface ChildEntityRepository {

    public <T> T saveByParentId( T object, String idParent ) throws HandledException;

    public <T> T getById( String id ) throws HandledException;

    public <T> List<T> getAll() throws HandledException;

    public <T> T updateById( String id, T object ) throws HandledException;

    public void deleteById ( String id ) throws HandledException;

}
