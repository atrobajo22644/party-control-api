package com.lvltwo.partycontrol.promoter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promoters")
public class PromoterController {

    @Autowired
    private PromoterService promoterService;

    @GetMapping
    public List<Promoter> listAll() {
        return promoterService.listAll();
    }

    @GetMapping(value = "/{id}")
    public Promoter getById(@PathVariable Integer id)
    {
        return promoterService.getById(id).orElse(null);
    }

    @PostMapping
    public Promoter create(@RequestBody PromoterCreateDTO promoterCreateDTO)
    {
        return promoterService.create(promoterCreateDTO);
    }

    @PutMapping(value = "/{id}")
    public Promoter update(@PathVariable Integer id, @RequestBody PromoterUpdateDTO promoterUpdateDTO)
    {
        return promoterService.update(id, promoterUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id)
    {
        promoterService.delete(id);
    }
}
