package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Service
public class TimeProcessingService {

    public void processRequestTime(String systemTime) {
        try {
            Instant requestTime = Instant.parse(systemTime);
            Instant receivedTime = Instant.now();
            long millis = Duration.between(requestTime, receivedTime).toMillis();
            log.info("Время между отправкой запроса (Сервис 1) и получением (Сервис 2): {} мс", millis);
        } catch (Exception e) {
            log.warn("Не удалось вычислить разницу времени: {}", e.getMessage());
        }
    }
}