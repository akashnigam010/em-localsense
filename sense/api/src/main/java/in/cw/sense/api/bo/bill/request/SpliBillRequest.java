package in.cw.sense.api.bo.bill.request;

import java.util.ArrayList;
import java.util.List;

import in.cw.sense.api.bo.table.dto.ItemDto;

public class SpliBillRequest {
	private Integer billId;
	private List<ItemDto> itemListOne;
	private List<ItemDto> itemListTwo;

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public List<ItemDto> getItemListOne() {
		if (itemListOne == null) {
			return new ArrayList<>();
		}
		return itemListOne;
	}

	public void setItemListOne(List<ItemDto> itemListOne) {
		this.itemListOne = itemListOne;
	}

	public List<ItemDto> getItemListTwo() {
		if (itemListTwo == null) {
			return new ArrayList<>();
		}
		return itemListTwo;
	}

	public void setItemListTwo(List<ItemDto> itemListTwo) {
		this.itemListTwo = itemListTwo;
	}
}
