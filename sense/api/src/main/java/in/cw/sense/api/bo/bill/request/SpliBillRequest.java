package in.cw.sense.api.bo.bill.request;

import java.util.ArrayList;
import java.util.List;

import in.cw.sense.api.bo.table.dto.Item;

public class SpliBillRequest {
	private Integer billId;
	private List<Item> itemListOne;
	private List<Item> itemListTwo;

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public List<Item> getItemListOne() {
		if (itemListOne == null) {
			return new ArrayList<>();
		}
		return itemListOne;
	}

	public void setItemListOne(List<Item> itemListOne) {
		this.itemListOne = itemListOne;
	}

	public List<Item> getItemListTwo() {
		if (itemListTwo == null) {
			return new ArrayList<>();
		}
		return itemListTwo;
	}

	public void setItemListTwo(List<Item> itemListTwo) {
		this.itemListTwo = itemListTwo;
	}
}
