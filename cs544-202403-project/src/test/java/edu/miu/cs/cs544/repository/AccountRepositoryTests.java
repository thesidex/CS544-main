package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.AccountType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findAccountsByBalanceCondition_passed() throws Exception {
        Account acc = new Account("Bruce", "Student", AccountType.DINING_POINTS, 15000.00, 100.00);
        entityManager.persist(acc);
        entityManager.flush();

        List<String> expected = accountRepository.findAccountsByBalanceCondition();
        assertEquals(expected.size(), 1);
    }
}
