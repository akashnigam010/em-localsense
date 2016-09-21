package in.cw.sense.app.personnel.mapper;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cwf.helper.hash.HashUtil;
import in.cw.sense.api.bo.personnel.dto.PersonnelDto;
import in.cw.sense.api.bo.personnel.entity.Personnel;
import in.cw.sense.api.bo.personnel.request.AddEditPersonnelRequest;
import in.cw.sense.api.type.RoleType;

@Component
public class PersonnelMapper {
	public void map(AddEditPersonnelRequest from, Personnel to) throws NoSuchAlgorithmException {
		to.setName(from.getName());
		to.setPin(HashUtil.hash(from.getPin()));
		to.setRoleId(from.getRoleId());
		if (StringUtils.isNotEmpty(from.getAccessCode())) {
			to.setAccessCode(HashUtil.hash(from.getAccessCode()));
		}		
		to.setMobile(from.getMobile());
	}

	public void map(List<Personnel> from, List<PersonnelDto> to) {
		for (Personnel interdata : from) {
			RoleType role = RoleType.getRoleById(interdata.getRoleId());
			if (RoleType.ADMIN != role) {
				PersonnelDto dto = new PersonnelDto();
				dto.setId(interdata.getId());
				dto.setName(interdata.getName());
				dto.setRoleId(interdata.getRoleId());
				dto.setMobile(interdata.getMobile());
				to.add(dto);
			}
		}
	}

	public List<PersonnelDto> map(List<Personnel> entities) {
		List<PersonnelDto> dtos = new ArrayList<>();
		for (Personnel entity : entities) {
			RoleType role = RoleType.getRoleById(entity.getRoleId());
			if (RoleType.ADMIN != role) {
				dtos.add(map(entity));
			}
		}
		return dtos;
	}
	
	public PersonnelDto map(Personnel entity) {
		PersonnelDto dto = new PersonnelDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setRoleId(entity.getRoleId());
		dto.setMobile(entity.getMobile());
		return dto;
	}
}
