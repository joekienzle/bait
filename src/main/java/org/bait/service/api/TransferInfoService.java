package org.bait.service.api;

import org.bait.model.TransferInfo;

public interface TransferInfoService {
    TransferInfo createTransferInformation(TransferInfo transferInfo);

    TransferInfo findTransferInfo(String transferInfoId);

    void deleteTransferInformation(String transferId);
}
