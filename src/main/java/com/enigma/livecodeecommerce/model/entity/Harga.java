package com.enigma.livecodeecommerce.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "harga")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Harga implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "barang_id")
    private Barang barang;

    @OneToMany(mappedBy = "harga")
    @JsonBackReference
    private List<DetailPenjualan> detailPenjualan;



//    @ManyToMany
//    @JoinTable(
//            name=("detail_penjualan"),
//            joinColumns=@JoinColumn(name="harga_id")
//    )
//    private Set<DetailPenjualan> detailPenjualan;

//    @OneToMany(mappedBy = "harga")
//    @JsonBackReference
//    private Set<DetailPenjualan> detailPenjualan;

}
