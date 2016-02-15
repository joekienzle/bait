package org.bait.service;

import org.bait.db.TransferInfoRepository;
import org.bait.db.TransferInfoImpl;

public interface TransferInfoService {
    TransferInfoImpl createTransferInformation(TransferInfoImpl transferInfoImpl);

    TransferInfoImpl findTransferInfo(String transferInfoId);

    void setTransferInfoRepository(TransferInfoRepository transferInfoRepository);
}
