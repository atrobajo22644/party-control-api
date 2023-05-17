package com.lvltwo.partycontrol.promoter;

import java.util.List;
import java.util.Optional;

public interface PromoterService {
    List<Promoter> listAll();

    Optional<Promoter> getById(Integer id);

    Promoter create(PromoterCreateDTO promoterCreateDTO);

    Promoter update(Integer id, PromoterUpdateDTO promoter);

    void delete(Integer id);
}
