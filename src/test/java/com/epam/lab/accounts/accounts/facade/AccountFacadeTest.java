package com.epam.lab.accounts.accounts.facade;

import com.epam.lab.accounts.accounts.repository.AccountModelRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountFacadeTest {

    @Autowired
    private AccountModelRepository accountModelRepository;

    @org.junit.Test
    public void testRe() {
        String email = "tommy.johnson@gmail.com";
        String ggg = "pharma-group";

        boolean b = accountModelRepository.existsAccountModelByCodeAndUserEmail(ggg, email);

        System.out.println();
    }
}
