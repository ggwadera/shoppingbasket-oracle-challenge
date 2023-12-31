package com.interview.shoppingbasket;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PromotionsFetchingCheckoutStep implements CheckoutStep {

    private final PromotionsService promotionsService;

    @Override
    public void execute(CheckoutContext checkoutContext) {
        promotionsService.getPromotions(checkoutContext.getBasket())
            .forEach(checkoutContext::addPromotion);
    }
}
