package gateway.base.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import gateway.base.user.pojo.po.UserAttempts;

@Mapper
public interface UserAttemptsMapper {
    int insert(UserAttempts record);

    int insertSelective(UserAttempts record);
}