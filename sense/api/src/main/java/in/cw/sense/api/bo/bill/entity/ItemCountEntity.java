package in.cw.sense.api.bo.bill.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ItemCountEntity {
	@Field("fnb")
	private Integer fnb;
	
	@Field("liquor")
	private Integer liquor;

	public Integer getFnbItemCount() {
		return fnb;
	}

	public void setFnbItemCount(Integer fnbItemCount) {
		this.fnb = fnbItemCount;
	}

	public Integer getLiqourItemCount() {
		return liquor;
	}

	public void setLiqourItemCount(Integer liqourItemCount) {
		this.liquor = liqourItemCount;
	}
}
