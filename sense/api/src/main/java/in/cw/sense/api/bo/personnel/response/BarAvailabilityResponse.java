package in.cw.sense.api.bo.personnel.response;

import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class BarAvailabilityResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;

	private Boolean isBarAvailable;

	public Boolean isBarAvailable() {
		return isBarAvailable;
	}

	public void setIsBarAvailable(Boolean isBarAvailable) {
		this.isBarAvailable = isBarAvailable;
	}
}
