package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends BaseRepository<Member, Long> {
    Optional<Member> findMemberByBarCode(String barCode);
}
