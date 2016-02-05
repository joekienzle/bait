package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.Bai;

public class BaiServiceImpl implements BaiService {

    BaiRepository baiRepository;

    @Override
    public Bai createBankAccountInformation(final Bai bai) {
        return baiRepository.saveBankAccountInformation(bai);
    }

    @Override
    public Bai findBankAccountInformation(final String baiId) {
        return baiRepository.findBankAccountInformationById(baiId);
    }

    @Override
    public void setBaiRepository(final BaiRepository baiRepository) {
        this.baiRepository = baiRepository;
    }
}
