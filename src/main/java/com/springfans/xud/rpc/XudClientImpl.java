package com.springfans.xud.rpc;

import com.springfans.xud.rpc.xud.*;
import com.springfans.xud.util.XudProxyProperties;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class XudClientImpl implements XudClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private XudProxyProperties xudProxyProperties;

    @Override
    public List<Pair> getPairs() {
        ManagedChannel channel = buildChannel();

        try {
            XudGrpc.XudBlockingStub blockingStub = XudGrpc.newBlockingStub(channel);
            GetPairsResponse getPairsResponse = blockingStub.getPairs(GetPairsRequest.newBuilder().build());
            return getPairsResponse.getPairsList();
        } finally {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("shutdown channel error:", e);
            }
        }
    }

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
        ManagedChannel channel = buildChannel();

        try {
            XudGrpc.XudBlockingStub blockingStub = XudGrpc.newBlockingStub(channel);
            return blockingStub.placeOrder(request);
        } finally {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("shutdown channel error:", e);
            }
        }
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest request) {
        ManagedChannel channel = buildChannel();

        try {
            XudGrpc.XudBlockingStub blockingStub = XudGrpc.newBlockingStub(channel);
            return blockingStub.cancelOrder(request);
        } finally {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("shutdown channel error:", e);
            }
        }
    }

    private ManagedChannel buildChannel(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(xudProxyProperties.getHost(), xudProxyProperties.getPort())
                .usePlaintext(true)
                .build();
        return channel;
    }
}
