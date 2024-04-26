package com.firststep.back.member.service;

import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.form.AddMemberForm;

public interface MemberService {

    MemberResponseDto addMember(AddMemberForm addMemberForm);
}
