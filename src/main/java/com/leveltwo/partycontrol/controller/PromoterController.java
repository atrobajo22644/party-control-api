package com.leveltwo.partycontrol.controller;

import com.leveltwo.partycontrol.model.Promoter;
import com.leveltwo.partycontrol.payload.request.CreatePromoterRequest;
import com.leveltwo.partycontrol.payload.request.UpdatePromoterRequest;
import com.leveltwo.partycontrol.payload.response.ApiResponse;
import com.leveltwo.partycontrol.service.PromoterService;
import com.leveltwo.partycontrol.utils.SortUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.leveltwo.partycontrol.utils.AppConstants.ID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promoters")
public class PromoterController {

    private final PromoterService promoterService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPromotersPage(
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id,asc") String[] sort) {
        try {
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(new Order(SortUtils.getSortDirection(sort[1]), sort[0])));
            Page<Promoter> promotersPage = promoterService.getPromotersPage(filter, pagingSort);
            List<Promoter> promoters = promotersPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("promoters", promoters);
            response.put("pageNumber", promotersPage.getPageable().getPageNumber());
            response.put("totalItems", promotersPage.getTotalElements());
            response.put("totalPages", promotersPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/active")
    public ResponseEntity<List<Promoter>> getActivePromoters() {
        return promoterService.getActivePromoters(true);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Promoter> getPromoter(@PathVariable(name=ID) Long id)
    {
        return promoterService.getPromoter(id);
    }

    @PostMapping
    public ResponseEntity<Promoter> createPromoter(@RequestBody CreatePromoterRequest createPromoterRequest)
    {
        return promoterService.createPromoter(createPromoterRequest);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Promoter> update(@PathVariable(name=ID) Long id, @RequestBody UpdatePromoterRequest updatePromoterRequest)
    {
        return promoterService.updatePromoter(id, updatePromoterRequest);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable(name=ID) Long id)
    {
        return promoterService.deletePromoter(id);
    }
}
