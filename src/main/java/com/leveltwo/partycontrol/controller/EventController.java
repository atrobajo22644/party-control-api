package com.leveltwo.partycontrol.controller;

import com.leveltwo.partycontrol.model.Event;
import com.leveltwo.partycontrol.payload.request.CreateEventRequest;
import com.leveltwo.partycontrol.payload.request.UpdateEventRequest;
import com.leveltwo.partycontrol.payload.response.ApiResponse;
import com.leveltwo.partycontrol.service.EventService;
import com.leveltwo.partycontrol.utils.SortUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leveltwo.partycontrol.utils.AppConstants.ID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEventsPage(
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "eventDate,desc") String[] sort) {
        try {
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(
                    new Sort.Order(SortUtils.getSortDirection(sort[1]), sort[0]))
            );
            Page<Event> eventsPage = eventService.getEventsPage(filter, pagingSort);
            List<Event> events = eventsPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("events", events);
            response.put("pageNumber", eventsPage.getPageable().getPageNumber());
            response.put("totalItems", eventsPage.getTotalElements());
            response.put("totalPages", eventsPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/opened")
    public ResponseEntity<Event> getLastOpenedEvent()
    {
        return eventService.getLastOpenedEvent();
    }

    @GetMapping(value="{id}")
    public ResponseEntity<Event> getEvent(@PathVariable(name=ID) Long id)
    {
        return eventService.getEvent(id);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest createEventRequest)
    {
        return eventService.createEvent(createEventRequest);
    }

    @PutMapping(value="{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable(name=ID) Long id, @RequestBody UpdateEventRequest updateEventRequest)
    {
        return eventService.updateEvent(id, updateEventRequest);
    }

    @DeleteMapping(value="{id}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable(name=ID) Long id)
    {
        return eventService.deleteEvent(id);
    }

    @PutMapping(value="{id}/close")
    public ResponseEntity<ApiResponse> closeEvent(@PathVariable(name=ID) Long id)
    {
        return eventService.closeEvent(id);
    }
}
