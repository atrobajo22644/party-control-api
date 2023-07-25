package com.leveltwo.partycontrol.controller;

import com.leveltwo.partycontrol.model.Event;
import com.leveltwo.partycontrol.payload.request.UpdateEventPromoterRequest;
import com.leveltwo.partycontrol.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.leveltwo.partycontrol.utils.AppConstants.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventPromoterController {

    private final EventService eventService;

    @PostMapping(value="/{eventId}/promoters/{promoterId}")
    public ResponseEntity<Event> addPromoterToEvent(
            @PathVariable(name=EVENT_ID) Long eventId,
            @PathVariable(name=PROMOTER_ID) Long promoterId)
    {
        return eventService.addPromoterToEvent(eventId, promoterId);
    }

    @PutMapping(value="/{eventId}/promoters/{promoterId}")
    public ResponseEntity<Event> updateEventPromoter(
            @PathVariable(name=EVENT_ID) Long eventId,
            @PathVariable(name=PROMOTER_ID) Long promoterId,
            @RequestBody UpdateEventPromoterRequest updateEventPromoterRequest)
    {
        return eventService.updateEventPromoter(eventId, promoterId, updateEventPromoterRequest);
    }

    @DeleteMapping(value="/{eventId}/promoters/{promoterId}")
    public ResponseEntity<Event> removePromoterFromEvent(
            @PathVariable(name=EVENT_ID) Long eventId,
            @PathVariable(name=PROMOTER_ID) Long promoterId)
    {
        return eventService.removePromoterFromEvent(eventId, promoterId);
    }
}
