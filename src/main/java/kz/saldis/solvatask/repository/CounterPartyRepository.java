package kz.saldis.solvatask.repository;

import jakarta.transaction.Transactional;
import kz.saldis.solvatask.model.CounterParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CounterPartyRepository extends JpaRepository<CounterParty,Long> {

    CounterParty findCounterPartyByBin(int bin);
}
