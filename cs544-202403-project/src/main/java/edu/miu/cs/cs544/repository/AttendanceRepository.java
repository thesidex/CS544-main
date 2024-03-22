package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Attendance;

import org.checkerframework.checker.units.qual.m;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends BaseRepository<Attendance, Long> {
    @Query(value = "select a from Attendance a where a.scanner.code = :code")
    List<Attendance> findAllRecordsByScannerCode(@Param("code") String code);
    @Query(value = "select a from Attendance a where a.scanner.event.id = :evtId")
    List<Attendance> findAllByEventId(@Param("evtId") String evtId);
    //@Query(value = "select a.* from attendance a, member m, account acc where a.member_id = m.id and m.id = acc.member_id and acc.id = :accountId and format(date_time,'yyyy-mm-dd') between :startDate and :endDate", nativeQuery = true)
    @Query(value = "SELECT a.* FROM attendance a JOIN member m ON a.member_id = m.id JOIN account acc ON m.id = acc.member_id WHERE acc.id = :accountId AND a.date_time >= :startDate AND a.date_time < :endDate", nativeQuery = true)
    List<Attendance> findAllByAccountId(@Param("accountId") Long accountId, @Param("startDate") String startDate,@Param("endDate") String endDate);
    @Query(value = "select a from Attendance a where a.member.id = :memId and a.scanner.event.id = :evtId")
    List<Attendance> findAllByMemberIdAndEventId(@Param("memId") long memId, @Param("evtId") long evtId);
    @Query(value = "select a from Attendance a where a.member.id = :memId")
    List<Attendance> findAllByMemberId(@Param("memId") String memId);
    @Query("SELECT a FROM Attendance a WHERE a.member.id = :memberId")
    List<Attendance> findAllByMemberId(@Param("memberId") Long memberId);
    @Query("SELECT a FROM Attendance a WHERE a.member.id = :memberId and a.scanner.event.id = :eventId")
    List<Attendance> findByMemberIdAndEventId(@Param("memberId") Long memberId, @Param("eventId") long eventId);
}


