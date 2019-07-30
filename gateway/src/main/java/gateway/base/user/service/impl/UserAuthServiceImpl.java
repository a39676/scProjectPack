package gateway.base.user.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gateway.base.user.mapper.UserAuthMapper;
import gateway.base.user.pojo.po.Auth;
import gateway.base.user.pojo.po.UserAuth;
import gateway.base.user.pojo.po.UserAuthExample;
import gateway.base.user.pojo.type.AuthType;
import gateway.base.user.service.AuthService;
import gateway.base.user.service.UserAuthService;
import gateway.common.service.CommonService;
import gateway.util.BaseUtilCustom;

@Service
public class UserAuthServiceImpl extends CommonService implements UserAuthService {

	private static final Logger log = LoggerFactory.getLogger(UserAuthServiceImpl.class);

	@Autowired
	private UserAuthMapper userAuthMapper;
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	@Autowired
	private AuthService authService;

	@Override
	public Long insertUserAuth(Long userId, Long authId) {
		UserAuth po = new UserAuth();
		Long creatorId = baseUtilCustom.getUserId();
		if (creatorId == null) {
			creatorId = userId;
		}
		Long newId = snowFlake.getNextId();
		po.setId(newId);
		po.setAuthId(authId);
		po.setUserId(userId);
		po.setCreateBy(creatorId);

		int count = userAuthMapper.insertSelective(po);
		if (count < 1) {
			log.error("insert user auth error userId: %s, authId: %s", userId, authId);
			return null;
		}

		return newId;
	}

	@Override
	public Long insertBaseUserAuth(Long userId, AuthType authType) {
		List<Auth> authList = authService.findAuthsByCondition(authType);
		if (authList == null || authList.size() < 1) {
			log.error("insert user auth error, auth not exists userId: %s, authType: %s", userId, authType.getName());
			return null;
		}
		
		Auth auth = authList.get(0);
		
		return insertUserAuth(userId, auth.getId());
	}
	
	@Override
	public int deleteUserAuth(Long userId, Long authId) {
		UserAuthExample example = new UserAuthExample();
		example.createCriteria().andUserIdEqualTo(userId).andAuthIdEqualTo(authId);
		List<UserAuth> userAuthList = userAuthMapper.selectByExample(example);
		if(userAuthList == null || userAuthList.size() < 1) {
			return 0;
		}
		
		UserAuth bo = new UserAuth();
		bo.setId(userAuthList.get(0).getId());
		bo.setIsDelete(true);
		return userAuthMapper.updateByPrimaryKeySelective(bo);
	}
	
	@Override
	public int deleteUserAuth(Long userId, AuthType authType) {
		List<Auth> authList = authService.findAuthsByCondition(authType);
		if (authList == null || authList.size() < 1) {
			return 0;
		}
		
		Auth auth = authList.get(0);
		
		return deleteUserAuth(userId, auth.getId());
	}

}
