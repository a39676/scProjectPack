package gateway.base.user.mapper;

import gateway.base.user.pojo.dto.UserIpDeleteDTO;
import gateway.base.user.pojo.po.UserIp;

public interface UserIpMapper {
    int insert(UserIp record);

	int insertSelective(UserIp record);
	
	int deleteRecord(UserIpDeleteDTO param);
}