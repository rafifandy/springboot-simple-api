package com.enigma.livecodeecommerce.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "detail_penjualan",uniqueConstraints={
        @UniqueConstraint(columnNames = {"penjualan_id", "harga_id"})
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DetailPenjualan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "penjualan_id")
    @JsonBackReference
    private Penjualan penjualan;
    @ManyToOne
    @JoinColumn(name = "harga_id")
    private Harga harga;
    @Column(nullable = false)
    private Double qty;

    private Double subTotal;

//    @EmbeddedId
//    private DetailPenjualanPK id;

//    @MapsId("penjualan_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "penjualan_id", insertable = false, updatable = false)
//    @JsonBackReference
//    private Penjualan penjualan;
//
//
//    @MapsId("harga_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "harga_id", insertable = false, updatable = false)
//    private Harga harga;

}
