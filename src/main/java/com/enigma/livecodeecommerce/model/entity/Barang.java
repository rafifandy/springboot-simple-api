package com.enigma.livecodeecommerce.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "barang")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Barang implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "kategori_barang",
            joinColumns = @JoinColumn(name = "barang_id"),
            inverseJoinColumns = @JoinColumn(name = "kategori_id")
    )
    private Set<Kategori> kategori;

    @Column
    private Double stock;


}
