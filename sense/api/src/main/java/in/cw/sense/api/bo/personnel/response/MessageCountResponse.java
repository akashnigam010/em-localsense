package in.cw.sense.api.bo.personnel.response;

import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class MessageCountResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;

	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
