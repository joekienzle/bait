package org.bait.service;

import org.bait.db.TransferInfoRepository;
import org.bait.db.model.TransferInfoDbImpl;
import org.bait.model.TransferInfo;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TransferInfoServiceImplTest {

    @Test
    public void repositoryIsInvokedOnCreate() {
        TransferInfoServiceImpl transferInfoService = new TransferInfoServiceImpl();
        TransferInfoRepository transferInfoRepositoryMock = mock(TransferInfoRepository.class);
        transferInfoService.setTransferInfoRepository(transferInfoRepositoryMock);

        transferInfoService.createTransferInformation(mock(TransferInfo.class));
        verify(transferInfoRepositoryMock, times(1)).save(any(TransferInfoDbImpl.class));
    }

    @Test
    public void repositoryIsInvokedOnFind() {
        TransferInfoServiceImpl transferInfoService = new TransferInfoServiceImpl();
        TransferInfoRepository transferInfoRepositoryMock = mock(TransferInfoRepository.class);
        transferInfoService.setTransferInfoRepository(transferInfoRepositoryMock);
        String transferId = "someId";

        transferInfoService.findTransferInfo(transferId);
        verify(transferInfoRepositoryMock, times(1)).findOne(transferId);
    }
}