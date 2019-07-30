package gateway.base.user.service;

import java.util.List;

import gateway.base.user.pojo.dto.FindAuthsConditionDTO;
import gateway.base.user.pojo.po.Auth;
import gateway.base.user.pojo.type.AuthType;

public interface AuthService {

	Long __createBaseSuperAdminAuth(Long supserAdminUserId);

	Long __createBaseAdminAuth(Long supserAdminUserId);

	Long __createBaseUserActiveAuth(Long supserAdminUserId);

	Long __createBaseUserAuth(Long supserAdminUserId);

	Long __createBasePosterAuth(Long supserAdminUserId);

	Long __createBaseDelayPosterAuth(Long supserAdminUserId);

	List<Auth> findSuperAdministratorAuth();

	List<Auth> findAuthsByCondition(FindAuthsConditionDTO dto);

	List<Auth> findAuthsByCondition(AuthType authType);

}
