package com.enigma.livecodeecommerce.model.request;

import com.enigma.livecodeecommerce.model.entity.DetailPenjualan;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;

@Setter
@Getter
public class PenjualanRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    private Set<DetailPenjualan> detailPenjualan;
}
