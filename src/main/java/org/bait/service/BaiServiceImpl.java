package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.model.BaiDbImpl;
import org.bait.model.Bai;
import org.bait.model.TransferInfo;
import org.bait.service.api.BaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

@Service
public class BaiServiceImpl implements BaiService {

    public static final String BAI_CANT_BE_DELETED_MESSAGE = "Bai cannot be deleted. Transfer object '%s' depend upon it. Delete them first in order to delete Bai.";

    private BaiRepository baiRepository;

    @Override
    @Transactional
    public Bai createBankAccountInformation(final Bai bai) {
        BaiDbImpl baiTransient = createTransient(bai);
        return baiRepository.save(baiTransient);
    }

    public static BaiDbImpl createTransient() {
        return new BaiDbImpl();
    }

    public static BaiDbImpl createTransient(final Bai bai) {
        BaiDbImpl baiDbImpl = new BaiDbImpl();
        baiDbImpl.setAccountNumber(bai.getAccountNumber());
        baiDbImpl.setBankNumber(bai.getBankNumber());
        baiDbImpl.setBankName(bai.getBankName());
        return baiDbImpl;
    }

    @Override
    public Bai findBankAccountInformation(final String baiId) {
        return baiRepository.findOne(baiId);
    }

    @Override
    @Transactional
    public void deleteBankAccountInformation(final String baiId) throws PreconditionFailedException {
        BaiDbImpl bai = baiRepository.findOne(baiId);
        Collection<String> transferInfoIdsForBai = bai.getTransferInfoIds();
        if (transferInfoIdsForBai != null && !transferInfoIdsForBai.isEmpty()) {
            throw new PreconditionFailedException(String.format(BAI_CANT_BE_DELETED_MESSAGE, transferInfoIdsForBai));
        }
        baiRepository.deleteByBaiId(baiId);
    }

    @Autowired
    @Required
    public void setBaiRepository(final BaiRepository baiRepository) {
        this.baiRepository = baiRepository;
    }
}
