package com.springfans.xud.domain;

public class Pair {
    private String id;
    private String baseCurrency;
    private String quoteCurrency;
    private String swapProtocol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getSwapProtocol() {
        return swapProtocol;
    }

    public void setSwapProtocol(String swapProtocol) {
        this.swapProtocol = swapProtocol;
    }
}
