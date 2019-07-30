package gateway.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import gateway.config.customComponent.SnowFlake;
import scAppCommon.service.GlobalCommonService;

public abstract class CommonService extends GlobalCommonService {
	
	@Autowired
	protected SnowFlake snowFlake;

}
