package org.bait.rs;

import org.bait.model.Bai;
import org.bait.rest.BaitResource;
import org.bait.rest.model.BaiJsonImpl;
import org.bait.service.PreconditionFailedException;
import org.bait.service.api.BaiService;
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
        when(baiServiceMock.createBankAccountInformation(any(Bai.class))).thenReturn(baiMock);
        baitResource.setBaiService(baiServiceMock);
        BaiJsonImpl baiJsonImplMock = mock(BaiJsonImpl.class);
        Response response = baitResource.createBaiInfo(baiJsonImplMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).createBankAccountInformation(baiJsonImplMock);
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
    public void deleteBaiInfo() throws PreconditionFailedException {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        String baiId = UUID.randomUUID().toString();
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.deleteBaiInfo(baiId);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(baiServiceMock, times(1)).deleteBankAccountInformation(baiId);
    }

    @Test
    public void deleteBaiInfoWithPreconditionFailed() throws PreconditionFailedException {
        BaitResource baitResource = new BaitResource();
        BaiService baiServiceMock = mock(BaiService.class);
        final String exceptionMessage = "Something went wrong";
        doThrow(new PreconditionFailedException(exceptionMessage)).when(baiServiceMock).deleteBankAccountInformation(anyString());
        String baiId = UUID.randomUUID().toString();
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.deleteBaiInfo(baiId);
        assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), response.getStatus());
        assertEquals(exceptionMessage, response.getEntity());
        verify(baiServiceMock, times(1)).deleteBankAccountInformation(baiId);
    }
}
