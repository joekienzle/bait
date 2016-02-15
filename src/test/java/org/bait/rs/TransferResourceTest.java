
package org.bait.rs;

import org.bait.model.TransferInfo;
import org.bait.rest.TransferResource;
import org.bait.service.TransferInfoService;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TransferResourceTest {
    @Test
    public void createTransferInfo() {
        TransferResource transferResource = new TransferResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        TransferInfo transferInfoMock = mock(TransferInfo.class);
        when(transferInfoServiceMock.createTransferInformation(transferInfoMock)).thenReturn(transferInfoMock);
        transferResource.setTransferInfoService(transferInfoServiceMock);
        Response response = transferResource.createTransferInfo(transferInfoMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(transferInfoServiceMock, times(1)).createTransferInformation(transferInfoMock);
    }

    @Test
    public void getTransferInfo() {
        TransferResource transferResource = new TransferResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        String transferInfoId = UUID.randomUUID().toString();
        TransferInfo transferInfoMock = mock(TransferInfo.class);
        when(transferInfoServiceMock.findTransferInfo(transferInfoId)).thenReturn(transferInfoMock);
        transferResource.setTransferInfoService(transferInfoServiceMock);

        Response response = transferResource.getTransferInfo(transferInfoId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(transferInfoServiceMock, times(1)).findTransferInfo(transferInfoId);
    }

    @Test
    public void getNoTransferInfo() {
        TransferResource transferResource = new TransferResource();
        TransferInfoService transferInfoServiceMock = mock(TransferInfoService.class);
        String transferInfoId = UUID.randomUUID().toString();
        when(transferInfoServiceMock.findTransferInfo(transferInfoId)).thenReturn(null);
        transferResource.setTransferInfoService(transferInfoServiceMock);

        Response response = transferResource.getTransferInfo(transferInfoId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(transferInfoServiceMock, times(1)).findTransferInfo(transferInfoId);
    }
}
