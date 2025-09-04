package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service; // Добавить этот импорт
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.arkhipov.MySecondTestAppSpringBoot.model.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ErrorProcessingService {

    public void logValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            log.error("Ошибки валидации (bindingResult): {}", errors);
        }
    }

    public ResponseEntity<Response> handleValidationError(Response response) {
        log.error("ValidationFailedException");
        response.setCode(Codes.FAILED);
        response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
        response.setErrorMessage(ErrorMessages.VALIDATION);
        log.info("Response после ошибки валидации: {}", response);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> handleUnsupportedCodeError(Response response) {
        log.error("UnsupportedCodeException");
        response.setCode(Codes.FAILED);
        response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
        response.setErrorMessage(ErrorMessages.UNSUPPORTED);
        log.info("Response после UnsupportedCodeException: {}", response);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> handleUnknownError(Response response, Exception e) {
        log.error("Неизвестная ошибка: {}", e.getMessage(), e);
        response.setCode(Codes.FAILED);
        response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
        response.setErrorMessage(ErrorMessages.UNKNOWN);
        log.info("Response после неизвестной ошибки: {}", response);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}