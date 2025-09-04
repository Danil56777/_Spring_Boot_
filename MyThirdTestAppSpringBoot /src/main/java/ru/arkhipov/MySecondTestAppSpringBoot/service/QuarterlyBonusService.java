package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class QuarterlyBonusService {

    public BigDecimal calculate(Positions position, double salary, int quarter, double performanceMultiplier) {

        // Проверка что сотрудник является управленцем
        if (!position.isManager()) {
            return BigDecimal.ZERO;
        }

        // Проверка валидности квартала
        if (quarter < 1 || quarter > 4) {
            throw new IllegalArgumentException("Квартал должен быть в диапазоне 1-4");
        }

        // Проверка валидности коэффициента эффективности
        if (performanceMultiplier <= 0) {
            throw new IllegalArgumentException("Коэффициент эффективности должен быть положительным");
        }

        // Базовая ставка премии
        double baseBonusRate = getQuarterlyBonusRate(position);

        // Расчет премии
        BigDecimal bonus = BigDecimal.valueOf(salary)
                .multiply(BigDecimal.valueOf(baseBonusRate))
                .multiply(BigDecimal.valueOf(performanceMultiplier));

        return bonus.setScale(2, RoundingMode.HALF_UP);
    }

    private double getQuarterlyBonusRate(Positions position) {
        switch (position) {
            case TL: return 0.3;
            case PM: return 0.25;
            case DIRECTOR: return 0.4;
            case HR_MANAGER: return 0.25;
            default: return 0.2;
        }
    }
}