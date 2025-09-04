package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuarterlyBonusServiceTest {

    @InjectMocks
    private QuarterlyBonusService quarterlyBonusService;

    @Test
    void testCalculate_ForManager_ShouldReturnCorrectBonus() {
        // Arrange
        Positions position = Positions.TL;
        double salary = 100000.0;
        int quarter = 2;
        double performanceMultiplier = 1.2;

        // Act
        BigDecimal result = quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);

        // Assert
        BigDecimal expected = BigDecimal.valueOf(100000)
                .multiply(BigDecimal.valueOf(0.3))
                .multiply(BigDecimal.valueOf(1.2))
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        assertEquals(expected, result);
        assertEquals(36000.00, result.doubleValue());
    }

    @Test
    void testCalculate_ForNonManager_ShouldReturnZero() {
        // Arrange
        Positions position = Positions.DEV;
        double salary = 100000.0;
        int quarter = 2;
        double performanceMultiplier = 1.2;

        // Act
        BigDecimal result = quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);

        // Assert
        assertEquals(BigDecimal.ZERO, result);
        assertEquals(0.00, result.doubleValue());
    }

    @Test
    void testCalculate_ForDifferentManagers_ShouldReturnDifferentRates() {
        // Arrange
        double salary = 100000.0;
        int quarter = 2;
        double performanceMultiplier = 1.0;

        // Act & Assert для разных управленцев
        BigDecimal tlBonus = quarterlyBonusService.calculate(Positions.TL, salary, quarter, performanceMultiplier);
        BigDecimal pmBonus = quarterlyBonusService.calculate(Positions.PM, salary, quarter, performanceMultiplier);
        BigDecimal directorBonus = quarterlyBonusService.calculate(Positions.DIRECTOR, salary, quarter, performanceMultiplier);
        BigDecimal hrManagerBonus = quarterlyBonusService.calculate(Positions.HR_MANAGER, salary, quarter, performanceMultiplier);

        // Assert
        assertEquals(30000.00, tlBonus.doubleValue()); // 30%
        assertEquals(25000.00, pmBonus.doubleValue());  // 25%
        assertEquals(40000.00, directorBonus.doubleValue()); // 40%
        assertEquals(25000.00, hrManagerBonus.doubleValue()); // 25%
    }

    @Test
    void testCalculate_WithDifferentQuarters_ShouldWorkForAllQuarters() {
        // Arrange
        Positions position = Positions.TL;
        double salary = 100000.0;
        double performanceMultiplier = 1.0;

        // Act & Assert для всех кварталов
        for (int quarter = 1; quarter <= 4; quarter++) {
            BigDecimal result = quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);
            assertNotNull(result);
            assertTrue(result.doubleValue() > 0);
        }
    }

    @Test
    void testCalculate_WithZeroPerformanceMultiplier_ShouldThrowException() {
        // Arrange
        Positions position = Positions.TL;
        double salary = 100000.0;
        int quarter = 2;
        double performanceMultiplier = 0.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);
        });

        assertEquals("Коэффициент эффективности должен быть положительным", exception.getMessage());
    }

    @Test
    void testCalculate_WithNegativePerformanceMultiplier_ShouldThrowException() {
        // Arrange
        Positions position = Positions.TL;
        double salary = 100000.0;
        int quarter = 2;
        double performanceMultiplier = -1.5;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);
        });

        assertEquals("Коэффициент эффективности должен быть положительным", exception.getMessage());
    }

    @Test
    void testCalculate_WithInvalidQuarter_ShouldThrowException() {
        // Arrange
        Positions position = Positions.TL;
        double salary = 100000.0;
        int quarter = 5;
        double performanceMultiplier = 1.2;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);
        });

        assertEquals("Квартал должен быть в диапазоне 1-4", exception.getMessage());
    }

    @Test
    void testCalculate_WithZeroQuarter_ShouldThrowException() {
        // Arrange
        Positions position = Positions.TL;
        double salary = 100000.0;
        int quarter = 0;
        double performanceMultiplier = 1.2;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);
        });

        assertEquals("Квартал должен быть в диапазоне 1-4", exception.getMessage());
    }

    @Test
    void testCalculate_WithDifferentSalaries_ShouldCalculateCorrectly() {
        // Arrange
        Positions position = Positions.TL;
        int quarter = 2;
        double performanceMultiplier = 1.0;

        // Test cases: [salary, expectedBonus]
        double[][] testCases = {
                {50000.0, 15000.0},   // 50k * 30% = 15k
                {100000.0, 30000.0},  // 100k * 30% = 30k
                {150000.0, 45000.0},  // 150k * 30% = 45k
                {200000.0, 60000.0}   // 200k * 30% = 60k
        };

        for (double[] testCase : testCases) {
            double salary = testCase[0];
            double expectedBonus = testCase[1];

            // Act
            BigDecimal result = quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);

            // Assert
            assertEquals(expectedBonus, result.doubleValue(), 0.01);
        }
    }

    @Test
    void testCalculate_WithDifferentPerformanceMultipliers_ShouldCalculateCorrectly() {
        // Arrange
        Positions position = Positions.TL;
        double salary = 100000.0;
        int quarter = 2;

        // Test cases: [multiplier, expectedBonus]
        double[][] testCases = {
                {0.5, 15000.0},   // 100k * 30% * 0.5 = 15k
                {1.0, 30000.0},   // 100k * 30% * 1.0 = 30k
                {1.5, 45000.0},   // 100k * 30% * 1.5 = 45k
                {2.0, 60000.0}    // 100k * 30% * 2.0 = 60k
        };

        for (double[] testCase : testCases) {
            double multiplier = testCase[0];
            double expectedBonus = testCase[1];

            // Act
            BigDecimal result = quarterlyBonusService.calculate(position, salary, quarter, multiplier);

            // Assert
            assertEquals(expectedBonus, result.doubleValue(), 0.01);
        }
    }

    @Test
    void testCalculate_ResultShouldBeRoundedToTwoDecimals() {
        // Arrange
        Positions position = Positions.TL;
        double salary = 123456.78;
        int quarter = 2;
        double performanceMultiplier = 1.123;

        // Act
        BigDecimal result = quarterlyBonusService.calculate(position, salary, quarter, performanceMultiplier);

        // Assert
        assertEquals(2, result.scale()); // Проверяем что 2 знака после запятой
        assertEquals(41579.98, result.doubleValue(), 0.001); // Округленное значение
    }
}