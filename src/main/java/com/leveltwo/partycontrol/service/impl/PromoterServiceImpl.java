package com.leveltwo.partycontrol.service.impl;

import com.leveltwo.partycontrol.exception.ResourceNotFoundException;
import com.leveltwo.partycontrol.model.Promoter;
import com.leveltwo.partycontrol.payload.request.CreatePromoterRequest;
import com.leveltwo.partycontrol.payload.request.UpdatePromoterRequest;
import com.leveltwo.partycontrol.payload.response.ApiResponse;
import com.leveltwo.partycontrol.repository.PromoterRepository;
import com.leveltwo.partycontrol.service.PromoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.leveltwo.partycontrol.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
public class PromoterServiceImpl implements PromoterService {

    private final PromoterRepository promoterRepository;

    @Override
    public Page<Promoter> getAllPromoters(Pageable pageable) {
        return promoterRepository.findAll(pageable);
    }

    @Override
    public Page<Promoter> getPromotersPage(String filter, Pageable pageable) {
        return (filter == null) ? getAllPromoters(pageable) : promoterRepository.findByNameContaining(filter, pageable);
    }

    @Override
    public ResponseEntity<List<Promoter>> getActivePromoters(boolean isActive) {
        return new ResponseEntity<>(promoterRepository.findByActive(isActive), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Promoter> getPromoter(Long id) {
        return new ResponseEntity<>(getPromoterById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Promoter> createPromoter(CreatePromoterRequest createPromoterRequest) {
        Promoter newPromoter = Promoter.builder()
                .name(createPromoterRequest.name())
                .active(createPromoterRequest.active())
                .build();
        return new ResponseEntity<>(promoterRepository.save(newPromoter), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Promoter> updatePromoter(Long id, UpdatePromoterRequest updatePromoterRequest) {
        Promoter promoter = getPromoterById(id);
        promoter.setName(updatePromoterRequest.name());
        promoter.setActive(updatePromoterRequest.active());
        return new ResponseEntity<>(promoterRepository.save(promoter), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> deletePromoter(Long id) {
        promoterRepository.deleteById(getPromoterById(id).getId());
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, PROMOTER_DELETED_OK), HttpStatus.OK);
    }

    private Promoter getPromoterById(Long id) {
        return promoterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PROMOTER, ID, id));
    }
}
