package com.interview.shoppingbasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RetailPriceCheckoutStepTest {

    PricingService pricingService;
    CheckoutContext checkoutContext;
    Basket basket;

    @BeforeEach
    void setup() {
        pricingService = mock(PricingService.class);
        checkoutContext = mock(CheckoutContext.class);
        basket = new Basket();

        when(checkoutContext.getBasket()).thenReturn(basket);
    }

    @Test
    void setPriceZeroForEmptyBasket() {

        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);

        verify(checkoutContext).setRetailPriceTotal(0.0);
    }

    @Test
    void setPriceOfProductToBasketItem() {

        basket.add("product1", "myproduct1", 10);
        basket.add("product2", "myproduct2", 10);

        when(pricingService.getPrice("product1")).thenReturn(3.99);
        when(pricingService.getPrice("product2")).thenReturn(2.0);
        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);
        verify(checkoutContext).setRetailPriceTotal(3.99 * 10 + 2 * 10);
    }

    @Test
    void setPriceOfProductToBasketItemWithPromotion() {

        basket.add("product1", "myproduct1", 10);
        basket.add("product2", "myproduct2", 10);

        when(pricingService.getPrice("product1")).thenReturn(3.99);
        when(pricingService.getPrice("product2")).thenReturn(2.0);


        var promotion = mock(Promotion.class);
        var strategy = mock(Promotion.PromotionStrategy.class);
        BasketItem item = basket.getItems().get(0);
        when(checkoutContext.getPromotion(item)).thenReturn(promotion);
        when(promotion.getStrategy()).thenReturn(strategy);
        when(strategy.apply(eq(item), anyDouble())).thenReturn(20.0);
        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);

        retailPriceCheckoutStep.execute(checkoutContext);
        verify(checkoutContext).setRetailPriceTotal(20.0 + 2 * 10);
    }

}
