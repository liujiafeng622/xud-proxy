package com.springfans.xud.service;

import com.springfans.xud.rpc.XudClient;
import com.springfans.xud.rpc.xud.Order;
import com.springfans.xud.rpc.xud.PlaceOrderRequest;
import com.springfans.xud.rpc.xud.PlaceOrderResponse;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static io.grpc.Status.ALREADY_EXISTS;

@Service
public class XudPlaceOrderServiceImpl implements XudPlaceOrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private XudClient xudClient;

    @Override
    public void placeOrder(BigDecimal price, BigDecimal quantity, String baseCurrency, String quoteCurrency, String orderId) {
        Order order = Order.newBuilder()
                .setPrice(price.doubleValue())
                .setQuantity(quantity.doubleValue())
                .setPairId(baseCurrency + "/" + quoteCurrency)
                .setCreatedAt(new Date().getTime())
                .setLocalId(orderId)
                .build();

        PlaceOrderRequest request = PlaceOrderRequest.newBuilder()
                .setOrder(order)
                .build();
        try {
            PlaceOrderResponse response = xudClient.placeOrder(request);
            logger.info(response.getRemainingOrder().getId());
        } catch (Exception e) {
            if (e instanceof StatusRuntimeException) {
                Status status = ((StatusRuntimeException) e).getStatus();
                if (status.equals(ALREADY_EXISTS)) {
                    logger.error("ALREADY_EXISTS: ", e.getMessage());
                }
            }
            logger.error("placeOrder error:", e);
            throw e;
        }
    }

}
