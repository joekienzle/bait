package org.bait.service;

import org.bait.db.TransferInfoRepository;
import org.bait.db.TransferInfoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
public class TransferInfoServiceImpl implements TransferInfoService {

    private TransferInfoRepository transferInfoRepository;

    @Override
    public TransferInfoImpl createTransferInformation(TransferInfoImpl transferInfoImpl) {
        return transferInfoRepository.save(transferInfoImpl);
    }

    @Override
    public TransferInfoImpl findTransferInfo(String transferInfoId) {
        return transferInfoRepository.findOne(transferInfoId);
    }

    @Override
    @Autowired
    @Required
    public void setTransferInfoRepository(final TransferInfoRepository transferInfoRepository) {
        this.transferInfoRepository = transferInfoRepository;
    }

}
