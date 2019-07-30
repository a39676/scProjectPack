package gateway.base.user.mapper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import gateway.base.user.pojo.dto.ResetFailAttemptDTO;
import gateway.base.user.pojo.dto.UserAttemptQuerayDTO;
import gateway.base.user.pojo.po.UserAttempts;
import gateway.base.user.pojo.po.Users;
import gateway.base.user.pojo.po.UsersDetail;

@Mapper
public interface UsersMapper {

	int insert(Users record);

	int insertSelective(Users record);

	int insertFailAttempts(String userName);

	int resetFailAttempts(ResetFailAttemptDTO param);

	ArrayList<UserAttempts> getUserAttempts(UserAttemptQuerayDTO param);

	int cleanAttempts(@Param("dateInput") Date dateInput);

	int lockUserWithAttempts(String userName);

	Users findUserByUserName(String userName);

	int setLockeds(Users user);

	Long getUserIdByUserName(String userName);

	Long getUserIdByUserNameOrEmail(String inputUserName);

	UsersDetail getUserDetailByUserName(String userName);

	Users findUser(Long userId);

	int countAttempts(String userName);

	int matchUserPassword(@Param("userId") Long userId, @Param("pwd") String pwd);

	int atLeastOneSuperAdministratorUser(
			@Param("authType") Integer authType,
			@Param("authName") String authName
	);

}