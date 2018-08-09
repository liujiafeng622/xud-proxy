package com.springfans.xud.service;

import com.springfans.xud.rpc.XudClient;
import com.springfans.xud.rpc.xud.CancelOrderRequest;
import com.springfans.xud.rpc.xud.CancelOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XudCancelOrderServiceImpl implements XudCancelOrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private XudClient xudClient;

    @Override
    public boolean cancelOrder(String baseCurrency, String quoteCurrency, String orderId) {

        CancelOrderRequest request = CancelOrderRequest.newBuilder()
                .setPairId(baseCurrency + "/" + quoteCurrency)
                .setOrderId(orderId)
                .build();
        CancelOrderResponse response = xudClient.cancelOrder(request);
        return response.getCanceled();
    }
}
