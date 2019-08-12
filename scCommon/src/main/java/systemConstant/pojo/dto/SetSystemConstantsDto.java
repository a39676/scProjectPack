package systemConstant.pojo.dto;

import java.util.List;

public class SetSystemConstantsDto {

	private List<SetSystemConstantDto> values;

	public List<SetSystemConstantDto> getValues() {
		return values;
	}

	public void setValues(List<SetSystemConstantDto> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "SetSystemConstantsDto [values=" + values + "]";
	}

}
