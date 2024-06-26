package com.firststep.back.member.repository;

import com.firststep.back.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberEmailAndMemberStatus(String email, int status);

    boolean existsByMemberNicknameAndMemberStatus(String nickname, int status);

    Member findByMemberEmailAndMemberStatus(String email, int userStatus);

    Member findByMemberNicknameAndMemberStatus(String nickname, int userStatus);

    List<Member> findAllByMemberLevel(int memberLevel);

    List<Member> findAllByMemberStatus(int memberStatus);
}
