package in.cw.sense.api.bo.personnel.dto;

public class LoggedInUser {
	private Integer id;
	private int roleId;
	private String roleName;
	private String name;
	private boolean isAuthorizedToManage = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAuthorizedToManage() {
		return isAuthorizedToManage;
	}

	public void setAuthorizedToManage(boolean isAuthorizedToManage) {
		this.isAuthorizedToManage = isAuthorizedToManage;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
