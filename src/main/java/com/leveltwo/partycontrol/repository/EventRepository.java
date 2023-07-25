package com.leveltwo.partycontrol.repository;

import com.leveltwo.partycontrol.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByNameContaining(String name, Pageable pageable);

    Event findFirstByOpenTrueOrderByEventDateDesc();
}
