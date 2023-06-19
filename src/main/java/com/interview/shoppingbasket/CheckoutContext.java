package com.interview.shoppingbasket;

import java.util.HashMap;
import java.util.Map;

class CheckoutContext {
    private final Basket basket;
    private double retailPriceTotal = 0.0;
    private final Map<String, Promotion> promotionsByProductCode = new HashMap<>();

    public void setRetailPriceTotal(double retailPriceTotal) {
        this.retailPriceTotal = retailPriceTotal;
    }

    CheckoutContext(Basket basket) {
        this.basket = basket;
    }

    public PaymentSummary paymentSummary() {
        return new PaymentSummary(retailPriceTotal);
    }


    public Basket getBasket() {
        return basket;
    }

    public Promotion getPromotion(BasketItem item) {
        return promotionsByProductCode.get(item.getProductCode());
    }

    public void addPromotion(Promotion promotion) {
        this.promotionsByProductCode.put(promotion.getProductCode(), promotion);
    }
}
