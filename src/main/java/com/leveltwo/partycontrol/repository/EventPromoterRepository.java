package com.leveltwo.partycontrol.repository;

import com.leveltwo.partycontrol.model.EventPromoter;
import com.leveltwo.partycontrol.model.EventPromoterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPromoterRepository extends JpaRepository<EventPromoter, EventPromoterId> {
}
