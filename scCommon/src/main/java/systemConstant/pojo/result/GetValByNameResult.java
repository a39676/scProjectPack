package systemConstant.pojo.result;

import common.result.CommonResult;

public class GetValByNameResult extends CommonResult {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "GetValByNameResult [value=" + value + "]";
	}

}
