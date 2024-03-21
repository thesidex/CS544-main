package edu.miu.cs.cs544.service;
import java.util.List;
import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.service.contract.AccountPayload;
import edu.miu.cs.cs544.service.contract.AttendancePayload;

public interface AccountService extends BaseReadWriteService <AccountPayload, Account, Long>{
	List<String> findAccountsByBalanceCondition();
	List<AttendancePayload> getAttendanceByAccountIdAndStartTimeAndEndTime(Long accountId, String startTime, String endTime);
 }
