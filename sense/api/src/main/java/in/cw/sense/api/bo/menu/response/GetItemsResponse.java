package in.cw.sense.api.bo.menu.response;

import java.util.ArrayList;
import java.util.List;

import in.cw.sense.api.bo.menu.dto.ItemDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class GetItemsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<ItemDto> items;

	public List<ItemDto> getItems() {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<ItemDto> items) {
		this.items = items;
	}

}
