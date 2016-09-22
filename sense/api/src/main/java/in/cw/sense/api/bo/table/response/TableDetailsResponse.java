package in.cw.sense.api.bo.table.response;

import java.util.List;

import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.table.dto.SeatingAreaDto;

@SuppressWarnings("rawtypes")
public class TableDetailsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<SeatingAreaDto> seatingAreas;

	public List<SeatingAreaDto> getSeatingAreas() {
		return seatingAreas;
	}
	public void setSeatingAreas(List<SeatingAreaDto> seatingAreas) {
		this.seatingAreas = seatingAreas;
	}
}
