package edu.miu.cs.cs544.repository;

import edu.miu.common.domain.AuditData;
import edu.miu.cs.cs544.domain.Member;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTests {

        @Autowired
        private MemberRepository memberRepository;

        private Member createTestMember(
                        String firstName,
                        String lasName,
                        String barCode) {
                return new Member(
                                firstName,
                                lasName,
                                firstName + lasName + "@gmail.com",
                                barCode,
                                new HashSet<>(),
                                new ArrayList<>(),
                                new AuditData());
        }

        @Transactional
        @Test
        public void whenBarCodeSupplied_ThenFindMember() {
                final var member1 = createTestMember(
                                "Bruce", "Banner", "HulkBarCode");

                final var member2 = createTestMember(
                                "Michael", "Knight", "RiderBarCode");

                memberRepository.save(member1);
                memberRepository.save(member2);

                memberRepository.flush();

                memberRepository.findMemberByBarCode("HulkBarCode")
                                .ifPresentOrElse(
                                                (member) -> Assertions.assertEquals(member.getFirstName(), "Bruce"),
                                                Assertions::fail);
        }

        @Test
        @Transactional
        public void whenSameBarCode_ThenThrowException() {
                final var sameBarCode = "BarCode";

                final var member1 = createTestMember(
                                "Bruce", "Banner", sameBarCode);

                final var member2 = createTestMember(
                                "Michael", "Knight", sameBarCode);

                memberRepository.save(member1);
                memberRepository.save(member2);

                Assertions.assertThrows(
                                DataIntegrityViolationException.class,
                                () -> memberRepository.flush());
        }

        @Test
        @Transactional
        @Rollback(false) // Ensure the transaction is not rolled back to see the actual exception
        public void whenDuplicateBarcode_thenThrowDataIntegrityViolationException() {
                // Arrange
                final String sameBarcode = "Barcode";
                Member member1 = createTestMember("Bruce", "Banner", sameBarcode);
                Member member2 = createTestMember("Michael", "Knight", sameBarcode);

                // Act & Assert
                memberRepository.save(member1);
                assertThrows(DataIntegrityViolationException.class, () -> memberRepository.saveAndFlush(member2));
        }
}
