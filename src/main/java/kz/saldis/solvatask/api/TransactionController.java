package kz.saldis.solvatask.api;

import kz.saldis.solvatask.dto.TransactionDTO;
import kz.saldis.solvatask.model.Transaction;
import kz.saldis.solvatask.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/exceeded-limits")
    public List<Optional<TransactionDTO>> getTransactionsWithExceededLimitsGroupedByClient(@RequestParam(name = "iin") int iin) {
        return transactionService.findTransactionsWithExceededLimitsGroupedByClient(iin);
    }

    @GetMapping("/client-transactions")
    public List<Transaction> getClientsAllTransactions(@RequestParam(name = "iin") int iin) {
        return transactionService.getClientTransactions(iin );
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction,
                                            @RequestParam(name = "iin") int iin,
                                            @RequestParam(name = "bin") int bin) {
        try {
            transactionService.createTransaction(transaction,iin,bin);
            return ResponseEntity.status(HttpStatus.CREATED).body("Transaction added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding transaction");
        }
    }
}
