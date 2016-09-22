package in.cw.sense.app.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import cwf.helper.security.jwt.JwtHelper;
import in.cw.sense.api.bo.personnel.dto.LoggedInUser;
import in.cw.sense.api.bo.personnel.request.UserSignOnRequest;
import in.cw.sense.api.bo.personnel.response.BarAvailabilityResponse;
import in.cw.sense.api.bo.personnel.response.MessageCountResponse;
import in.cw.sense.api.bo.personnel.response.UserSignOnResponse;
import in.cw.sense.api.bo.restaurant.dto.RestaurantInfoDto;
import in.cw.sense.api.bo.restaurant.type.BarInfoType;
import in.cw.sense.api.type.RoleType;
import in.cw.sense.app.login.mapper.LoginMapper;
import in.cw.sense.app.restaurantinfo.RestaurantInfoDao;

@Service
public class LoginDelegate {
	@Autowired LoginDao dao;
	@Autowired LoginMapper mapper;
	@Autowired RestaurantInfoDao restaurantInfoDao;

	public UserSignOnResponse login(UserSignOnRequest request, Boolean isPinSignOn) throws BusinessException {
		UserSignOnResponse response = new UserSignOnResponse();
		LoggedInUser user = null;
		if (isPinSignOn) {
			user = mapper.map(dao.login(request, isPinSignOn));
		} else {
			user = mapper.map(dao.login(request, isPinSignOn));
			user.setAuthorizedToManage(true);
		}
		response.setUser(user);
		String token = JwtHelper.createJsonWebToken(user.getName(), RoleType.getRoleById(user.getRoleId()).getRole(),
				1L);
		response.setToken(token);
		return response;
	}

	public BarAvailabilityResponse isBarAvailable() throws BusinessException {
		BarAvailabilityResponse response = new BarAvailabilityResponse();
		RestaurantInfoDto restaurantInfoDto = restaurantInfoDao.getRestaurantInformation();
		BarInfoType barInfoType = BarInfoType.getBarInfoByCode(restaurantInfoDto.getBarInfoType());
		response.setIsBarAvailable(barInfoType.isBarAvailable());
		return response;
	}

	public MessageCountResponse getNewMessagesCount() throws BusinessException {
		MessageCountResponse response = new MessageCountResponse();
		response.setCount(dao.getNewMessageCount());
		return response;
	}
}
