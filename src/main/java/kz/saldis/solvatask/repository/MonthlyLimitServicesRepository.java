package kz.saldis.solvatask.repository;

import kz.saldis.solvatask.model.Client;
import kz.saldis.solvatask.model.CurrencyExchangeRate;
import kz.saldis.solvatask.model.MonthlyLimitGoods;
import kz.saldis.solvatask.model.MonthlyLimitServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MonthlyLimitServicesRepository extends JpaRepository<MonthlyLimitServices, Long> {
    MonthlyLimitServices findByClient_Id(Long clientId);

//    @Query("SELECT mls FROM MonthlyLimitServices mls WHERE mls.client.id = :clientId ORDER BY mls.balance ASC LIMIT 1")
//    MonthlyLimitServices findSmallestBalanceMonthlyLimitByClientId(Long clientId);

    @Query("SELECT mls FROM MonthlyLimitServices mls WHERE mls.client.id = :clientId ORDER BY mls.limitSetDate DESC LIMIT 1")
    MonthlyLimitServices findLatestLimitBalance(Long clientId);

    @Query("SELECT mls.limitService FROM MonthlyLimitServices mls WHERE mls.client.id = :clientId AND mls.limitService is not null ORDER BY mls.limitSetDate DESC LIMIT 1")
    Double lastSetLimit(Long clientId);

//    @Query("SELECT mls.limitService FROM MonthlyLimitServices mls JOIN Cli c WHERE mls.client_id = :clientId ORDER BY mls.limit_service DESC limit 1\n")
//    Double lastSetLimit(Long clientId);
}
