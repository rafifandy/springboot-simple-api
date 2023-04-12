package com.enigma.livecodeecommerce.model.request;

import com.enigma.livecodeecommerce.model.entity.Barang;
import com.enigma.livecodeecommerce.model.entity.Kategori;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class HargaRequest {

    @NotNull(message = "Price is required")
    private Double price;
    private Barang barang;
}
