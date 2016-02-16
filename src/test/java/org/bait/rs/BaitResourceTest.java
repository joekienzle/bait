package org.bait.rs;

import org.bait.model.Bai;
import org.bait.rest.BaitResource;
import org.bait.rest.model.BaiJsonImpl;
import org.bait.service.PreconditionFailedException;
import org.bait.service.api.BaiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaitResourceTest {

    @Mock
    private BaiService baiServiceMock;

    @Mock
    private Bai baiMock;

    @Mock
    private BaiJsonImpl baiJsonImplMock;

    private BaitResource baitResource;

    public static final String EXCEPTION_MESSAGE = "Something went wrong";

    @Before
    public void setUp() {
        baitResource = new BaitResource();
        baitResource.setBaiService(baiServiceMock);
    }

    @Test
    public void createBaiInfo() {
        when(baiServiceMock.createBankAccountInformation(any(Bai.class))).thenReturn(baiMock);
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.createBaiInfo(baiJsonImplMock);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).createBankAccountInformation(baiJsonImplMock);
    }

    @Test
    public void getBaiInfo() {
        String someId = "someId";
        when(baiServiceMock.findBankAccountInformation(someId)).thenReturn(baiMock);

        Response response = baitResource.findBankAccountInformation(someId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(baiServiceMock, times(1)).findBankAccountInformation(someId);
    }

    @Test
    public void getNoBaiInfo() {
        baitResource.setBaiService(baiServiceMock);
        String someId = "someId";
        when(baiServiceMock.findBankAccountInformation(someId)).thenReturn(null);

        Response response = baitResource.findBankAccountInformation(someId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(baiServiceMock, times(1)).findBankAccountInformation(someId);
    }

    @Test
    public void deleteBaiInfo() throws PreconditionFailedException {
        String baiId = UUID.randomUUID().toString();
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.deleteBaiInfo(baiId);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(baiServiceMock, times(1)).deleteBankAccountInformation(baiId);
    }

    @Test
    public void deleteBaiInfoWithPreconditionFailed() throws PreconditionFailedException {
        doThrow(new PreconditionFailedException(EXCEPTION_MESSAGE)).when(baiServiceMock).deleteBankAccountInformation(anyString());
        String baiId = UUID.randomUUID().toString();
        baitResource.setBaiService(baiServiceMock);
        Response response = baitResource.deleteBaiInfo(baiId);
        assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), response.getStatus());
        assertEquals(EXCEPTION_MESSAGE, response.getEntity());
        verify(baiServiceMock, times(1)).deleteBankAccountInformation(baiId);
    }
}
