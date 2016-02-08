package org.bait.integration;

import org.bait.config.SpringConfig;
import org.bait.model.Bai;
import org.bait.rest.BaitResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringIntegrationTest {

    @Autowired
    private BaitResource baitResource;

    @Test
    public void writeTest() {
        Bai baiMock = mock(Bai.class);
        when(baiMock.getAccountNumber()).thenReturn("123456");
        when(baiMock.getBankNumber()).thenReturn("7890");
        when(baiMock.getBankName()).thenReturn("my bank name");
        Response response = baitResource.createBaiInfo(baiMock);
        assertNotNull(response);
        Bai bai = response.readEntity(Bai.class);
        assertNotNull(bai);
    }
}
