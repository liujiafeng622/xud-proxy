package com.springfans.xud.rpc;

import com.springfans.xud.rpc.xud.*;

import java.util.List;

public interface XudClient {
    List<Pair> getPairs();

    PlaceOrderResponse placeOrder(PlaceOrderRequest request);

    CancelOrderResponse cancelOrder(CancelOrderRequest request);
}
