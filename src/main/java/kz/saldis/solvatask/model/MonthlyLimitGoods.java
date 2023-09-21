package kz.saldis.solvatask.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class MonthlyLimitGoods extends BaseEntity{

    @ManyToOne
    @JsonIgnore
    private Client client;
    private Double limitGoods;
    private Double balance;
    private LocalDateTime limitSetDate;
    private String currency;
}
