package gateway.base.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import gateway.base.user.pojo.po.Users;

@Mapper
public interface UserRegistMapper {
	
	int isUserExists(String userName);
	
	int resetPassword(@Param("pwd")String pwd, @Param("pwdd")String pwdd, @Param("userId")Long userId);
	
	int insertNewUser(Users user);

}
