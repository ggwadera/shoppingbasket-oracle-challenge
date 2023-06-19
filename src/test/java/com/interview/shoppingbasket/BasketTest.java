package com.interview.shoppingbasket;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BasketTest {
    @Test
    void emptyBasket() {
        Basket basket = new Basket();
        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 0);
    }

    @Test
    void createBasketFullConstructor() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 1);
        assertEquals(basketSize.get(0).getProductCode(), "productCode");
        assertEquals(basketSize.get(0).getProductName(), "myProduct");
        assertEquals(basketSize.get(0).getQuantity(), 10);
    }

    @Test
    void createBasketWithMultipleProducts() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        basket.add("productCode2", "myProduct2", 10);
        basket.add("productCode3", "myProduct3", 10);

        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(),3);
        assertEquals(basketSize.get(0).getProductCode(), "productCode");
        assertEquals(basketSize.get(0).getProductName(), "myProduct");
        assertEquals(basketSize.get(0).getQuantity(), 10);
        assertEquals(basketSize.get(1).getProductCode(), "productCode2");
        assertEquals(basketSize.get(1).getProductName(), "myProduct2");
        assertEquals(basketSize.get(1).getQuantity(), 10);
        assertEquals(basketSize.get(2).getProductCode(), "productCode3");
        assertEquals(basketSize.get(2).getProductName(), "myProduct3");
        assertEquals(basketSize.get(2).getQuantity(), 10);
    }

    @Test
    void consolidateBasketTest() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        basket.add("productCode", "myProduct", 5);
        basket.add("productCode2", "myProduct2", 10);

        List<BasketItem> items = basket.getItems();
        assertEquals(items.size(),3);

        basket.consolidateItems();
        List<BasketItem> consolidated = basket.getItems();
        assertNotEquals(items, consolidated); // assumption, the instance is replaced
        assertEquals(consolidated.size(), 2);
        assertEquals(consolidated.get(0).getProductCode(), "productCode");
        assertEquals(consolidated.get(0).getProductName(), "myProduct");
        assertEquals(consolidated.get(0).getQuantity(), 15);
        assertEquals(consolidated.get(1).getProductCode(), "productCode2");
        assertEquals(consolidated.get(1).getProductName(), "myProduct2");
        assertEquals(consolidated.get(1).getQuantity(), 10);
    }
}
