package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.restaurant.request.RestaurantInfoRequest;
import in.cw.sense.api.bo.restaurant.response.RestaurantInfoResponse;
import in.cw.sense.app.restaurantinfo.RestaurantInfoDelegate;
import in.cw.sense.app.service.validation.RestaurantInfoValidator;

@RestController
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OWNER') or hasAuthority('MANAGER')")
@RequestMapping(value = "/restaurantinfo")
public class RestaurantInfoService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	RestaurantInfoDelegate delegate;
	@Autowired
	RestaurantInfoValidator validator;

	@RequestMapping(value = "/getRestaurantInfo", method = RequestMethod.GET, headers = "Accept=application/json")
	public RestaurantInfoResponse getRestaurantInformation() {
		RestaurantInfoResponse response = new RestaurantInfoResponse();
		try {
			response = delegate.getRestaurantInformation();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/saveRestaurantInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public RestaurantInfoResponse saveRestaurantInformation(@RequestBody RestaurantInfoRequest request) {
		RestaurantInfoResponse response = new RestaurantInfoResponse();
		try {
			validator.validateRestaurantInfoRequest(request);
			response = delegate.saveRestaurantInformation(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
