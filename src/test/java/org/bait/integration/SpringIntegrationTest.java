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
        String accountNumber = "accountNumber" + createUuid();
        String bankNumber = "bankNumber" + createUuid();
        String bankName = "bankName" + createUuid();
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

    @Test
    public void baiServiceMultipleWriteAndReadTest() {
        String accountNumberOne = "accountNumberOne" + createUuid();
        String bankNumberOne = "bankNumberOne" + createUuid();
        String bankNameOne = "bankNameOne" + createUuid();
        Bai baiOne = createBai(accountNumberOne, bankNumberOne, bankNameOne);
        Bai persistedBaiOne = baiService.createBankAccountInformation(baiOne);
        assertNotNull(persistedBaiOne);
        String baiOneId = persistedBaiOne.getBaiId();
        assertNotNull(baiOneId);

        String accountNumberTwo = "accountNumberTwo" + createUuid();
        String bankNumberTwo = "bankNumberTwo" + createUuid();
        String bankNameTwo = "bankNameTwo" + createUuid();
        Bai baiTwo = createBai(accountNumberTwo, bankNumberTwo, bankNameTwo);
        Bai persistedBaiTwo = baiService.createBankAccountInformation(baiTwo);
        assertNotNull(persistedBaiTwo);
        String baiTwoId = persistedBaiTwo.getBaiId();
        assertNotNull(baiTwoId);

        Bai loadedBaiOne = baiService.findBankAccountInformation(baiOneId);
        assertEquals(accountNumberOne, loadedBaiOne.getAccountNumber());
        assertEquals(bankNumberOne, loadedBaiOne.getBankNumber());
        assertEquals(bankNameOne, loadedBaiOne.getBankName());

        Bai loadedBaiTwo = baiService.findBankAccountInformation(baiTwoId);
        assertEquals(accountNumberTwo, loadedBaiTwo.getAccountNumber());
        assertEquals(bankNumberTwo, loadedBaiTwo.getBankNumber());
        assertEquals(bankNameTwo, loadedBaiTwo.getBankName());
    }

    @Test
    public void baiServiceWriteAndDeleteTest() {
        String accountNumber = "accountNumber" + createUuid();
        String bankNumber = "bankNumber"  + createUuid();
        String bankName = "bankName" +  createUuid();
        Bai bai = createBai(accountNumber, bankNumber, bankName);
        Bai persistedBai = baiService.createBankAccountInformation(bai);
        assertNotNull(persistedBai);
        String baiId = persistedBai.getBaiId();
        assertNotNull(baiId);
        Bai loadedBai = baiService.findBankAccountInformation(baiId);
        assertEquals(accountNumber, loadedBai.getAccountNumber());
        assertEquals(bankNumber, loadedBai.getBankNumber());
        assertEquals(bankName, loadedBai.getBankName());

        baiService.deleteBankAccountInformation(baiId);
        loadedBai = baiService.findBankAccountInformation(baiId);
        assertNull(loadedBai);
    }

    @Test
    public void baiServiceFindNoBai() {
        Bai loadedBai = baiService.findBankAccountInformation(createUuid());
        assertNull(loadedBai);
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
}
