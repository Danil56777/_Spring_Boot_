package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.model.*;
import ru.arkhipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Service
public class ResponseFactory {

    private final DateTimeUtil dateTimeUtil;

    public ResponseFactory(DateTimeUtil dateTimeUtil) {
        this.dateTimeUtil = dateTimeUtil;
    }

    public Response createResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(dateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .annualBonus(0.0)
                .quarterlyBonus(null)
                .build();
    }

    public Response createErrorResponse(Request request, Codes code, ErrorCodes errorCode, ErrorMessages errorMessage) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(dateTimeUtil.getCustomFormat().format(new Date()))
                .code(code)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .annualBonus(0.0)
                .quarterlyBonus(null)
                .build();
    }
}