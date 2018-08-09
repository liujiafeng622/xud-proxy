package com.springfans.xud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springfans.xud.domain.Pair;
import com.springfans.xud.rpc.XudClient;
import com.springfans.xud.service.XudCancelOrderService;
import com.springfans.xud.service.XudPlaceOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/xud")
@RestController
public class XudProxyServiceController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private XudPlaceOrderService xudPlaceOrderService;

    @Autowired
    private XudCancelOrderService xudCancelOrderService;

    @Autowired
    private XudClient xudClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/place-order")
    public void placeOrder(
            @RequestParam BigDecimal price,
            @RequestParam BigDecimal quantity,
            @RequestParam String baseCurrency,
            @RequestParam String quoteCurrency,
            @RequestParam String orderId) {
        logger.info("placeOrder req: price {}, quantity {}, baseCurrency {}, quoteCurrency {}, orderId {}", price, quantity, baseCurrency, quoteCurrency, orderId);
        xudPlaceOrderService.placeOrder(price, quantity, baseCurrency, quoteCurrency, orderId);
    }

    @PostMapping("/cancel-order")
    public boolean cancelOrder(
            @RequestParam String baseCurrency,
            @RequestParam String quoteCurrency,
            @RequestParam String orderId) {
        logger.info("cancelOrder req: baseCurrency {}, quoteCurrency {}, orderId {}", baseCurrency, quoteCurrency, orderId);
        return xudCancelOrderService.cancelOrder(baseCurrency, quoteCurrency, orderId);
    }

    @GetMapping("/pairs")
    public List<Pair> getPairs() {
        logger.info("getPairs req:");
        return changePair(xudClient.getPairs());
    }

    private List<Pair> changePair(List<com.springfans.xud.rpc.xud.Pair> pairList) {
        List<Pair> pairs = new ArrayList<>();
        for (com.springfans.xud.rpc.xud.Pair xudPair : pairList) {
            Pair pair = new Pair();
            pair.setId(xudPair.getId());
            pair.setBaseCurrency(xudPair.getBaseCurrency());
            pair.setQuoteCurrency(xudPair.getQuoteCurrency());
            pair.setSwapProtocol(xudPair.getSwapProtocol());
            pairs.add(pair);
        }
        return pairs;
    }

}
