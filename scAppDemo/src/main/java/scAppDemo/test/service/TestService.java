package scAppDemo.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import scAppDemo.base.domain.po.Roles;
import scAppDemo.base.mapper.RolesMapper;

@Service
public class TestService {
	
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private RolesMapper rolesMapper;
	
	public Roles roleMapper() {
		Roles role = rolesMapper.selectByPrimaryKey(1L);
		role.setRoleId(Long.parseLong(port));
		return role;
	}

}
