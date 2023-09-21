package kz.saldis.solvatask.service;

import kz.saldis.solvatask.model.CurrencyExchangeRate;

import kz.saldis.solvatask.repository.CurrencyExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeRateService {

    @Value("${openexchangerates.api.url}")
    private String apiUrl;

    @Value("${openexchangerates.api.appId}")
    private String appId;

    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public void updateExchangeRates() {
        String url = apiUrl + "?app_id=" + appId;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseData = response.getBody();
            LocalDateTime date = LocalDateTime.now();
            String baseCurrency = "USD"; // Базовая валюта

            Map<String, Double> rates = (Map<String, Double>) responseData.get("rates");


            Double exchangeRateUSDToRUB = rates.get("RUB");
            CurrencyExchangeRate currencyExchangeRateUSDToRUB = new CurrencyExchangeRate();
            currencyExchangeRateUSDToRUB.setFromCurrency(baseCurrency);
            currencyExchangeRateUSDToRUB.setToCurrency("RUB");
            currencyExchangeRateUSDToRUB.setExchangeRate(exchangeRateUSDToRUB);
            currencyExchangeRateUSDToRUB.setUpdateTime(date);
            currencyExchangeRateRepository.save(currencyExchangeRateUSDToRUB);

            Double exchangeRateUSDToKZT = rates.get("KZT");
            CurrencyExchangeRate currencyExchangeRateUSDToKZT = new CurrencyExchangeRate();
            currencyExchangeRateUSDToKZT.setFromCurrency(baseCurrency);
            currencyExchangeRateUSDToKZT.setToCurrency("KZT");
            currencyExchangeRateUSDToKZT.setExchangeRate(exchangeRateUSDToKZT);
            System.out.println(exchangeRateUSDToKZT);
            currencyExchangeRateUSDToKZT.setUpdateTime(date);
            currencyExchangeRateRepository.save(currencyExchangeRateUSDToKZT);
        } else {
            throw new RuntimeException("Не удалось получить данные от Open Exchange Rates");
        }
    }

    public Double getExchangeRate(String currency, Double transactionSum) {
        Double exchangeRate;
        Double sumInUsd;
        if (currency.equalsIgnoreCase("KZT")) {
            exchangeRate = currencyExchangeRateRepository.usdToKztRate();
            sumInUsd = transactionSum / exchangeRate;
            return sumInUsd;
        } else if (currency.equalsIgnoreCase("RUB")) {
            exchangeRate = currencyExchangeRateRepository.usdToRubRate();
            sumInUsd = transactionSum / exchangeRate;
            return sumInUsd;
        }
        return transactionSum;
    }
}
