package gateway.base.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import gateway.base.user.pojo.dto.UserIpDeleteDTO;
import gateway.base.user.pojo.po.UserIp;

@Mapper
public interface UserIpMapper {
    int insert(UserIp record);

	int insertSelective(UserIp record);
	
	int deleteRecord(UserIpDeleteDTO param);
}