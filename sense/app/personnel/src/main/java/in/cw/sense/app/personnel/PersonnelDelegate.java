package in.cw.sense.app.personnel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.personnel.dto.PersonnelDto;
import in.cw.sense.api.bo.personnel.request.AddEditPersonnelRequest;
import in.cw.sense.api.bo.personnel.request.DeletePersonnelRequest;
import in.cw.sense.api.bo.personnel.response.PersonnelResponse;
import in.cw.sense.app.personnel.mapper.PersonnelMapper;

@Service
public class PersonnelDelegate {
	@Autowired
	PersonnelDao dao;
	@Autowired
	PersonnelMapper mapper;

	public PersonnelResponse addEditPersonnel(AddEditPersonnelRequest request) throws BusinessException {
		PersonnelResponse response = new PersonnelResponse();
		List<PersonnelDto> personnels = null;
		if (request.getId() == null) {
			personnels = dao.addPersonnel(request);
		} else {
			personnels = dao.editPersonnel(request);
		}
		response.setPersonnels(personnels);
		return response;
	}

	public PersonnelResponse getPersonnels() throws BusinessException {
		PersonnelResponse response = new PersonnelResponse();
		List<PersonnelDto> personnels = dao.getPersonnels();
		response.setPersonnels(personnels);
		return response;
	}

	public PersonnelResponse deletePersonnel(DeletePersonnelRequest request) throws BusinessException {
		PersonnelResponse response = new PersonnelResponse();
		List<PersonnelDto> personnels = dao.deletePersonnel(request.getId());
		response.setPersonnels(personnels);
		return response;
	}
}
