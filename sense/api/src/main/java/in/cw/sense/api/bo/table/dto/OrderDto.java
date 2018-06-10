package in.cw.sense.api.bo.table.dto;

import java.util.List;

public class OrderDto {
	private Integer id;
	private List<ItemDto> items;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<ItemDto> getItems() {
		return items;
	}
	public void setItems(List<ItemDto> items) {
		this.items = items;
	}
}
