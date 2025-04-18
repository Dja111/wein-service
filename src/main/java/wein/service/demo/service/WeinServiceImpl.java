package wein.service.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wein.service.demo.domain.Wein;
import wein.service.demo.repository.WeinRepository;

@Service
public class WeinServiceImpl implements WeinService{

    private WeinRepository weinRepository;

    @Autowired
    WeinServiceImpl(WeinRepository weinRepository){
        this.weinRepository = weinRepository;
    }
    @Override
    public Wein createNewWein(Wein wein) {
        return weinRepository.save(wein);
    }

    @Override
    public Iterable<Wein> getWeins() {
        return weinRepository.findAll();
    }

    @Override
    public boolean deleteWeinById(long id) {
         weinRepository.deleteById(id);
         if(weinRepository.existsWeinById(id)){
             return false;
         }else {
             return true;
         }
    }

    @Override
    public Wein getWeinById(long id) {
        return weinRepository.findWeinById(id);
    }

    @Override
    public Wein updateWein(Wein wein) throws Exception {
        Wein optionalWein = weinRepository.findWeinById(wein.getId());
        if(weinRepository.existsWeinById(optionalWein.getId())){
            return weinRepository.save(wein);
        }else {
            throw new Exception("Error-Type 404, This Win was noct Found");
        }
    }
}
