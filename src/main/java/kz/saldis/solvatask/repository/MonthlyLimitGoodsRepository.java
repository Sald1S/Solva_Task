package kz.saldis.solvatask.repository;

import kz.saldis.solvatask.model.Client;
import kz.saldis.solvatask.model.MonthlyLimitGoods;
import kz.saldis.solvatask.model.MonthlyLimitServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MonthlyLimitGoodsRepository extends JpaRepository<MonthlyLimitGoods, Long> {

    @Query("SELECT mgl FROM MonthlyLimitGoods mgl WHERE mgl.client.id = :clientId ORDER BY mgl.limitSetDate DESC LIMIT 1")
    MonthlyLimitGoods findLatestLimitBalance(Long clientId);

    @Query("SELECT mgl.limitGoods FROM MonthlyLimitGoods mgl WHERE mgl.client.id = :clientId AND" +
            " mgl.limitGoods is not null ORDER BY mgl.limitSetDate DESC LIMIT 1")
    Double lastSetLimit(Long clientId);
}
