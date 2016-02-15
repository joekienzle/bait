package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.model.BaiDbImpl;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BaiImplServiceDbImplTest {

    @Test
    public void repositoryIsInvokedOnCreate() {
        BaiService baiService = new BaiServiceImpl();
        BaiDbImpl baiDbImplMock = mock(BaiDbImpl.class);
        BaiRepository baiRepository = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepository);

        baiService.createBankAccountInformation(baiDbImplMock);
        verify(baiRepository, times(1)).save(baiDbImplMock);
    }

    @Test
    public void repositoryIsInvokedOnFind() {
        BaiService baiService = new BaiServiceImpl();
        BaiRepository baiRepository = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepository);
        String accountId = "someId";

        baiService.findBankAccountInformation(accountId);
        verify(baiRepository, times(1)).findOne(accountId);
    }
}