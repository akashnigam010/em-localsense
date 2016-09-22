package in.cw.sense.api.bo.menu.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.menu.type.FnbItemType;

@Document(collection = "item")
public class FnbItem extends Item {
	
	@Field("fnbItemType")
	private FnbItemType fnbItemType;

	@Field("servesFor")
	private Integer servesFor;
	
	@Field("preparationTime")
	private Integer preparationTime;
	
	@Field("spicyLevel")
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
