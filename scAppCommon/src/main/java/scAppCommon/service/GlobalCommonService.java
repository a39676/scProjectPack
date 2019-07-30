package scAppCommon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import scAppCommon.pojo.dto.PageParam;
import scAppCommon.pojo.result.CommonResult;
import scAppCommon.pojo.type.ResultType;

public abstract class GlobalCommonService {

protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Value("${envName}")
	protected String envName;
	
	@Autowired
	protected RedisTemplate<String, String> redisTemplate;

	private static final int normalPageSize = 10;
	private static final int maxPageSize = 300;
	protected static final long theStartTime = 946656000000L;

	protected PageParam setPageFromPageNo(Integer pageNo) {
		return setPageFromPageNo(pageNo, normalPageSize);
	}

	protected PageParam setPageFromPageNo(Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 1;
		}
		if (pageSize > maxPageSize) {
			pageSize = maxPageSize;
		}
		PageParam pp = new PageParam();
		if (pageNo == 1) {
			pp.setPageStart(0);
			pp.setPageEnd(pageSize);
		} else if (pageNo > 1) {
			pp.setPageStart(pageSize * (pageNo - 1) + 1);
			pp.setPageEnd(pp.getPageStart() + pageSize);
		}
		pp.setPageSize(pageSize);
		return pp;
	}

	protected CommonResult nullParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.nullParam);
		return result;
	}
	
	protected CommonResult errorParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.errorParam);
		return result;
	}
	
	protected CommonResult serviceError() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.serviceError);
		return result;
	}
	
	protected CommonResult normalSuccess() {
		CommonResult result = new CommonResult();
		result.normalSuccess();
		return result;
	}
	
	protected CommonResult notLogin() {
		CommonResult result = new CommonResult();
		result.failWithMessage("请登录后操作");
		return result;
	}
	
	protected static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.contains("windows")) {
			return true;
		} else {
			return false;
		}
	}
}
