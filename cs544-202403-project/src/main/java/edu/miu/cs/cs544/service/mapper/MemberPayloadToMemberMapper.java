package edu.miu.cs.cs544.service.mapper;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class MemberPayloadToMemberMapper extends BaseMapper<MemberPayload, Member> {
    public MemberPayloadToMemberMapper(MapperFactory mapperFactory) {
        super(mapperFactory, MemberPayload.class, Member.class);
    }
}
