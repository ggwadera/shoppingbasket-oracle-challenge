package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = new BasketItem();
        basketItem.setProductCode(productCode);
        basketItem.setProductName(productName);
        basketItem.setQuantity(quantity);

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    /**
     * Consolidates the basket items by product code.
     * The items are grouped in a map by their product code, and their quantities are merged.
     * The items list is then replaced by the new consolidated items list.
     */
    public void consolidateItems() {
        // Use a linked hash map here to maintain insertion order
        Map<String, BasketItem> itemsByProductCode = new LinkedHashMap<>();
        for (BasketItem item : items) {
            itemsByProductCode.merge(item.getProductCode(), item, Basket::mergeQuantities);
        }
        this.items = new ArrayList<>(itemsByProductCode.values());
        /* TODO
         * - Can the product name be different for a same product code? If yes, what should be expected?
         * - Can the list instance be replaced or should it be modified in-place? If not, the list should be final.
         */
    }

    private static BasketItem mergeQuantities(BasketItem item1, BasketItem item2) {
        int mergedQuantity = item1.getQuantity() + item2.getQuantity();
        item1.setQuantity(mergedQuantity);
        return item1;
    }
}
