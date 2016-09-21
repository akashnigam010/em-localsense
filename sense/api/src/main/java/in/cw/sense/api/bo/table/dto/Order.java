package in.cw.sense.api.bo.table.dto;

import java.util.List;

public class Order {
	private Integer id;
	private List<Item> items;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
