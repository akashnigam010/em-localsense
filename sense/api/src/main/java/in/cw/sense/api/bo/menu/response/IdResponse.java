package in.cw.sense.api.bo.menu.response;

import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class IdResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
