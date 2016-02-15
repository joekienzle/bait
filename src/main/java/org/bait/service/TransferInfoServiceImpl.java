package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.TransferInfoRepository;
import org.bait.model.Bai;
import org.bait.model.TransferInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
public class TransferInfoServiceImpl implements TransferInfoService {

    private TransferInfoRepository transferInfoRepository;

    private BaiRepository baiRepository;

    @Override
    public TransferInfo createTransferInformation(TransferInfo transferInfo, String baiId) {
        Bai bai = baiRepository.findOne(baiId);
        transferInfo.setBai(bai);
        return transferInfoRepository.save(transferInfo);
    }

    @Override
    public TransferInfo findTransferInfo(String baiId, String transferInfoId) {
        return null;
    }

    @Override
    @Autowired
    @Required
    public void setTransferInfoRepository(final TransferInfoRepository transferInfoRepository) {
        this.transferInfoRepository = transferInfoRepository;
    }

    @Autowired
    @Required
    public void setBaiRepository(final BaiRepository baiRepository) {
        this.baiRepository = baiRepository;
    }
}
