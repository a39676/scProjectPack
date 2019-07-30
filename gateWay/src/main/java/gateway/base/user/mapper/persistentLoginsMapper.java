package gateway.base.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import gateway.base.user.pojo.po.persistentLogins;

@Mapper
public interface persistentLoginsMapper {
    int insert(persistentLogins record);

    int insertSelective(persistentLogins record);
}