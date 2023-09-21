package kz.saldis.solvatask.model;


import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class CurrencyExchangeRate extends BaseEntity{

    private String fromCurrency;
    private String toCurrency;
    private LocalDateTime updateTime;
    private Double exchangeRate;
}
