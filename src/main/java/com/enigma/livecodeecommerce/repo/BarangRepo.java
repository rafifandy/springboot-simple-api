package com.enigma.livecodeecommerce.repo;

import com.enigma.livecodeecommerce.model.entity.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BarangRepo extends JpaRepository<Barang, Long>, JpaSpecificationExecutor<Barang> {

}

