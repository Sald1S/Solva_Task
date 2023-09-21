package kz.saldis.solvatask.service;

import jakarta.persistence.EntityNotFoundException;
import kz.saldis.solvatask.model.CounterParty;
import kz.saldis.solvatask.repository.CounterPartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounterPartyService {

    private final CounterPartyRepository counterPartyRepository;

    public CounterParty addCounterParty(CounterParty counterParty) {
        return counterPartyRepository.save(counterParty);
    }

    public CounterParty getCounterParty(Long id) {
        return counterPartyRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("counter party not found"));
    }

    public CounterParty getCounterPartyByBin(int bin) {
        return counterPartyRepository.findCounterPartyByBin(bin);
    }

}
