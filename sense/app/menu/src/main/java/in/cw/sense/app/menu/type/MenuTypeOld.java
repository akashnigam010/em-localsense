package in.cw.sense.app.menu.type;

public enum MenuTypeOld {
	ALACARTE("alacarte", "A La Carte"),
	BUFFET("buffet", "Buffet"),
	COMBO("combo", "Combo");

	private String code;
	private String description;

	MenuTypeOld(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getNameByCode(String code) {
		for (MenuTypeOld e : MenuTypeOld.values()) {
			if (e.code.equals(code)) {
				return e.description;
			}
		}
		return null;
	}

	public static MenuTypeOld getByCode(String code) {
		for (MenuTypeOld menuType : MenuTypeOld.values()) {
			if (menuType.getCode().equals(code)) {
				return menuType;
			}
		}

		return null;
	}
}
