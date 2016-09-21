package in.cw.sense.app.restaurantinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cwf.dbhelper.SenseContext;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.restaurant.dto.RestaurantInfoDto;
import in.cw.sense.api.bo.restaurant.entity.RestaurantInfo;
import in.cw.sense.api.bo.restaurant.request.RestaurantInfoRequest;
import in.cw.sense.app.restaurantinfo.mapper.RestaurantInfoMapper;
import in.cw.sense.app.restaurantinfo.type.RestaurantInfoErrorCodeType;

@Repository
public class RestaurantInfoDao {
	private static final String RESTAURANT_INFO_SEQ = "restaurant_info_seq";
	@Autowired SenseContext context;
	@Autowired RestaurantInfoMapper mapper;
	@Autowired MongoTemplate senseMongoTemplate;
	
	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}

	public RestaurantInfoDto getRestaurantInformation() throws BusinessException {
		List<RestaurantInfo> restaurantInfoEntities = senseMongoTemplate.findAll(RestaurantInfo.class);
		if (restaurantInfoEntities.size() == 0) {
			throw new BusinessException(RestaurantInfoErrorCodeType.RESTAURANT_INFO_NOT_AVAILABLE);
		} else if (restaurantInfoEntities.size() > 1) {
			throw new BusinessException(RestaurantInfoErrorCodeType.INVALID_DATA);
		} else {
			return mapper.mapRestaurantInfoEntityToDto(restaurantInfoEntities.get(0));
		}
	}
	
	public RestaurantInfoDto saveRestaurantInformation(RestaurantInfoRequest request) throws BusinessException {
		RestaurantInfo entity = null;
		List<RestaurantInfo> restaurantInfoEntities = senseMongoTemplate.findAll(RestaurantInfo.class);
		if (restaurantInfoEntities.size() == 0) {
			int id = (int) context.getNextSequenceId(RESTAURANT_INFO_SEQ);
			entity = mapper.mapRestaurantInfoRequestToEntity(request, new RestaurantInfo());
			entity.setId(id);
		} else if (restaurantInfoEntities.size() > 1) {
			throw new BusinessException(RestaurantInfoErrorCodeType.INVALID_DATA);
		} else {
			entity = mapper.mapRestaurantInfoRequestToEntity(request, restaurantInfoEntities.get(0));
		}
		senseMongoTemplate.save(entity);
		return mapper.mapRestaurantInfoEntityToDto(entity);
	}

}
