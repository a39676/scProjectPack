package gateway.base.user.service;

import java.util.List;

import gateway.base.user.pojo.po.AuthRole;
import gateway.base.user.pojo.po.AuthRoleExample;

public interface AuthRoleService {

	Long insertAuthRole(Long authId, Long roleId, Long creatorId);

	List<AuthRole> selectByExample(AuthRoleExample example);

	int deleteById(Long id);

}
