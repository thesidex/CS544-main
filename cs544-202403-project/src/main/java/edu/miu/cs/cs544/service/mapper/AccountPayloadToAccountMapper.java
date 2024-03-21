package edu.miu.cs.cs544.service.mapper;

import org.springframework.stereotype.Component;

import edu.miu.common.service.mapper.BaseMapper;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.service.contract.AccountPayload;
import ma.glasnost.orika.MapperFactory;

@Component
public class AccountPayloadToAccountMapper extends BaseMapper<AccountPayload, Account>{

	public AccountPayloadToAccountMapper(MapperFactory mapperFactory) {
		super(mapperFactory, AccountPayload.class, Account.class);
	}
}