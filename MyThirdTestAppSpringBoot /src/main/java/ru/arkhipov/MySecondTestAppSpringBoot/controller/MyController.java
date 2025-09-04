package ru.arkhipov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;
import ru.arkhipov.MySecondTestAppSpringBoot.service.*;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final TimeProcessingService timeProcessingService;
    private final ErrorProcessingService errorProcessingService;
    private final BonusCalculationService bonusCalculationService;
    private final ResponseFactory responseFactory;

    @Autowired
    public MyController(ValidationService validationService,
                        TimeProcessingService timeProcessingService,
                        ErrorProcessingService errorProcessingService,
                        BonusCalculationService bonusCalculationService,
                        ResponseFactory responseFactory) {
        this.validationService = validationService;
        this.timeProcessingService = timeProcessingService;
        this.errorProcessingService = errorProcessingService;
        this.bonusCalculationService = bonusCalculationService;
        this.responseFactory = responseFactory;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("Получен request: {}", request);

        // Обработка времени запроса
        timeProcessingService.processRequestTime(request.getSystemTime());

        // Создаем базовый response
        Response response = responseFactory.createResponse(request);
        log.info("Создан начальный response: {}", response);

        try {
            // Логирование ошибок валидации
            errorProcessingService.logValidationErrors(bindingResult);

            // Валидация
            validationService.isValid(bindingResult);
            log.info("Валидация прошла успешно.");

            // Проверка на запрещенный UID
            if ("123".equals(request.getUid())) {
                log.error("Ошибка: UID=123 не поддерживается.");
                throw new ru.arkhipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException("UID 123 запрещен");
            }

            // Расчет премий
            bonusCalculationService.calculateBonuses(request, response);

        } catch (ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException e) {
            return errorProcessingService.handleValidationError(response);

        } catch (ru.arkhipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException e) {
            return errorProcessingService.handleUnsupportedCodeError(response);

        } catch (Exception e) {
            return errorProcessingService.handleUnknownError(response, e);
        }

        log.info("Отправляем финальный response: {}", response);
        return ResponseEntity.ok(response);
    }
}