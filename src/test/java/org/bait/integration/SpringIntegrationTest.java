package org.bait.integration;

import org.bait.config.SpringConfig;
import org.bait.model.Bai;
import org.bait.rest.BaitResource;
import org.bait.service.BaiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringIntegrationTest {

    @Autowired
    private BaiService baiService;

    @Autowired
    private BaitResource baitResource;

    @Test
    public void baiServiceWriteAndReadTest() {
        String accountNumber = "123456";
        String bankNumber = "7890";
        String bankName = "my bank name";
        Bai bai = createBai(accountNumber, bankNumber, bankName);
        Bai persistedBai = baiService.createBankAccountInformation(bai);
        assertNotNull(persistedBai);
        String baiId = persistedBai.getBaiId();
        assertNotNull(baiId);
        Bai loadedBai = baiService.findBankAccountInformation(baiId);
        assertEquals(accountNumber, loadedBai.getAccountNumber());
        assertEquals(bankNumber, loadedBai.getBankNumber());
        assertEquals(bankName, loadedBai.getBankName());
    }

    private Bai createBai(final String accountNumber,final  String bankNumber,final  String bankName) {
        Bai bai = new Bai();
        bai.setAccountNumber(accountNumber);
        bai.setBankNumber(bankNumber);
        bai.setBankName(bankName);
        return bai;
    }

    @Test
    public void baiResourceWriteTest() {
        String accountNumber = "123456";
        String bankNumber = "7890";
        String bankName = "my bank name";
        Bai bai = createBai(accountNumber, bankNumber, bankName);

        Response response = baitResource.createBaiInfo(bai);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }
}
