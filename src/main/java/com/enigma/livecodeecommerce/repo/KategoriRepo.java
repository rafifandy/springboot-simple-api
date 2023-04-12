package com.enigma.livecodeecommerce.repo;

import com.enigma.livecodeecommerce.model.entity.Kategori;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface KategoriRepo extends JpaRepository<Kategori, Long>, JpaSpecificationExecutor<Kategori> {
}

