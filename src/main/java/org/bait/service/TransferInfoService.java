package org.bait.service;

import org.bait.db.TransferInfoRepository;
import org.bait.db.model.TransferInfoDbImpl;

public interface TransferInfoService {
    TransferInfoDbImpl createTransferInformation(TransferInfoDbImpl transferInfoDbImpl);

    TransferInfoDbImpl findTransferInfo(String transferInfoId);

    void setTransferInfoRepository(TransferInfoRepository transferInfoRepository);
}
