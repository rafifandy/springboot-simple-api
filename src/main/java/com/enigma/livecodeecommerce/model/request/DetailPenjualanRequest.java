package com.enigma.livecodeecommerce.model.request;

import com.enigma.livecodeecommerce.model.entity.Harga;
import com.enigma.livecodeecommerce.model.entity.Kategori;
import com.enigma.livecodeecommerce.model.entity.Penjualan;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class DetailPenjualanRequest {
    private Double qty;
    private Harga harga;
    private Penjualan penjualan;
}
