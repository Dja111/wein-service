package wein.service.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wein.service.demo.domain.Wein;

@Repository
public interface WeinRepository extends CrudRepository<Wein, Long> {
    Wein findWeinById(Long id);
    Wein findWeinByWeinName(String weinName);
    boolean existsWeinById(Long id);
}
