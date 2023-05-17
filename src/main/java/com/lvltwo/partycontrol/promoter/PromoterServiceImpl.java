package com.lvltwo.partycontrol.promoter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoterServiceImpl implements PromoterService {

    @Autowired
    private PromoterRepository promoterRepository;


    @Override
    public List<Promoter> listAll() {
        return promoterRepository.findAll();
    }

    @Override
    public Optional<Promoter> getById(Integer id) {
        return promoterRepository.findById(id);
    }

    @Override
    public Promoter create(PromoterCreateDTO promoterCreateDTO) {
        Promoter promoter = Promoter.builder()
                .name(promoterCreateDTO.name())
                .build();

        return promoterRepository.save(promoter);
    }

    @Override
    public Promoter update(Integer id, PromoterUpdateDTO promoterUpdateDTO) {
        Optional<Promoter> optional = getById(id);
        if (optional.isPresent()) {
            Promoter dbPromoter = optional.get();
            dbPromoter.setName(promoterUpdateDTO.name());
            dbPromoter.setActive(promoterUpdateDTO.isActive());

            return promoterRepository.save(dbPromoter);
        }

        return null;
    }

    @Override
    public void delete(Integer id) {
        if (getById(id).isPresent()) {
            promoterRepository.deleteById(id);
        }
    }
}
