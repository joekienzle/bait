package org.bait.service;

import org.bait.db.BaiRepository;
import org.bait.model.Bai;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BaiServiceImplTest {

    @Test
    public void repositoryIsInvokedOnCreate() {
        BaiService baiService = new BaiServiceImpl();
        Bai baiMock = mock(Bai.class);
        BaiRepository baiRepository = mock(BaiRepository.class);
        baiService.setBaiRepository(baiRepository);

        baiService.createBankAccountInformation(baiMock);
        verify(baiRepository, times(1)).save(baiMock);
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