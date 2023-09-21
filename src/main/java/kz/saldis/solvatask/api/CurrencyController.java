package kz.saldis.solvatask.api;

import kz.saldis.solvatask.service.CurrencyExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyExchangeRateService currencyExchangeRateService;

    @GetMapping("/update")
    public ResponseEntity<?> updateExchangeRates() {
        try {
            // Ваша логика сохранения транзакции в базе данных
            currencyExchangeRateService.updateExchangeRates();
            return ResponseEntity.status(HttpStatus.CREATED).body("Currency updated successfully");
        } catch (Exception e) {
            // Обработка ошибок и возврат соответствующего HTTP-статуса
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating currency");
        }
    }
}