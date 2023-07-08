package cl.lewickidev.hexagonaldemo.api.v1.shared.domain.utils;

import cl.lewickidev.hexagonaldemo.api.v1.shared.domain.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class Errors {

    // Codigos
    public static final String DUPLICATED = "DUPLICATED_EXCEPTION";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String DELETED = "DELETED";
    public static final String CONFLICT = "CONFLICT";
    public static final String NO_CONTENT = "NO_CONTENT";

    // Mensajes
    public static final String IDENTITY_NOT_FOUND = "Identificador %s no encontrado";
    public static final String IDENTITY_DUPLICATED = "Identificador %s ya existe";
    public static final String IDENTITY_DELETED = "Identificador %s se encuentra borrado";
    public static final String IDENTITY_NOT_FOUND_IN = "Identificador %s no encontrado en %s";
    public static final String IDENTITY_FOUND_IN = "Identificador %s fue encontrado en %s";
    public static final String FIELD_INVALID = "El campo %s no es correcto";
    public static final String FIELD_MANDATORY = "El campo %s es requerido";
    public static final String FIELD_ID_NOT_REQUIRED = "El campo %s no debe ser incluido ya que es autogenerado por el sistema";
    public static final String COMPOSE_KEYS_DUPLICATED = "Identificador de aplicación %s y módulo %s ya existen.";


    public static String messageIdNotFound(String id) {
        return String.format(IDENTITY_NOT_FOUND, id);
    }

    public static String messageIdNotFound(Long id) {
        return String.format(IDENTITY_NOT_FOUND, String.valueOf(id));
    }

    public static String messageIdNotFoundIn(String in, Long id) {
        return String.format(IDENTITY_NOT_FOUND_IN, String.valueOf(id), in);
    }

    public static String messageIdNotFoundIn(Long in, String id) {
        return String.format(IDENTITY_NOT_FOUND_IN, String.valueOf(id), String.valueOf(in));
    }

    public static String messageIdNotFoundIn(Long in, Long id) {
        return String.format(IDENTITY_NOT_FOUND_IN, String.valueOf(id), String.valueOf(in));
    }

    public static String messageIdFoundIn(String in, String id) {
        return String.format(IDENTITY_FOUND_IN, String.valueOf(id), String.valueOf(in));
    }

    public static String messageIdFoundIn(Long in, String id) {
        return String.format(IDENTITY_FOUND_IN, String.valueOf(id), String.valueOf(in));
    }

    public static String messageIdFoundIn(Long in, Long id) {
        return String.format(IDENTITY_FOUND_IN, String.valueOf(id), String.valueOf(in));
    }

    public static String messageIdDuplicated(String id) {
        return String.format(IDENTITY_DUPLICATED, id);
    }

    public static String messageIdDuplicated(Long id) {
        return String.format(IDENTITY_DUPLICATED, String.valueOf(id));
    }

    public static String messageIdDuplicated(UUID id) {
        return String.format(IDENTITY_DUPLICATED, String.valueOf(id));
    }

    public static String messageFieldInvalid(String field) {
        return String.format(FIELD_INVALID, String.valueOf(field));
    }

    public static String messageIdNotRequired(String field) {
        return String.format(FIELD_ID_NOT_REQUIRED, String.valueOf(field));
    }

    public static String messageFieldMandatory(String field) {
        return String.format(FIELD_MANDATORY, String.valueOf(field));
    }

    public static String messageNameAndCodeNotFound(String name, String code) {
        return String.format(IDENTITY_NOT_FOUND_IN, code, name);
    }

    public static String messageIdDeleted(String id) {
        return String.format(IDENTITY_DELETED, id);
    }

    public static String messageIdDeleted(Long id) {
        return String.format(IDENTITY_DELETED, String.valueOf(id));
    }

    public static String messageAppModuleExists(String app, String module) {
        return String.format(COMPOSE_KEYS_DUPLICATED, app, module);
    }
    /**
     * Metodo estatico para manejar la HandledException commun para todos los metodos de esta clase
     * @param e HandledException a manejar
     * @return devuelve un HttpReponse con la salida del error
     */
    public static ResponseEntity<?> handleException(HandledException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e.isCode(NOT_FOUND) || e.isCode(DELETED)) {
            status = HttpStatus.NOT_FOUND;
        } else if (e.isCode(DUPLICATED)) {
            status = HttpStatus.CONFLICT;
        } else if (e.isCode(BAD_REQUEST)) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e.isCode(CONFLICT)) {
            status = HttpStatus.CONFLICT;
        } else if (e.isCode(NO_CONTENT)) {
            status = HttpStatus.NO_CONTENT;
        }
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDTO(e.getCode(), e.getLocalizedMessage()));
    }

}
