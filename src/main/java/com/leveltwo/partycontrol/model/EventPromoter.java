package com.leveltwo.partycontrol.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;

@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@Entity
@Table(name = "event_promoter")
public class
EventPromoter {
    @JsonIgnore
    @EmbeddedId
    private EventPromoterId id;

    @MapsId("eventId")
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @MapsId("promoterId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Promoter promoter;

    @Builder.Default
    private Integer ladies = 0;

    @Builder.Default
    private Integer guys = 0;

    @Builder.Default
    @Column(name = "free_ladies")
    private Integer freeLadies = 0;

    @Builder.Default
    private Integer vip = 0;

    @Builder.Default
    private boolean paid = false;

    public EventPromoter(Event event, Promoter promoter) {
        this.event = event;
        this.promoter = promoter;
        this.id = new EventPromoterId(event.getId(), promoter.getId());
    }
}
