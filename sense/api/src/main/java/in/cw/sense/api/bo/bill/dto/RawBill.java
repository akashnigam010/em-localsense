package in.cw.sense.api.bo.bill.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.api.bo.restaurant.dto.RestaurantInfoDto;
import in.cw.sense.api.bo.table.dto.ItemDto;

public class RawBill {
	private RestaurantInfoDto restaurantInfo;
	private BillDto bill;

	public RawBill(RestaurantInfoDto restaurantInfo, BillDto bill) {
		super();
		this.restaurantInfo = restaurantInfo;
		this.bill = bill;
	}

	public RestaurantInfoDto getRestaurantInfo() {
		return restaurantInfo;
	}

	public void setRestaurantInfo(RestaurantInfoDto restaurantInfo) {
		this.restaurantInfo = restaurantInfo;
	}

	public BillDto getBill() {
		return bill;
	}

	public void setBill(BillDto bill) {
		this.bill = bill;
	}

	public List<ItemDto> getFnbItems() {
		List<ItemDto> fnbItems = new ArrayList<>();
		for (ItemDto item : this.bill.getOrders()) {
			if (item.getType() == MenuType.FNB) {
				fnbItems.add(item);
			}
		}
		return fnbItems;
	}

	public List<ItemDto> getBarItems() {
		List<ItemDto> barItems = new ArrayList<>();
		for (ItemDto item : this.bill.getOrders()) {
			if (item.getType() == MenuType.BAR) {
				barItems.add(item);
			}
		}
		return barItems;
	}

	public Date getBillDate() {
		Date date = new Date();
		return date;
	}
}
