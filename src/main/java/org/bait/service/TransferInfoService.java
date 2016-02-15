package org.bait.service;

import org.bait.db.TransferInfoRepository;
import org.bait.model.TransferInfo;

public interface TransferInfoService {
    TransferInfo createTransferInformation(TransferInfo transferInfo, String baiId);

    TransferInfo findTransferInfo(String baiId, String transferInfoId);

    void setTransferInfoRepository(TransferInfoRepository transferInfoRepository);
}
