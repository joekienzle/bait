package org.bait.model;

public interface TransferInfo {
    Bai getBai();

    void setBai(Bai bai);

    String getTransferId();

    String getSubject();

    void setSubject(String subject);

    String getAmount();

    void setAmount(String amount);
}
