package com.interview.shoppingbasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.interview.shoppingbasket.Promotion.PromotionStrategy.TEN_PERCENT_OFF;
import static com.interview.shoppingbasket.Promotion.PromotionStrategy.TWO_FOR_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckoutPipelineTest {

    CheckoutPipeline checkoutPipeline;

    @Mock
    Basket basket;

    @Mock
    PromotionsService promotionsService;

    @Mock
    PricingService pricingService;

    @BeforeEach
    void setup() {
        checkoutPipeline = new CheckoutPipeline();
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {
        checkoutPipeline.addStep(new BasketConsolidationCheckoutStep());
        checkoutPipeline.addStep(new PromotionsFetchingCheckoutStep(promotionsService));
        checkoutPipeline.addStep(new RetailPriceCheckoutStep(pricingService));

        var checkoutBasket = new Basket();
        checkoutBasket.add("code1", "name1", 5);
        checkoutBasket.add("code1", "name1", 5);
        checkoutBasket.add("code2", "name2", 5);
        checkoutBasket.add("code3", "name3", 10);

        when(promotionsService.getPromotions(checkoutBasket)).thenReturn(List.of(
            new Promotion("code1", TEN_PERCENT_OFF),
            new Promotion("code2", TWO_FOR_ONE)
        ));

        when(pricingService.getPrice("code1")).thenReturn(10.0);
        when(pricingService.getPrice("code2")).thenReturn(2.0);
        when(pricingService.getPrice("code3")).thenReturn(1.0);

        PaymentSummary paymentSummary = checkoutPipeline.checkout(checkoutBasket);
        assertEquals(90.0 + 6.0 + 10.0, paymentSummary.getRetailTotal());
    }

}
