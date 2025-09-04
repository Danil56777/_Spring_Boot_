package ru.arkhipov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    /**
     * Уникальный идентификатор сообщения
     */
    @NotBlank(message = "uid обязателен")
    @Size(max = 32, message = "uid должен быть не длиннее 32 символов")
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    @NotBlank(message = "operationUid обязателен")
    @Size(max = 32, message = "operationUid должен быть не длиннее 32 символов")
    private String operationUid;

    /**
     * Имя системы отправителя
     */
    private Systems systemName;

    /**
     * Время создания сообщения
     */
    @NotBlank(message = "systemTime обязателен")
    private String systemTime;

    /**
     * Наименование ресурса
     */
    private String source;

    /**
     * Должность сотрудника
     */
    @NotNull(message = "position обязателен")
    private Positions position;

    /**
     * Заработная плата сотрудника
     */
    @Positive(message = "salary должен быть больше 0")
    private double salary;

    /**
     * Квартал для расчета премии (1-4)
     */
    @Min(value = 1, message = "quarter должен быть от 1 до 4")
    @Max(value = 4, message = "quarter должен быть от 1 до 4")
    private int quarter;

    /**
     * Коэффициент эффективности для расчета премии
     */
    @Positive(message = "performanceMultiplier должен быть больше 0")
    private double performanceMultiplier;

    /**
     * Коэффициент годового бонуса
     */
    @PositiveOrZero(message = "bonus не может быть отрицательным")
    private double bonus;

    /**
     * Количество рабочих дней в году
     */
    @Min(value = 0, message = "workDays не может быть меньше 0")
    @Max(value = 366, message = "workDays не может быть больше 366")
    private int workDays;

    /**
     * Идентификатор коммуникации
     */
    @Min(value = 1, message = "communicationId должен быть не меньше 1")
    @Max(value = 100000, message = "communicationId должен быть не больше 100000")
    private int communicationId;

    /**
     * Идентификатор шаблона
     */
    private int templateId;

    /**
     * Код продукта
     */
    private int productCode;

    /**
     * СМС код
     */
    private int smsCode;

    @Override
    public String toString() {
        return "{"
                + "uid='" + uid + '\''
                + ", operationUid='" + operationUid + '\''
                + ", systemName='" + systemName + '\''
                + ", systemTime='" + systemTime + '\''
                + ", source='" + source + '\''
                + ", position='" + position + '\''
                + ", salary=" + salary
                + ", bonus=" + bonus
                + ", workDays=" + workDays
                + ", communicationId=" + communicationId
                + ", templateId=" + templateId
                + ", productCode=" + productCode
                + ", smsCode=" + smsCode
                + "}";
    }
}