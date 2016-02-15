package org.bait.service;

import org.bait.db.TransferInfoRepository;
import org.bait.db.model.BaiDbImpl;
import org.bait.db.model.TransferInfoDbImpl;
import org.bait.model.Bai;
import org.bait.model.TransferInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
public class TransferInfoServiceImpl implements TransferInfoService {

    private TransferInfoRepository transferInfoRepository;

    @Override
    public TransferInfo createTransferInformation(TransferInfo transferInfo) {
        TransferInfoDbImpl transferInfoTransient = createTransient(transferInfo);
        Bai baiTransient = BaiServiceImpl.createTransient();
        baiTransient.setBaiId(transferInfo.getBaiId());
        transferInfoTransient.setBai(baiTransient);
        return transferInfoRepository.save(transferInfoTransient);
    }

    public static TransferInfoDbImpl createTransient(TransferInfo transferInfo) {
        TransferInfoDbImpl transferInfoTransient = new TransferInfoDbImpl();
        transferInfoTransient.setSubject(transferInfo.getSubject());
        transferInfoTransient.setAmount(transferInfo.getAmount());
        return transferInfoTransient;
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
