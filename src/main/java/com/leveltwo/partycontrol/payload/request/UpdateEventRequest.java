package com.leveltwo.partycontrol.payload.request;

import java.time.LocalDate;
import java.util.List;

public record UpdateEventRequest(String name, LocalDate eventDate, boolean open, int vipTotal,
                                 int freeLadiesTotal, int totalGeneralAdmission, int totalOver21,
                                 List<Long> promoters) {
}
