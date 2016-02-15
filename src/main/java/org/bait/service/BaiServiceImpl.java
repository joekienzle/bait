package org.bait.service;

import org.bait.db.BaiImpl;
import org.bait.db.BaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaiServiceImpl implements BaiService {

    private BaiRepository baiRepository;

    @Override
    @Transactional
    public BaiImpl createBankAccountInformation(final BaiImpl baiImpl) {
        return baiRepository.save(baiImpl);
    }

    @Override
    public BaiImpl findBankAccountInformation(final String baiId) {
        return baiRepository.findOne(baiId);
    }

    @Override
    @Transactional
    public void deleteBankAccountInformation(final String baiId) {
        baiRepository.deleteByBaiId(baiId);
    }

    @Override
    @Autowired
    @Required
    public void setBaiRepository(final BaiRepository baiRepository) {
        this.baiRepository = baiRepository;
    }
}
