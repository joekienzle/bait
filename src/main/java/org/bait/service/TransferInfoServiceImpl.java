package org.bait.service;

import org.bait.db.TransferInfoRepository;
import org.bait.model.TransferInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
public class TransferInfoServiceImpl implements TransferInfoService {

    private TransferInfoRepository transferInfoRepository;

    @Override
    public TransferInfo createTransferInformation(TransferInfo transferInfo) {
        return transferInfoRepository.save(transferInfo);
    }

    @Override
    public TransferInfo findTransferInfo(String transferInfoId) {
        return null;
    }

    @Override
    @Autowired
    @Required
    public void setTransferInfoRepository(final TransferInfoRepository transferInfoRepository) {
        this.transferInfoRepository = transferInfoRepository;
    }

}
