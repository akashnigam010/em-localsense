package in.cw.sense.app.service.validation;

import org.springframework.stereotype.Component;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.personnel.request.UserSignOnRequest;
import in.cw.sense.app.login.type.LoginErrorCodeType;

@Component
public class LoginValidator {
	public void validateLoginService(UserSignOnRequest request, boolean isPinSignOn) throws BusinessException {
		if (!isPinSignOn) {
			if (request.getAccessCode() == null) {
				throw new BusinessException(LoginErrorCodeType.ACCESS_CODE_EMPTY);
			}
		}
		if (request.getPin() == null) {
			throw new BusinessException(LoginErrorCodeType.PIN_EMPTY);
		}
	}
}
