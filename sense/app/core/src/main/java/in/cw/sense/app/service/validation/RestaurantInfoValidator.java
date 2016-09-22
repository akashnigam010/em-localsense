package in.cw.sense.app.service.validation;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.restaurant.request.RestaurantInfoRequest;
import in.cw.sense.app.restaurantinfo.type.RestaurantInfoErrorCodeType;

@Component
public class RestaurantInfoValidator {
	private static final Logger LOG = Logger.getLogger(RestaurantInfoValidator.class);
	
	public void validateRestaurantInfoRequest(RestaurantInfoRequest request) throws BusinessException {
		if (StringUtils.isEmpty(request.getName()) || request.getBarInfo() == null
				|| StringUtils.isEmpty(request.getAddressLine1()) || StringUtils.isEmpty(request.getAddressLine2())
				|| StringUtils.isEmpty(request.getCity()) || request.getPinCode() == null
				|| StringUtils.isEmpty(request.getPhone1())) {
			LOG.error("One or more fields were null/empty while trying to access restaurant info service");
			throw new BusinessException(RestaurantInfoErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

}
