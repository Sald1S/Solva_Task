package kz.saldis.solvatask.ServiceTest;

import kz.saldis.solvatask.model.*;
import kz.saldis.solvatask.repository.MonthlyLimitGoodsRepository;
import kz.saldis.solvatask.repository.MonthlyLimitServicesRepository;
import kz.saldis.solvatask.repository.TransactionRepository;
import kz.saldis.solvatask.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
//public class TransactionServiceTest {
//
//    @InjectMocks
//    private TransactionService transactionService;
//
//    @Mock
//    private ClientService clientService;
//
//    @Mock
//    private CounterPartyService counterPartyService;
//
//    @Mock
//    private MonthlyLimitServiceForServices monthlyLimitServiceForServices;
//
//    @Mock
//    private MonthlyLimitServiceForGoods monthlyLimitServiceForGoods;
//
//    @Mock
//    private CurrencyExchangeRateService currencyExchangeRateService;
//
//    @Mock
//    private MonthlyLimitServicesRepository monthlyLimitServicesRepository;
//
//    @Mock
//    private MonthlyLimitGoodsRepository monthlyLimitGoodsRepository;
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Test
//    public void testCreateTransaction_Success() {
//        int iin = 11;
//        int bin = 111;
//
//
//        Transaction transaction = Transaction.builder()
//                .bin(bin)
//                .iin(iin)
//                .transactionSum(100.00)
//                .client(Client.builder()
//                        .firstName("ALdi")
//                        .iin(iin)
//                        .build())
//                .currency("USD")
//                .counterparty(CounterParty.builder()
//                        .name("Kaspi")
//                        .bin(bin)
//                        .build())
//                .expenseCategory(TransactionCategory.SERVICE)
//                .date(LocalDateTime.now())
//                .build();
//
//        transactionService.createTransaction(transaction, iin, bin);
//
//        // Проверьте, что методы сохранения были вызваны
//        verify(monthlyLimitServicesRepository, times(1)).save(any(MonthlyLimitServices.class));
//        verify(transactionRepository, times(1)).save(any(Transaction.class));
//    }
//}
