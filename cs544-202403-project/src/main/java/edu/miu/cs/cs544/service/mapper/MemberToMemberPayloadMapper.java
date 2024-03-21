package edu.miu.cs.cs544.service.mapper;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class MemberToMemberPayloadMapper extends BaseMapper<Member, MemberPayload> {

    public MemberToMemberPayloadMapper(MapperFactory mapperFactory) {
        super(mapperFactory, Member.class, MemberPayload.class);
    }
}
