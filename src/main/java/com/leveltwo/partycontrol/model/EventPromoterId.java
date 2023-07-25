package com.leveltwo.partycontrol.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EventPromoterId {
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "promoter_id")
    private Long promoterId;
}
