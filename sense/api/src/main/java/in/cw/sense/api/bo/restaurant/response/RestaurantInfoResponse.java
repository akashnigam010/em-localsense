package in.cw.sense.api.bo.restaurant.response;

import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.restaurant.dto.RestaurantInfoDto;

@SuppressWarnings("rawtypes")
public class RestaurantInfoResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;

	private RestaurantInfoDto restaurantInfo;

	public RestaurantInfoDto getRestaurantInfo() {
		return restaurantInfo;
	}

	public void setRestaurantInfo(RestaurantInfoDto restaurantInfo) {
		this.restaurantInfo = restaurantInfo;
	}
}
