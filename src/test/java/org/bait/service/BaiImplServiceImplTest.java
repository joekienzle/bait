package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.BaiImpl;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BaiImplServiceImplTest {

    @Test
    public void repositoryIsInvokedOnCreate() {
        BaiService baiService = new BaiServiceImpl();
        BaiImpl baiImplMock = mock(BaiImpl.class);
        BaiRepository baiRepository = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepository);

        baiService.createBankAccountInformation(baiImplMock);
        verify(baiRepository, times(1)).save(baiImplMock);
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