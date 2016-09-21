package in.cw.sense.app.restaurantinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.restaurant.dto.RestaurantInfoDto;
import in.cw.sense.api.bo.restaurant.request.RestaurantInfoRequest;
import in.cw.sense.api.bo.restaurant.response.RestaurantInfoResponse;

@Service
public class RestaurantInfoDelegate {

	@Autowired
	RestaurantInfoDao dao;

	public RestaurantInfoResponse getRestaurantInformation() throws BusinessException {
		RestaurantInfoResponse response = new RestaurantInfoResponse();
		RestaurantInfoDto restaurantInfoDto = dao.getRestaurantInformation();
		response.setRestaurantInfo(restaurantInfoDto);
		return response;
	}
	
	public RestaurantInfoResponse saveRestaurantInformation(RestaurantInfoRequest request) throws BusinessException {
		RestaurantInfoResponse response = new RestaurantInfoResponse();
		RestaurantInfoDto restaurantInfoDto = dao.saveRestaurantInformation(request);
		response.setRestaurantInfo(restaurantInfoDto);
		return response;
	}
}
