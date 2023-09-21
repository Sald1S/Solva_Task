package kz.saldis.solvatask.repository;

import kz.saldis.solvatask.api.CurrencyController;
import kz.saldis.solvatask.model.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate,Long> {

//    @Query(value = "select * from currency_exchange_rate where to_currency = 'KZT' order by set_date desc LIMIT 1")
//    CurrencyExchangeRate usdToKzt();

    @Query("SELECT c.exchangeRate FROM CurrencyExchangeRate c WHERE c.toCurrency = 'KZT' ORDER BY c.updateTime DESC")
    Double usdToKztRate();

    @Query("SELECT c.exchangeRate FROM CurrencyExchangeRate c WHERE c.toCurrency = 'RUB' ORDER BY c.updateTime DESC")
    Double usdToRubRate();
}
