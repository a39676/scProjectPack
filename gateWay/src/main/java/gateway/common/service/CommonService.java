package gateway.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import gateway.config.customComponent.SnowFlake;
import scAppCommon.service.AppCommonService;

public abstract class CommonService extends AppCommonService {
	
	@Autowired
	protected SnowFlake snowFlake;

}
