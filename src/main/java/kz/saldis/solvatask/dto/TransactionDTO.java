package kz.saldis.solvatask.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kz.saldis.solvatask.model.TransactionCategory;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

    private String firstName;
    private String companyName;
    private Double transactionSum;
    private String currency;
    private TransactionCategory expenseCategory;
    private Boolean isLimitExceeded;
    private LocalDateTime date;
    private Double balance;
}
