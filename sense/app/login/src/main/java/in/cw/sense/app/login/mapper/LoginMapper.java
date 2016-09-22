package in.cw.sense.app.login.mapper;

import org.springframework.stereotype.Component;

import in.cw.sense.api.bo.personnel.dto.LoggedInUser;
import in.cw.sense.api.bo.personnel.dto.PersonnelDto;
import in.cw.sense.api.bo.personnel.entity.Personnel;
import in.cw.sense.api.type.RoleType;

@Component
public class LoginMapper {
	public PersonnelDto map(Personnel from) {
		PersonnelDto to = new PersonnelDto();
		to.setId(from.getId());
		to.setName(from.getName());
		to.setRoleId(from.getRoleId());
		return to;
	}

	public LoggedInUser map(PersonnelDto personnel) {
		Integer roleId = personnel.getRoleId();
		LoggedInUser user = new LoggedInUser();
		user.setId(personnel.getId());
		user.setName(personnel.getName());
		user.setRoleId(roleId);
		user.setRoleName(RoleType.getRoleById(roleId).getRole());
		return user;
	}
}
