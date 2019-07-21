package scAppDemo.test.pojo.vo;

import java.time.LocalDateTime;

public class TestVO {
	private String voName;
	private LocalDateTime localDateTime;

	public String getVoName() {
		return voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	@Override
	public String toString() {
		return "TestVO [voName=" + voName + ", localDateTime=" + localDateTime + "]";
	}

}
