package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnnualBonusServiceImplTest {

    @Test
    void calculate_nonLeapYear() {
        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;
        String systemTime = "2025-05-20"; // 2025 год НЕ високосный - 365 дней

        // when
        double result = new AnnualBonusServiceImpl()
                .calculate(position, salary, bonus, workDays, systemTime);

        // then
        double expected = salary * bonus * (workDays / 365.0);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculate_leapYear() {
        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;
        String systemTime = "2024-05-20"; // 2024 год високосный - 366 дней

        // when
        double result = new AnnualBonusServiceImpl()
                .calculate(position, salary, bonus, workDays, systemTime);

        // then
        double expected = salary * bonus * (workDays / 366.0);
        assertThat(result).isEqualTo(expected);
    }
}
