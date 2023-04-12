package com.enigma.livecodeecommerce.repo;

import com.enigma.livecodeecommerce.model.entity.Harga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HargaRepo extends JpaRepository<Harga, Long>, JpaSpecificationExecutor<Harga> {
}

