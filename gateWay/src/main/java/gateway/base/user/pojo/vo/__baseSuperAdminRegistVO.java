package gateway.base.user.pojo.vo;

import common.result.CommonResult;

public class __baseSuperAdminRegistVO extends CommonResult<Object> {

	private Long newSuperAdminId;

	public Long getNewSuperAdminId() {
		return newSuperAdminId;
	}

	public void setNewSuperAdminId(Long newSuperAdminId) {
		this.newSuperAdminId = newSuperAdminId;
	}

	@Override
	public String toString() {
		return "__baseSuperAdminRegistVO [newSuperAdminId=" + newSuperAdminId + ", getCode()=" + getCode()
				+ ", getResult()=" + getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
