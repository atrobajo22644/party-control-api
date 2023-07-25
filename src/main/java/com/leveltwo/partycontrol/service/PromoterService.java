package com.leveltwo.partycontrol.service;

import com.leveltwo.partycontrol.model.Promoter;
import com.leveltwo.partycontrol.payload.request.CreatePromoterRequest;
import com.leveltwo.partycontrol.payload.request.UpdatePromoterRequest;
import com.leveltwo.partycontrol.payload.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PromoterService {
    //Page<Promoter> getAllPromoters(Pageable pageable);
    Page<Promoter> getAllPromoters(Pageable pageable);

    Page<Promoter> getPromotersPage(String filter, Pageable pageable);

    ResponseEntity<List<Promoter>> getActivePromoters(boolean isActive);

    ResponseEntity<Promoter> getPromoter(Long id);

    ResponseEntity<Promoter> createPromoter(CreatePromoterRequest createPromoterRequest);

    ResponseEntity<Promoter> updatePromoter(Long id, UpdatePromoterRequest updatePromoterRequest);

    ResponseEntity<ApiResponse> deletePromoter(Long id);
}
