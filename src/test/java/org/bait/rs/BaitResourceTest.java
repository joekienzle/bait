package org.bait.rs;

import org.bait.model.Bai;
import org.bait.model.TransferInfo;
import org.bait.rest.BaitResource;
import org.bait.service.BaiService;
import org.bait.service.TransferInfoService;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BaitResourceTest {
    @Test
    public void createBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        Bai baiMock = mock(Bai.class);
        when(baiServiceMock.createBankAccountInformation(baiMock)).thenReturn(baiMock);
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.createBaiInfo(baiMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).createBankAccountInformation(baiMock);
    }

    @Test
    public void getBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        Bai baiMock = mock(Bai.class);
        baitResource.setBaiService(baiServiceMock);
        String someId = "someId";
        when(baiServiceMock.findBankAccountInformation(someId)).thenReturn(baiMock);

        Response response = baitResource.findBankAccountInformation(someId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).findBankAccountInformation(someId);
    }

    @Test
    public void getNoBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        baitResource.setBaiService(baiServiceMock);
        String someId = "someId";
        when(baiServiceMock.findBankAccountInformation(someId)).thenReturn(null);

        Response response = baitResource.findBankAccountInformation(someId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(baiServiceMock, times(1)).findBankAccountInformation(someId);
    }

    @Test
    public void deleteBaiInfo() {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        String baiId = UUID.randomUUID().toString();
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.deleteBaiInfo(baiId);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(baiServiceMock, times(1)).deleteBankAccountInformation(baiId);
    }

    @Test
    public void createTransferInfo() {
        BaitResource baitResource = new BaitResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        String baiId = UUID.randomUUID().toString();
        TransferInfo transferInfoMock = mock(TransferInfo.class);
        when(transferInfoServiceMock.createTransferInformation(transferInfoMock, baiId)).thenReturn(transferInfoMock);
        baitResource.setTransferInfoService(transferInfoServiceMock);
        Response response = baitResource.createTransferInfo(baiId, transferInfoMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(transferInfoServiceMock, times(1)).createTransferInformation(transferInfoMock, baiId);
    }

    @Test
    public void getTransferInfo() {
        BaitResource baitResource = new BaitResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        String baiId = UUID.randomUUID().toString();
        String transferInfoId = UUID.randomUUID().toString();
        TransferInfo transferInfoMock = mock(TransferInfo.class);
        when(transferInfoServiceMock.findTransferInfo(baiId, transferInfoId)).thenReturn(transferInfoMock);
        baitResource.setTransferInfoService(transferInfoServiceMock);

        Response response = baitResource.getTransferInfo(baiId, transferInfoId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(transferInfoServiceMock, times(1)).findTransferInfo(baiId, transferInfoId);
    }

    @Test
    public void getNoTransferInfo() {
        BaitResource baitResource = new BaitResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        String baiId = UUID.randomUUID().toString();
        String transferInfoId = UUID.randomUUID().toString();
        when(transferInfoServiceMock.findTransferInfo(baiId, transferInfoId)).thenReturn(null);
        baitResource.setTransferInfoService(transferInfoServiceMock);

        Response response = baitResource.getTransferInfo(baiId, transferInfoId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(transferInfoServiceMock, times(1)).findTransferInfo(baiId, transferInfoId);
    }
}
