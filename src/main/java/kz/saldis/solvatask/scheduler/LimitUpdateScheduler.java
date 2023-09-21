package kz.saldis.solvatask.scheduler;


import kz.saldis.solvatask.api.ClientController;
import kz.saldis.solvatask.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LimitUpdateScheduler {

    private final ClientService clientService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateLimitsEveryMonth() {
        clientService.updateLimitsEveryMonth();
    }
}
