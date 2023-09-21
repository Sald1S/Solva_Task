package kz.saldis.solvatask.api;

import kz.saldis.solvatask.model.Client;
import kz.saldis.solvatask.model.Transaction;
import kz.saldis.solvatask.service.ClientService;
import kz.saldis.solvatask.service.MonthlyLimitServiceForGoods;
import kz.saldis.solvatask.service.MonthlyLimitServiceForServices;
import kz.saldis.solvatask.service.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final MonthlyLimitServiceForGoods monthlyLimitServiceForGoods;
    private final MonthlyLimitServiceForServices monthlyLimitServiceForServices;

    @PostMapping("/add-client")
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        try {
            clientService.createClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).body("Client added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding client");
        }
    }

    @PutMapping("limits/update-limit-for-goods")
    public ResponseEntity<?> updateLimitForGoods(@RequestParam(name = "iin") int iin,
                                                 @RequestParam(name = "newLimit") Double newLimit) {
        try {
            monthlyLimitServiceForGoods.updateLimitForGoods(newLimit,iin);
            return ResponseEntity.status(HttpStatus.CREATED).body("Limit updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding client");
        }
    }

    @PutMapping("limits/update-limit-for-services")
    public ResponseEntity<?> updateLimitForServices(@RequestParam(name = "iin") int iin,
                                                 @RequestParam(name = "newLimit") Double newLimit) {
        try {
            monthlyLimitServiceForServices.updateLimitForServices(newLimit,iin);
            return ResponseEntity.status(HttpStatus.CREATED).body("Limit updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding client");
        }
    }

    @PutMapping("limits/update-limits")
    public ResponseEntity<?> updateLimitsEveryMonth() {
        try {
            // Ваша логика сохранения транзакции в базе данных
            clientService.updateLimitsEveryMonth();
            return ResponseEntity.status(HttpStatus.CREATED).body("Limits update successfully");
        } catch (Exception e) {
            // Обработка ошибок и возврат соответствующего HTTP-статуса
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating limits");
        }
    }
}
