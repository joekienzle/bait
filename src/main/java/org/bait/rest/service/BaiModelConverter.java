package org.bait.rest.service;

import org.bait.model.Bai;
import org.bait.rest.model.BaiJsonImpl;

public class BaiModelConverter {
    public static BaiJsonImpl convert(final Bai bai) {
        BaiJsonImpl baiJsonImpl = new BaiJsonImpl();
        baiJsonImpl.setBaiId(bai.getBaiId());
        baiJsonImpl.setAccountNumber(bai.getAccountNumber());
        baiJsonImpl.setBankNumber(bai.getBankNumber());
        baiJsonImpl.setBankName(bai.getBankName());
        return baiJsonImpl;
    }
}
