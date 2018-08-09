package com.springfans.xud.service;

public interface XudCancelOrderService {
    boolean cancelOrder(String baseCurrency, String quoteCurrency, String orderId);
}
