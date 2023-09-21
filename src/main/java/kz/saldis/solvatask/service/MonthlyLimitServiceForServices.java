package kz.saldis.solvatask.service;

import kz.saldis.solvatask.model.*;
import kz.saldis.solvatask.repository.MonthlyLimitServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyLimitServiceForServices {

    private final MonthlyLimitServicesRepository monthlyLimitServicesRepository;
    private final ClientService clientService;

    public MonthlyLimitServices getLatestLimitBalance(Long clientId) {
        return monthlyLimitServicesRepository.findLatestLimitBalance(clientId);
    }

    private Double getLastSetLimit(Long clientId) {
        return monthlyLimitServicesRepository.lastSetLimit(clientId);
    }

    public void updateLimit(MonthlyLimitServices monthlyLimitServices) {
        monthlyLimitServicesRepository.save(monthlyLimitServices);
    }

    public MonthlyLimitServices createMonthlyLimit(Client client){
        return
                monthlyLimitServicesRepository.save(MonthlyLimitServices.builder().
                        limitService(1000.00).
                        balance(1000.00).
                        currency("USD").
                        limitSetDate(LocalDateTime.now()).
                        client(client).
                        build());
    }

    public void updateLimitForServices(Double newLimitForGoods,int iin) {
        Client client = clientService.getClientByIIN(iin);
        List<MonthlyLimitServices> monthlyLimitServicesList = client.getLimitServices();
        Double lastLimit = getLastSetLimit(client.getId());
        MonthlyLimitServices monthlyLimitServices = getLatestLimitBalance(client.getId());
        System.out.println(monthlyLimitServices);
        MonthlyLimitServices mls = MonthlyLimitServices.builder().
                        client(client).
                        limitSetDate(LocalDateTime.now()).
                        limitService(newLimitForGoods).
                        balance(monthlyLimitServices.getBalance() + (newLimitForGoods- lastLimit)).
                        currency("USD").
                        build();
        monthlyLimitServicesList.add(mls);
        client.setLimitServices(monthlyLimitServicesList);
        monthlyLimitServicesRepository.save(mls);
    }
}
