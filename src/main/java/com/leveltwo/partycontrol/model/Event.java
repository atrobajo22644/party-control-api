package com.leveltwo.partycontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "vip_total")
    private Integer vipTotal = 0;

    @Builder.Default
    @Column(name = "free_ladies_total")
    private Integer freeLadiesTotal = 0;

    @Builder.Default
    @Column(name = "total_general_admission")
    private Integer totalGeneralAdmission = 0;

    @Builder.Default
    @Column(name = "total_over_21")
    private Integer totalOver21 = 0;

    @Builder.Default
    @DateTimeFormat(pattern="MM/dd/yyyy")
    @Column(name = "event_date")
    private LocalDate eventDate = LocalDate.now();

    @Builder.Default
    private boolean open = true;

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventPromoter> promoters = new ArrayList<>();

    public void addPromoter(Promoter promoter) {
        EventPromoter eventPromoter = EventPromoter.builder()
                .event(this).promoter(promoter)
                .id(new EventPromoterId(this.id, promoter.getId())).build();
        promoters.add(eventPromoter);
    }

    public void removePromoter(Promoter promoter) {
        for (Iterator<EventPromoter> iterator = promoters.iterator(); iterator.hasNext();) {
            EventPromoter eventPromoter = iterator.next();

            if (eventPromoter.getEvent().equals(this) && eventPromoter.getPromoter().equals(promoter)) {
                iterator.remove();
                eventPromoter.getEvent().getPromoters().remove(eventPromoter);
                eventPromoter.setEvent(null);
                eventPromoter.setPromoter(null);
            }
        }
    }
}
