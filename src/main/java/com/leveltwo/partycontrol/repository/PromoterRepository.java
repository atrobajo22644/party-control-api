package com.leveltwo.partycontrol.repository;

import com.leveltwo.partycontrol.model.Promoter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoterRepository extends JpaRepository<Promoter, Long> {
    List<Promoter> findByActive(boolean isActive);

    Page<Promoter> findByNameContaining(String filter, Pageable pageable);
}
