package gateway.base.user.mapper;

import gateway.base.user.pojo.po.UserAttempts;

public interface UserAttemptsMapper {
    int insert(UserAttempts record);

    int insertSelective(UserAttempts record);
}