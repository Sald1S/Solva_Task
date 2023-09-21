package kz.saldis.solvatask.repository;

import kz.saldis.solvatask.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findClientByIin(int iin);

    @Query("SELECT c FROM Client c JOIN Transaction tr on c.id = :clientId join MonthlyLimitGoods mlg on tr.date = mlg.limitSetDate where tr.expenseCategory = 1 and tr.isLimitExceeded = true and mlg.balance < 0")
    Client findClientsWithExceededLimit(Long clientId);
}
