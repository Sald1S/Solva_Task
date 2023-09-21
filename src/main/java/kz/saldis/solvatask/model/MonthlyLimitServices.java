package kz.saldis.solvatask.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MonthlyLimitServices extends BaseEntity{

    @ManyToOne
    @JsonIgnore
    private Client client;
    private Double limitService;
    private Double balance;
    private LocalDateTime limitSetDate;
    private String currency;
}
