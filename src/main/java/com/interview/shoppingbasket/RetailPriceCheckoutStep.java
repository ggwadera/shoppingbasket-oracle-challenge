package com.interview.shoppingbasket;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private final PricingService pricingService;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        double retailTotal = 0.0;

        for (BasketItem basketItem: basket.getItems()) {
            int quantity = basketItem.getQuantity();
            double price = pricingService.getPrice(basketItem.getProductCode());
            basketItem.setProductRetailPrice(price);
            double itemTotal = quantity * price;
            Promotion promotion = checkoutContext.getPromotion(basketItem);
            retailTotal += applyPromotion(promotion, basketItem, itemTotal);
        }

        checkoutContext.setRetailPriceTotal(retailTotal);
    }

    /**
     * Applies the promotion if found onto the total price of the item
     * @param promotion promotion for the product code, can be null
     * @param item the basket item to apply the promotion
     * @param price the total price for this item
     * @return the adjusted total price if a promotion exists, else the original total
     */
    public double applyPromotion(Promotion promotion, BasketItem item, double price) {
        return promotion != null
            ? promotion.getStrategy().apply(item, price)
            : price;
    }
}
