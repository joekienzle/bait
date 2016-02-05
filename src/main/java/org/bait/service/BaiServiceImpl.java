package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.Bai;

public class BaiServiceImpl implements BaiService {

    BaiRepository baiRepository;

    @Override
    public Bai createBankAccountInformation(Bai bai) {
        return baiRepository.saveBankAccountInformation(bai);
    }

    @Override
    public Bai findBankAccountInformation(String id) {
        return baiRepository.findBankAccountInformationById(id);
    }

    @Override
    public void setBaiRepository(BaiRepository baiRepository) {
        this.baiRepository = baiRepository;
    }
}
