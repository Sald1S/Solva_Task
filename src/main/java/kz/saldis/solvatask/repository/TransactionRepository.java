package kz.saldis.solvatask.repository;

import jakarta.transaction.Transactional;
import kz.saldis.solvatask.model.Transaction;
import kz.saldis.solvatask.dto.TransactionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT NEW kz.saldis.solvatask.dto.TransactionDTO" +
            "    (c.firstName," +
            "    t.counterparty.name," +
            "    t.transactionSum," +
            "    t.currency," +
            "    t.expenseCategory," +
            "    t.isLimitExceeded," +
            "    t.date," +
            "    mlg.balance)" +
            " FROM Client c" +
            " JOIN Transaction t ON c.id = t.client.id" +
            " JOIN MonthlyLimitGoods mlg ON t.date = mlg.limitSetDate" +
            " WHERE t.isLimitExceeded = true" +
            " AND mlg.balance < 0" +
            " AND c.id = :clientId")
    List<Optional<TransactionDTO>> findTransactionsWithExceededLimitsByGoods(Long clientId);


    @Query("SELECT NEW kz.saldis.solvatask.dto.TransactionDTO" +
            "    (c.firstName," +
            "    t.counterparty.name," +
            "    t.transactionSum," +
            "    t.currency," +
            "    t.expenseCategory," +
            "    t.isLimitExceeded," +
            "    t.date," +
            "    mlg.balance)" +
            " FROM Client c" +
            " JOIN Transaction t ON c.id = t.client.id" +
            " JOIN MonthlyLimitServices mlg ON t.date = mlg.limitSetDate" +
            " WHERE t.isLimitExceeded = true" +
            " AND mlg.balance < 0" +
            " AND c.id = :clientId")
    List<Optional<TransactionDTO>> findTransactionsWithExceededLimitsByService(Long clientId);

    List<Transaction> findAllByClient_Id(Long clientId);
}
