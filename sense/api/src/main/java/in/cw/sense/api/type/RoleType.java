package in.cw.sense.api.type;

public enum RoleType {
	ADMIN(1, "ADMIN"), OWNER(2, "OWNER"), MANAGER(3, "MANAGER"), STEWARD(4, "STEWARD");

	private Integer id;
	private String role;

	RoleType(Integer id, String role) {
		this.id = id;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	public static RoleType getRoleById(Integer id) {
		for (RoleType type : RoleType.values()) {
			if (type.getId() == id) {
				return type;
			}
		}

		return null;
	}
}
