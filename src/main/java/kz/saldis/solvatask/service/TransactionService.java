package kz.saldis.solvatask.service;

import kz.saldis.solvatask.dto.TransactionDTO;
import kz.saldis.solvatask.model.*;
import kz.saldis.solvatask.repository.CurrencyExchangeRateRepository;
import kz.saldis.solvatask.repository.MonthlyLimitGoodsRepository;
import kz.saldis.solvatask.repository.MonthlyLimitServicesRepository;
import kz.saldis.solvatask.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MonthlyLimitServiceForServices monthlyLimitServiceForServices;
    private final MonthlyLimitServiceForGoods monthlyLimitServiceForGoods;
    private final ClientService clientService;
    private final CounterPartyService counterPartyService;
    private final CurrencyExchangeRateService currencyExchangeRateService;

    public List<Transaction> getClientTransactions(int iin) {
        Client client = clientService.getClientByIIN(iin);
        return transactionRepository.findAllByClient_Id(client.getId());
    }

    public List<Optional<TransactionDTO>> findTransactionsWithExceededLimitsGroupedByClient(int iin) {
        Client client = clientService.getClientByIIN(iin);
        List<Optional<TransactionDTO>> listGoods = transactionRepository.findTransactionsWithExceededLimitsByGoods(client.getId());
        List<Optional<TransactionDTO>> listService = transactionRepository.findTransactionsWithExceededLimitsByService(client.getId());
        listGoods.addAll(listService);
        return listGoods;
    }


    public void createTransaction(Transaction transaction, int iin, int bin) {
        Client client = clientService.getClientByIIN(iin);
        transaction.setClient(client);
        transaction.setIin(iin);
        CounterParty counterParty = counterPartyService.getCounterPartyByBin(bin);
        transaction.setCounterparty(counterParty);
        transaction.setBin(bin);
        transaction.setDate(LocalDateTime.now());

        Double transactionSumInUSD = currencyExchangeRateService.getExchangeRate(transaction.getCurrency(), transaction.getTransactionSum());
        System.out.println(transactionSumInUSD);
        if (transaction.getExpenseCategory() == TransactionCategory.SERVICE) {
            MonthlyLimitServices monthlyLimitServices = monthlyLimitServiceForServices.getLatestLimitBalance(transaction.getClient().getId());
            if (transactionSumInUSD > monthlyLimitServices.getBalance()) {
                transaction.setIsLimitExceeded(true);
                monthlyLimitServiceForServices.updateLimit(MonthlyLimitServices.builder().
                        balance(monthlyLimitServices.getBalance() - transactionSumInUSD).
                        currency("USD").
                        limitSetDate(transaction.getDate()).
                        client(transaction.getClient()).
                        build());
            } else {
                transaction.setIsLimitExceeded(false);
                monthlyLimitServiceForServices.updateLimit(MonthlyLimitServices.builder().
                        balance(monthlyLimitServices.getBalance() - transactionSumInUSD).
                        currency("USD").
                        limitSetDate(transaction.getDate()).
                        client(transaction.getClient()).
                        build());
            }
        } else if (transaction.getExpenseCategory() == TransactionCategory.GOODS) {
            MonthlyLimitGoods monthlyLimitGoods = monthlyLimitServiceForGoods.getLatestLimitBalance(transaction.getClient().getId());
            if (transactionSumInUSD > monthlyLimitGoods.getBalance()) {
                transaction.setIsLimitExceeded(true);
                monthlyLimitServiceForGoods.updateLimit(MonthlyLimitGoods.builder().
                        balance(monthlyLimitGoods.getBalance() - transactionSumInUSD).
                        currency("USD").
                        limitSetDate(transaction.getDate()).
                        client(transaction.getClient()).
                        build());
            } else {
                transaction.setIsLimitExceeded(false);
                monthlyLimitServiceForGoods.updateLimit(MonthlyLimitGoods.builder().
                        balance(monthlyLimitGoods.getBalance() - transactionSumInUSD).
                        currency("USD").
                        limitSetDate(transaction.getDate()).
                        client(transaction.getClient()).
                        build());
            }
        }
        transactionRepository.save(transaction);
    }
}
