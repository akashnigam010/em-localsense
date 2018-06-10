package in.cw.sense.api.bo.kot.response;

import java.util.List;

import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.table.dto.OrderDto;

public class AssociatedKotResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<OrderDto> associatedKots;

	public List<OrderDto> getAssociatedKots() {
		return associatedKots;
	}

	public void setAssociatedKots(List<OrderDto> associatedKots) {
		this.associatedKots = associatedKots;
	}
}
