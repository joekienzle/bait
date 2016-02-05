package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.Bai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
public class BaiServiceImpl implements BaiService {

    private BaiRepository baiRepository;

    @Override
    public Bai createBankAccountInformation(final Bai bai) {
        return baiRepository.saveBankAccountInformation(bai);
    }

    @Override
    public Bai findBankAccountInformation(final String baiId) {
        return baiRepository.findBankAccountInformationById(baiId);
    }

    @Override
    @Autowired
    @Required
    public void setBaiRepository(final BaiRepository baiRepository) {
        this.baiRepository = baiRepository;
    }
}
