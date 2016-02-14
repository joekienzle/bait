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

import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringIntegrationTest {

    @Autowired
    private BaiService baiService;

    @Autowired
    private BaitResource baitResource;

    @Test
    public void baiServiceWriteAndReadTest() {
        Bai bai = createBai("accountNumber" + createUuid(), "bankNumber" + createUuid(), "bankName" + createUuid());
        Bai persistedBai = baiService.createBankAccountInformation(bai);
        assertNotNull(persistedBai);
        String baiId = persistedBai.getBaiId();
        assertNotNull(baiId);
        compareBai(bai, baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void baiServiceMultipleWriteAndReadTest() {
        Bai baiOne = createBai("accountNumberOne" + createUuid(), "bankNumberOne" + createUuid(), "bankNameOne" + createUuid());
        Bai persistedBaiOne = baiService.createBankAccountInformation(baiOne);
        assertNotNull(persistedBaiOne);
        String baiOneId = persistedBaiOne.getBaiId();
        assertNotNull(baiOneId);

        Bai baiTwo = createBai("accountNumberTwo" + createUuid(), "bankNumberTwo" + createUuid(), "bankNameTwo" + createUuid());
        Bai persistedBaiTwo = baiService.createBankAccountInformation(baiTwo);
        assertNotNull(persistedBaiTwo);
        String baiTwoId = persistedBaiTwo.getBaiId();
        assertNotNull(baiTwoId);

        compareBai(baiOne, baiService.findBankAccountInformation(baiOneId));
        compareBai(baiTwo, baiService.findBankAccountInformation(baiTwoId));
    }

    @Test
    public void baiServiceWriteAndDeleteTest() {
        Bai bai = createBai("accountNumber" + createUuid(), "bankNumber"  + createUuid(), "bankName" +  createUuid());
        Bai persistedBai = baiService.createBankAccountInformation(bai);
        assertNotNull(persistedBai);
        String baiId = persistedBai.getBaiId();
        assertNotNull(baiId);
        compareBai(bai, baiService.findBankAccountInformation(baiId));

        baiService.deleteBankAccountInformation(baiId);
        assertNull(baiService.findBankAccountInformation(baiId));
    }

    @Test
    public void baiServiceFindNoBai() {
        Bai loadedBai = baiService.findBankAccountInformation(createUuid());
        assertNull(loadedBai);
    }

    @Test
    public void baiResourceWriteTest() {
        Bai bai = createBai("123456", "7890", "my bank name");

        Response response = baitResource.createBaiInfo(bai);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    private String createUuid() {
        return UUID.randomUUID().toString();
    }

    private Bai createBai(final String accountNumber,final  String bankNumber,final  String bankName) {
        Bai bai = new Bai();
        bai.setAccountNumber(accountNumber);
        bai.setBankNumber(bankNumber);
        bai.setBankName(bankName);
        return bai;
    }

    private void compareBai(final Bai expected, final Bai actual) {
        assertEquals(expected.getAccountNumber(), actual.getAccountNumber());
        assertEquals(expected.getBankNumber(), actual.getBankNumber());
        assertEquals(expected.getBankName(), actual.getBankName());
    }
}
