package com.leveltwo.partycontrol.payload.request;

public record UpdateEventPromoterRequest(Integer ladies, Integer guys, Integer freeLadies, Integer vip, Boolean paid) {
}
