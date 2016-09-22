package in.cw.sense.app.service.validation;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.personnel.request.AddEditPersonnelRequest;
import in.cw.sense.api.bo.personnel.request.DeletePersonnelRequest;
import in.cw.sense.api.type.RoleType;
import in.cw.sense.app.personnel.type.PersonnelErrorCodeType;

@Component
public class PersonnelValidator {
	private static final Logger LOG = Logger.getLogger(PersonnelValidator.class);
	private static final int PIN_CODE_LENGTH = 4;
	private static final int ACCESS_CODE_LENGTH = 6;

	public void validateAddEditPersonnelRequest(AddEditPersonnelRequest request) throws BusinessException {
		if (request.getPin() == null || request.getRoleId() == null || request.getName() == null) {
			LOG.error("One or more details are not populated in AddEditPersonnelRequest");
			throw new BusinessException(PersonnelErrorCodeType.PERSONNEL_DETAILS_MISSING);
		}
		if (RoleType.MANAGER == RoleType.getRoleById(request.getRoleId())
				|| RoleType.OWNER == RoleType.getRoleById(request.getRoleId())) {
			if (StringUtils.isBlank(request.getAccessCode())) {
				LOG.error("Accesscode cannot be empty for MANAGER and OWNER while Adding a Personnel");
				throw new BusinessException(PersonnelErrorCodeType.ACCESS_CODE_NULL_OR_EMPTY);
			}
			if (request.getAccessCode().length() < ACCESS_CODE_LENGTH) {
				LOG.error("Accesscode cannot be less than 4 digits for MANAGER and OWNER");
				throw new BusinessException(PersonnelErrorCodeType.ACCESS_CODE_LENGTH_ERROR);
			}
		}
		if (request.getPin().length() != PIN_CODE_LENGTH) {
			LOG.error("PIN length should be 4 digits");
			throw new BusinessException(PersonnelErrorCodeType.PIN_CODE_LENGTH_ERROR);
		}
	}

	public void validateDeletePersonnelRequest(DeletePersonnelRequest request) throws BusinessException {
		if (request.getId() == null) {
			LOG.error("ID cannot be null or empty while Deleting Personnel Details");
			throw new BusinessException(PersonnelErrorCodeType.MISSING_ID_ERROR);
		}
	}
}
