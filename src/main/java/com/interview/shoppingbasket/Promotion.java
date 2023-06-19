package com.interview.shoppingbasket;

import lombok.Data;

@Data
public class Promotion {

    private final String productCode;
    private final PromotionStrategy strategy;

    public enum PromotionStrategy {

        TEN_PERCENT_OFF {
            @Override
            public double apply(BasketItem item, double totalPrice) {
                return totalPrice * 0.9;
            }
        },

        FIFTY_PERCENT_OFF {
            @Override
            public double apply(BasketItem item, double totalPrice) {
                return totalPrice * 0.5;
            }
        },

        TWO_FOR_ONE {
            @Override
            public double apply(BasketItem item, double totalPrice) {
                int halfQuantity = item.getQuantity() / 2;
                return totalPrice - item.getProductRetailPrice() * halfQuantity;
            }
        };

        public abstract double apply(BasketItem item, double totalPrice);
    }
}
