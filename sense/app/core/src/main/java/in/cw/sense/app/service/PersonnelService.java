package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.personnel.request.AddEditPersonnelRequest;
import in.cw.sense.api.bo.personnel.request.DeletePersonnelRequest;
import in.cw.sense.api.bo.personnel.response.PersonnelResponse;
import in.cw.sense.app.personnel.PersonnelDelegate;
import in.cw.sense.app.service.validation.PersonnelValidator;

@RestController
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OWNER') or hasAuthority('MANAGER')")
@RequestMapping(value = "/personnel")
public class PersonnelService {
	@Autowired
	PersonnelDelegate delegate;
	@Autowired
	ResponseHelper helper;
	@Autowired
	PersonnelValidator validator;

	@RequestMapping(value = "/getPersonnels", method = RequestMethod.GET, headers = "Accept=application/json")
	public PersonnelResponse viewPersonnel() {
		PersonnelResponse response = new PersonnelResponse();
		try {
			response = delegate.getPersonnels();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addEditPersonnel", method = RequestMethod.POST, headers = "Accept=application/json")
	public PersonnelResponse addPersonnel(@RequestBody AddEditPersonnelRequest request) {
		PersonnelResponse response = new PersonnelResponse();
		try {
			validator.validateAddEditPersonnelRequest(request);
			response = delegate.addEditPersonnel(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/deletePersonnel", method = RequestMethod.POST, headers = "Accept=application/json")
	public PersonnelResponse deletePersonnel(@RequestBody DeletePersonnelRequest request) {
		PersonnelResponse response = new PersonnelResponse();
		try {
			validator.validateDeletePersonnelRequest(request);
			response = delegate.deletePersonnel(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
