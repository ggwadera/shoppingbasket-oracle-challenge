package com.interview.shoppingbasket;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.interview.shoppingbasket.Promotion.PromotionStrategy.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PromotionsFetchingCheckoutStepTest {

    @Test
    void execute() {
        PromotionsService promotionsService = Mockito.mock(PromotionsService.class);
        List<Promotion> promotions = List.of(
            new Promotion("code1", TEN_PERCENT_OFF),
            new Promotion("code2", FIFTY_PERCENT_OFF),
            new Promotion("code3", TWO_FOR_ONE)
        );

        CheckoutContext checkoutContext = Mockito.mock(CheckoutContext.class);
        Basket basket = Mockito.mock(Basket.class);

        when(checkoutContext.getBasket()).thenReturn(basket);
        when(promotionsService.getPromotions(basket)).thenReturn(promotions);

        var promotionsFetchingStep = new PromotionsFetchingCheckoutStep(promotionsService);
        promotionsFetchingStep.execute(checkoutContext);

        verify(promotionsService).getPromotions(basket);
        for (Promotion promotion : promotions) {
            verify(checkoutContext).addPromotion(promotion);
        }
    }
}