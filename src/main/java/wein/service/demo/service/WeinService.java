package wein.service.demo.service;

import org.springframework.stereotype.Service;
import wein.service.demo.domain.Wein;

@Service
public interface WeinService {
    Wein createNewWein(Wein wein);
    Iterable<Wein> getWeins();
    boolean deleteWeinById(long id);
    Wein getWeinById(long id);
    Wein updateWein(Wein wein) throws Exception;
}
