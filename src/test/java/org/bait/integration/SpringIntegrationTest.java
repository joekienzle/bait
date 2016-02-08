package org.bait.integration;

import org.bait.config.SpringConfig;
import org.bait.model.Bai;
import org.bait.service.BaiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringIntegrationTest {

    @Autowired
    private BaiService baiService;

    @Test
    public void writeTest() {
        Bai sendBai = new Bai();
        sendBai.setAccountNumber("123456");
        sendBai.setBankNumber("7890");
        sendBai.setBankName("my bank name");
        Bai persistedBai = baiService.createBankAccountInformation(sendBai);
        assertNotNull(persistedBai);
        String baiId = persistedBai.getBaiId();
        assertNotNull(baiId);
        System.out.println(baiId);
    }
}
