package systemConstant.pojo.dto;

import java.util.List;

public class GetValsByNameDto {

	private List<String> constantNames;

	public List<String> getConstantNames() {
		return constantNames;
	}

	public void setConstantNames(List<String> constantNames) {
		this.constantNames = constantNames;
	}

	@Override
	public String toString() {
		return "GetValsByNameDto [constantNames=" + constantNames + "]";
	}

}
