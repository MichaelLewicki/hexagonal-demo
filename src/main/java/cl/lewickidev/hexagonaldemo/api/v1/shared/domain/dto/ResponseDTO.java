package cl.lewickidev.hexagonaldemo.api.v1.shared.domain.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    private final String message;

    public ResponseDTO(String message) {
        this.message = message;
    }

}
