package org.bait.service;

import org.bait.db.TransferInfoRepository;
import org.bait.db.model.TransferInfoDbImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
public class TransferInfoServiceImpl implements TransferInfoService {

    private TransferInfoRepository transferInfoRepository;

    @Override
    public TransferInfoDbImpl createTransferInformation(TransferInfoDbImpl transferInfoDbImpl) {
        return transferInfoRepository.save(transferInfoDbImpl);
    }

    @Override
    public TransferInfoDbImpl findTransferInfo(String transferInfoId) {
        return transferInfoRepository.findOne(transferInfoId);
    }

    @Autowired
    @Required
    public void setTransferInfoRepository(final TransferInfoRepository transferInfoRepository) {
        this.transferInfoRepository = transferInfoRepository;
    }

}
