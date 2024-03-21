package edu.miu.cs.cs544;

import java.util.ArrayList;
import java.util.List;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.repository.AccountRepository;
import edu.miu.cs.cs544.repository.AttendanceRepository;
import edu.miu.cs.cs544.service.AccountService;
import edu.miu.cs.cs544.service.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountServiceTests {
    @MockBean
    AccountRepository accountRepository;
    @MockBean
    AttendanceRepository attendanceRepository;
    AccountService accountService;
    List<String> emails = new ArrayList<>(List.of("thephong.nguyen@miu.edu"));
    List<Attendance> attendances = new ArrayList<>(List.of(new Attendance()));

    @Before
    public void setup() {
        accountService = new AccountServiceImpl(accountRepository, attendanceRepository);
    }

    @Test
    public void findAccountsByBalanceCondition_passed() {
        Mockito.when(accountRepository.findAccountsByBalanceCondition()).thenReturn(emails);
        accountService.findAccountsByBalanceCondition();
        verify(accountRepository, times(1)).findAccountsByBalanceCondition();
    }

    @Test
    public void getAttendanceByAccountIdAndStartTimeAndEndTime_passed() {
        Mockito.when(attendanceRepository.findAllByAccountId(1L, "2024-01-01", "2024-02-15")).thenReturn(attendances);
        accountService.getAttendanceByAccountIdAndStartTimeAndEndTime(1L, "2024-01-01", "2024-02-15");
        verify(attendanceRepository, times(1)).findAllByAccountId(1L, "2024-01-01", "2024-02-15");
    }
}
