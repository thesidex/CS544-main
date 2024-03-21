package edu.miu.cs.cs544.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Account;


@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {
    @Query(value = "select m.email from Account a, Member m where a.member_id = m.id and cvalue < dvalue*0.05", nativeQuery = true)
    List<String> findAccountsByBalanceCondition();
}
