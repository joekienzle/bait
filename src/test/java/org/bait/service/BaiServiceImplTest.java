package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.db.model.BaiDbImpl;
import org.bait.model.Bai;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BaiServiceImplTest {

    @Test
    public void repositoryIsInvokedOnCreate() {
        BaiServiceImpl baiService = new BaiServiceImpl();
        BaiRepository baiRepositoryMock = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepositoryMock);

        baiService.createBankAccountInformation(mock(Bai.class));
        verify(baiRepositoryMock, times(1)).save(any(BaiDbImpl.class));
    }

    @Test
    public void repositoryIsInvokedOnFind() {
        BaiServiceImpl baiService = new BaiServiceImpl();
        BaiRepository baiRepositoryMock = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepositoryMock);
        String accountId = "someId";

        baiService.findBankAccountInformation(accountId);
        verify(baiRepositoryMock, times(1)).findOne(accountId);
    }
}