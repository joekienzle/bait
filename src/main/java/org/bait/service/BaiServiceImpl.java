package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.model.BaiDbImpl;
import org.bait.model.Bai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaiServiceImpl implements BaiService {

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

    public static BaiDbImpl createTransient(Bai bai) {
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
    public void deleteBankAccountInformation(final String baiId) {
        baiRepository.deleteByBaiId(baiId);
    }

    @Autowired
    @Required
    public void setBaiRepository(final BaiRepository baiRepository) {
        this.baiRepository = baiRepository;
    }
}
