package com.enigma.livecodeecommerce.model.request;

import com.enigma.livecodeecommerce.model.entity.Kategori;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class BarangRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private Set<Kategori> kategori;


    private Double stock;
}
