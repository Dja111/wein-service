package wein.service.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wein.service.demo.domain.Wein;
import wein.service.demo.service.WeinService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(WeinController.ENTRY_POINT)
public class WeinController {
    protected static final String ENTRY_POINT = "/weins";

    private WeinService weinService;

    @Autowired
    WeinController(WeinService weinService){
        this.weinService = weinService;
    }

    @PostMapping(path = "")
    public Wein createWein(@RequestBody Wein wein) {
        return weinService.createNewWein(wein);
    }

    @GetMapping(path = "")
    public Iterable<Wein> getWeins(){
        return  weinService.getWeins();
    }

    @GetMapping(path = "/{id}")
    public Wein getWeinById(@PathVariable long id) {
        return weinService.getWeinById(id);
    }

    @DeleteMapping(path = "/{id}")
    public boolean deleteWeinById(@PathVariable long id){
        return  weinService.deleteWeinById(id);
    }
    @PutMapping(path = "")
    public Wein updateWein(@RequestBody Wein wein) throws Exception {
        return weinService.updateWein(wein);
    }

}
