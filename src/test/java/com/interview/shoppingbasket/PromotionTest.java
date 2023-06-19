package com.interview.shoppingbasket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionTest {

    @Test
    void tenPercentOffTest() {
        Promotion promotion = new Promotion("code", Promotion.PromotionStrategy.TEN_PERCENT_OFF);
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(1);
        basketItem.setProductRetailPrice(10.0);
        double total = promotion.getStrategy().apply(basketItem, basketItem.getProductRetailPrice());
        assertEquals(9.0, total);
    }

    @Test
    void fiftyPercentOffTest() {
        Promotion promotion = new Promotion("code", Promotion.PromotionStrategy.FIFTY_PERCENT_OFF);
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(1);
        basketItem.setProductRetailPrice(10.0);
        double total = promotion.getStrategy().apply(basketItem, basketItem.getProductRetailPrice());
        assertEquals(5.0, total);
    }

    @ParameterizedTest
    @CsvSource({
        "1, 5.0, 5.0",
        "2, 5.0, 5.0",
        "3, 5.0, 10.0",
        "4, 5.0, 10.0",
        "5, 5.0, 15.0",
    })
    void buyTwoPayOneTest(int quantity, double price, double expected) {
        Promotion promotion = new Promotion("code", Promotion.PromotionStrategy.TWO_FOR_ONE);
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(quantity);
        basketItem.setProductRetailPrice(price);
        double total = promotion.getStrategy().apply(basketItem, quantity * price);
        assertEquals(expected, total);
    }
}