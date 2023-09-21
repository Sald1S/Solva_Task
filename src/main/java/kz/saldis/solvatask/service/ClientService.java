package kz.saldis.solvatask.service;

import jakarta.persistence.EntityNotFoundException;
import kz.saldis.solvatask.model.Client;
import kz.saldis.solvatask.model.MonthlyLimitGoods;
import kz.saldis.solvatask.model.MonthlyLimitServices;
import kz.saldis.solvatask.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    @Lazy
    private MonthlyLimitServiceForServices monthlyLimitServiceForServices;

    @Autowired
    @Lazy
    private MonthlyLimitServiceForGoods monthlyLimitServiceForGoods;


    public Client getClientByIIN(int iin) {
        return clientRepository.findClientByIin(iin);
    }

    public Client getAllClientsWithExceedLimit(int iin) {
        Client client = getClientByIIN(iin);
        return clientRepository.findClientsWithExceededLimit(client.getId());
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public void createClient(Client client) {
        Client newClient = getClientByIIN(client.getIin());
        if (newClient != null) {
            throw new DuplicateKeyException("Клиент с таким ИИН уже существует");
        }

        Client savedClient = clientRepository.save(client);
        addDefaultLimitsForGoods(savedClient);
        addDefaultLimitsForServices(savedClient);
    }

    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void updateLimitsEveryMonth() {
        List<Client> allClients = getAllClients();
        for(Client cl : allClients) {
            addDefaultLimitsForGoods(cl);
            addDefaultLimitsForServices(cl);
        }
    }

    private void addDefaultLimitsForServices(Client client){
        MonthlyLimitServices monthlyLimitServices = monthlyLimitServiceForServices.createMonthlyLimit(client);
        List<MonthlyLimitServices> monthlyLimitServicesList = new ArrayList<>();
        monthlyLimitServicesList.add(monthlyLimitServices);
        client.setLimitServices(monthlyLimitServicesList);
    }

    private void addDefaultLimitsForGoods(Client client){
        MonthlyLimitGoods monthlyLimitGoods = monthlyLimitServiceForGoods.createMonthlyLimit(client);
        List<MonthlyLimitGoods> monthlyLimitGoodsList = new ArrayList<>();
        monthlyLimitGoodsList.add(monthlyLimitGoods);
        client.setLimitGoods(monthlyLimitGoodsList);
    }
}
