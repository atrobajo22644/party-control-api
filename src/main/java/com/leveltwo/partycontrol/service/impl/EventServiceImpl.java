package com.leveltwo.partycontrol.service.impl;

import com.leveltwo.partycontrol.exception.ResourceNotFoundException;
import com.leveltwo.partycontrol.model.Event;
import com.leveltwo.partycontrol.model.EventPromoter;
import com.leveltwo.partycontrol.model.EventPromoterId;
import com.leveltwo.partycontrol.model.Promoter;
import com.leveltwo.partycontrol.payload.request.CreateEventRequest;
import com.leveltwo.partycontrol.payload.request.UpdateEventPromoterRequest;
import com.leveltwo.partycontrol.payload.request.UpdateEventRequest;
import com.leveltwo.partycontrol.payload.response.ApiResponse;
import com.leveltwo.partycontrol.repository.EventPromoterRepository;
import com.leveltwo.partycontrol.repository.EventRepository;
import com.leveltwo.partycontrol.repository.PromoterRepository;
import com.leveltwo.partycontrol.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.leveltwo.partycontrol.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final PromoterRepository promoterRepository;
    private final EventPromoterRepository eventPromoterRepository;

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Page<Event> getEventsPage(String filter, Pageable pageable) {
        return (filter == null) ? getAllEvents(pageable) :
                eventRepository.findByNameContaining(filter, pageable);
    }

    @Override
    public ResponseEntity<Event> getEvent(Long id) {
        return new ResponseEntity<>(getEventById(id), HttpStatus.OK);
    }

    public ResponseEntity<Event> getLastOpenedEvent() {
        return new ResponseEntity<>(this.eventRepository.findFirstByOpenTrueOrderByEventDateDesc(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Event> createEvent(CreateEventRequest createEventRequest) {
        Event newEvent = Event.builder()
                .name(createEventRequest.name())
                .eventDate(createEventRequest.eventDate())
                .open(createEventRequest.open())
                .build();
        createEventRequest.promoters().forEach((Long promoterId) -> {
            Promoter promoter = getPromoterById(promoterId);
            newEvent.addPromoter(promoter);
        });
        return new ResponseEntity<>(eventRepository.save(newEvent), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Event> updateEvent(Long id, UpdateEventRequest updateEventRequest) {
        Event event = getEventById(id);
        event.setName(updateEventRequest.name());
        event.setEventDate(updateEventRequest.eventDate());
        event.setOpen(updateEventRequest.open());
        event.setFreeLadiesTotal(updateEventRequest.freeLadiesTotal());
        event.setVipTotal(updateEventRequest.vipTotal());
        event.setTotalGeneralAdmission(updateEventRequest.totalGeneralAdmission());
        event.setTotalOver21(updateEventRequest.totalOver21());
        event.getPromoters().removeIf(eventPromoter -> !updateEventRequest.promoters().contains(eventPromoter.getPromoter().getId()));
        updateEventRequest.promoters().forEach((Long promoterId) -> {
            if (event.getPromoters().stream().noneMatch(eventPromoter -> eventPromoter.getPromoter().getId().equals(promoterId))) {
                Promoter promoter = getPromoterById(promoterId);
                event.addPromoter(promoter);
            }
        });
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteEvent(Long id) {
        eventRepository.deleteById(getEventById(id).getId());
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, EVENT_DELETED_OK), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> closeEvent(Long id) {
        Event event = getEventById(id);
        event.setOpen(Boolean.FALSE);
        eventRepository.save(event);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, EVENT_CLOSED_OK), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Event> addPromoterToEvent(Long eventId, Long promoterId) {
        Event event = getEventById(eventId);
        Promoter promoter = getPromoterById(promoterId);
        event.addPromoter(promoter);
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Event> removePromoterFromEvent(Long eventId, Long promoterId) {
        Event event = getEventById(eventId);
        Promoter promoter = getPromoterById(promoterId);
        event.removePromoter(promoter);
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Event> updateEventPromoter(Long eventId, Long promoterId, UpdateEventPromoterRequest request) {
        EventPromoter eventPromoter = eventPromoterRepository.findById(new EventPromoterId(eventId, promoterId))
                .orElseThrow(() -> new ResourceNotFoundException(EVENT_PROMOTER, ID, eventId));
        eventPromoter.setLadies(request.ladies());
        eventPromoter.setGuys(request.guys());
        eventPromoter.setFreeLadies(request.freeLadies());
        eventPromoter.setVip(request.vip());
        eventPromoter.setPaid(request.paid());
        eventPromoterRepository.save(eventPromoter);

        return new ResponseEntity<>(getEventById(eventId), HttpStatus.OK);
    }

    private Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(EVENT, ID, id));
    }

    private Promoter getPromoterById(Long id) {
        return promoterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PROMOTER, ID, id));
    }
}
