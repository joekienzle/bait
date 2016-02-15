package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.model.BaiDbImpl;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BaiServiceImplTest {

    @Test
    public void repositoryIsInvokedOnCreate() {
        BaiServiceImpl baiService = new BaiServiceImpl();
        BaiRepository baiRepository = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepository);

        baiService.createBankAccountInformation(mock(BaiDbImpl.class));
        verify(baiRepository, times(1)).save(any(BaiDbImpl.class));
    }

    @Test
    public void repositoryIsInvokedOnFind() {
        BaiServiceImpl baiService = new BaiServiceImpl();
        BaiRepository baiRepository = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepository);
        String accountId = "someId";

        baiService.findBankAccountInformation(accountId);
        verify(baiRepository, times(1)).findOne(accountId);
    }
}