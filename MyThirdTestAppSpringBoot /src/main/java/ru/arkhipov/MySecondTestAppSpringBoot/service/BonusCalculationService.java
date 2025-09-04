package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Response;

import java.math.BigDecimal;

@Slf4j
@Service
public class BonusCalculationService {

    private final AnnualBonusService annualBonusService;
    private final QuarterlyBonusService quarterlyBonusService;

    public BonusCalculationService(AnnualBonusService annualBonusService,
                                   QuarterlyBonusService quarterlyBonusService) {
        this.annualBonusService = annualBonusService;
        this.quarterlyBonusService = quarterlyBonusService;
    }

    public void calculateBonuses(Request request, Response response) {
        // Расчет годовой премии
        double annualBonus = annualBonusService.calculate(
                request.getPosition(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays()
        );
        response.setAnnualBonus(annualBonus);

        // Расчет квартальной премии
        BigDecimal quarterlyBonus = quarterlyBonusService.calculate(
                request.getPosition(),
                request.getSalary(),
                request.getQuarter(),
                request.getPerformanceMultiplier()
        );
        response.setQuarterlyBonus(quarterlyBonus);

        log.info("Рассчитаны премии: годовая={}, квартальная={}", annualBonus, quarterlyBonus);
    }
}