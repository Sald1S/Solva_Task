package kz.saldis.solvatask.scheduler;

import kz.saldis.solvatask.api.CurrencyController;
import kz.saldis.solvatask.service.CurrencyExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyScheduler {

    private final CurrencyExchangeRateService currencyExchangeRateService;

    @Scheduled(cron = "0 * * * * *")
    public void updateExchangeRatesDaily() {
        currencyExchangeRateService.updateExchangeRates();
    }
}
