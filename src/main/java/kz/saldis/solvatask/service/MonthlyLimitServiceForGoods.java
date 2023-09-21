package kz.saldis.solvatask.service;

import kz.saldis.solvatask.model.*;
import kz.saldis.solvatask.repository.MonthlyLimitGoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class  MonthlyLimitServiceForGoods {

    private final MonthlyLimitGoodsRepository monthlyLimitGoodsRepository;
    private final ClientService clientService;



    public MonthlyLimitGoods getLatestLimitBalance(Long clientId) {
        return monthlyLimitGoodsRepository.findLatestLimitBalance(clientId);
    }

    private Double getLastSetLimit(Long clientId) {
        return monthlyLimitGoodsRepository.lastSetLimit(clientId);
    }

    public void updateLimit(MonthlyLimitGoods monthlyLimitGoods) {
        monthlyLimitGoodsRepository.save(monthlyLimitGoods);
    }

    public MonthlyLimitGoods createMonthlyLimit(Client client){
        return
                monthlyLimitGoodsRepository.save(MonthlyLimitGoods.builder().
                        limitGoods(1000.00).
                        balance(1000.00).
                        limitSetDate(LocalDateTime.now()).
                        currency("USD").
                        client(client).
                        build());
    }

    public void updateLimitForGoods(Double newLimitForGoods,int iin) {
        Client client = clientService.getClientByIIN(iin);
        List<MonthlyLimitGoods> monthlyLimitGoodsList = client.getLimitGoods();
        Double lastLimit = getLastSetLimit(client.getId());
        MonthlyLimitGoods monthlyLimitGoods = getLatestLimitBalance(client.getId());
        MonthlyLimitGoods mlg = MonthlyLimitGoods.builder().
                client(client).
                limitSetDate(LocalDateTime.now()).
                limitGoods(newLimitForGoods).
                balance(monthlyLimitGoods.getBalance() + (newLimitForGoods- lastLimit)).
                currency("USD").
                build();
        monthlyLimitGoodsList.add(mlg);
        client.setLimitGoods(monthlyLimitGoodsList);
        monthlyLimitGoodsRepository.save(mlg);
    }
}

