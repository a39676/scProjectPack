package gateway.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import gateway.base.user.pojo.po.Roles;

@Mapper
public interface RolesMapper {
    int insert(Roles record);

    int insertSelective(Roles record);
    
    int insertOrUpdate(Roles role);

    Roles getRoleByName(String roleName);
    
	List<Roles> getRoleList();
	
	List<Roles> findRolesByAuthId(Long authId);
	
	List<Roles> findRolesByAuthIdList(@Param("authIdList")List<Long> authIdList);
}