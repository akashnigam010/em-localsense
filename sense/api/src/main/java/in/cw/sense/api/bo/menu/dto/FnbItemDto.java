package in.cw.sense.api.bo.menu.dto;

import in.cw.sense.api.bo.menu.type.FnbItemType;

public class FnbItemDto extends ItemDto {
	private FnbItemType fnbItemType;
	private Integer servesFor;
	private Integer preparationTime;
	private Integer spicyLevel;

	public FnbItemType getFnbItemType() {
		return fnbItemType;
	}

	public void setFnbItemType(FnbItemType fnbItemType) {
		this.fnbItemType = fnbItemType;
	}

	public Integer getServesFor() {
		return servesFor;
	}

	public void setServesFor(Integer servesFor) {
		this.servesFor = servesFor;
	}

	public Integer getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(Integer preparationTime) {
		this.preparationTime = preparationTime;
	}

	public Integer getSpicyLevel() {
		return spicyLevel;
	}

	public void setSpicyLevel(Integer spicyLevel) {
		this.spicyLevel = spicyLevel;
	}
}
