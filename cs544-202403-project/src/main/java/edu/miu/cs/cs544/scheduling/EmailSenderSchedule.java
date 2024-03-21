package edu.miu.cs.cs544.scheduling;

import edu.miu.cs.cs544.integration.KafkaSender;
import edu.miu.cs.cs544.repository.AccountRepository;
import edu.miu.cs.cs544.service.contract.EmailPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EmailSenderSchedule {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //@Scheduled(fixedRate = 20000)
    @Scheduled(cron = "0 0 10 * * ?")
    public void dailyJob() {
        List<String> accounts = accountRepository.findAccountsByBalanceCondition();
        for (String email : accounts) {
            EmailPayload payload = new EmailPayload(email, "Email: " + email + " has a balance below the 5% of the default balance.");
            kafkaSender.send("email", payload);
           //kafkaTemplate.send("email", "Email: " + email + " has a balance below the 5% of the default balance.");
        }
    }
}
