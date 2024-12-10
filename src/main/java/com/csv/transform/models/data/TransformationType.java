package com.csv.transform.models.data;

import lombok.Getter;

@Getter
public enum TransformationType {
    AMOUNT_RANGE("amountRange"),
    DATE_RANGE("dateRange"),
    HIGH_LEVEL_KEYWORD("highLevelKeyword"),
    PAYMENT_KEYWORD("paymentKeyword"),
    PURCHASE_KEYWORD("purchaseKeyword");

    private final String value;

    TransformationType(String value) {
        this.value = value;
    }
}
