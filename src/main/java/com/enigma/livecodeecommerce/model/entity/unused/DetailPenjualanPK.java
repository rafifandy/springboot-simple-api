package com.enigma.livecodeecommerce.model.entity.unused;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DetailPenjualanPK implements Serializable {
    @Column(name = "penjualan_id")
    private Long penjualan_id;
    @Column(name = "harga_id")
    private Long harga_id;
}
