package com.enigma.livecodeecommerce.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KategoriRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
