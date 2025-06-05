package com.aashray.backend.repository;

import com.aashray.backend.model.BinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinRepository extends JpaRepository<BinEntity, Long> {
}
