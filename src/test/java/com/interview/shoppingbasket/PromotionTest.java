package com.interview.shoppingbasket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.interview.shoppingbasket.Promotion.PromotionStrategy.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionTest {

    @Test
    void tenPercentOffTest() {
        BasketItem basketItem = getBasketItem(1, 10.0);
        double total = TEN_PERCENT_OFF.apply(basketItem, basketItem.getProductRetailPrice());
        assertEquals(9.0, total);
    }

    @Test
    void fiftyPercentOffTest() {
        BasketItem basketItem = getBasketItem(1, 10.0);
        double total = FIFTY_PERCENT_OFF.apply(basketItem, basketItem.getProductRetailPrice());
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
        BasketItem basketItem = getBasketItem(quantity, price);
        double total = TWO_FOR_ONE.apply(basketItem, quantity * price);
        assertEquals(expected, total);
    }

    private static BasketItem getBasketItem(int quantity, double price) {
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(quantity);
        basketItem.setProductRetailPrice(price);
        return basketItem;
    }
}