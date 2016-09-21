package in.cw.sense.api.bo.menu.response;

import in.cw.sense.api.bo.menu.dto.ItemDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class ItemResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private ItemDto item;

	public ItemDto getItem() {
		return item;
	}

	public void setItem(ItemDto item) {
		this.item = item;
	}
}
