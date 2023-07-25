package com.leveltwo.partycontrol.payload.request;

import java.time.LocalDate;
import java.util.List;

public record CreateEventRequest(String name, LocalDate eventDate, boolean open, List<Long> promoters) {
}
