package cl.lewickidev.hexagonaldemo.api.v1.shared.domain.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO {

    private String code;
    private String message;

    public ErrorResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
