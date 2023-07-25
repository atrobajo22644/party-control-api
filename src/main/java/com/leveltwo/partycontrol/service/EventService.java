package com.leveltwo.partycontrol.service;

import com.leveltwo.partycontrol.model.Event;
import com.leveltwo.partycontrol.payload.request.CreateEventRequest;
import com.leveltwo.partycontrol.payload.request.UpdateEventPromoterRequest;
import com.leveltwo.partycontrol.payload.request.UpdateEventRequest;
import com.leveltwo.partycontrol.payload.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {

    Page<Event> getAllEvents(Pageable pageable);

    Page<Event> getEventsPage(String filter, Pageable pageable);

    ResponseEntity<Event> getEvent(Long id);

    ResponseEntity<Event> getLastOpenedEvent();

    ResponseEntity<Event> createEvent(CreateEventRequest createEventRequest);

    ResponseEntity<Event> updateEvent(Long id, UpdateEventRequest updateEventRequest);

    ResponseEntity<ApiResponse> deleteEvent(Long id);

    ResponseEntity<ApiResponse> closeEvent(Long id);

    ResponseEntity<Event> addPromoterToEvent(Long eventId, Long promoterId);

    ResponseEntity<Event> removePromoterFromEvent(Long eventId, Long promoterId);

    ResponseEntity<Event> updateEventPromoter(Long eventId, Long promoterId, UpdateEventPromoterRequest request);
}
