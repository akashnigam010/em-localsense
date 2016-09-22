package in.cw.sense.api.bo.personnel.response;

import java.util.List;

import in.cw.sense.api.bo.personnel.dto.PersonnelDto;
import in.cw.sense.api.bo.response.GenericResponse;

public class PersonnelResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<PersonnelDto> personnels;

	public List<PersonnelDto> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<PersonnelDto> personnels) {
		this.personnels = personnels;
	}
}
