package gateway.base.user.mapper;

import gateway.base.user.pojo.po.persistentLogins;

public interface persistentLoginsMapper {
    int insert(persistentLogins record);

    int insertSelective(persistentLogins record);
}