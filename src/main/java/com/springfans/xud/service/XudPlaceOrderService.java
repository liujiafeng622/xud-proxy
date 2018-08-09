package com.springfans.xud.service;

import java.math.BigDecimal;

public interface XudPlaceOrderService {
    void placeOrder(BigDecimal price, BigDecimal quantity,
                    String baseCurrency, String quoteCurrency,
                    String orderId);
}
