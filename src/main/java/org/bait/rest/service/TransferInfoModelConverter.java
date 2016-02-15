package org.bait.rest.service;

import org.bait.model.TransferInfo;
import org.bait.rest.model.TransferInfoJsonImpl;

public class TransferInfoModelConverter {
    public static TransferInfoJsonImpl convert(final TransferInfo transferInfo) {
        TransferInfoJsonImpl transferInfoJson = new TransferInfoJsonImpl();
        transferInfoJson.setTransferId(transferInfo.getTransferId());
        transferInfoJson.setAmount(transferInfo.getAmount());
        transferInfoJson.setSubject(transferInfo.getSubject());
        transferInfoJson.setBaiId(transferInfo.getBaiId());
        return transferInfoJson;
    }
}
