package in.cw.sense.api.bo.table.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "table_seating_area")
public class TableSeatingArea {
	@Id
	@Field
	private Integer id;
	@Field("seatingAreaName")
	private String seatingAreaName;

	public TableSeatingArea(int id, String seatingAreaName) {
		this.id = id;
		this.seatingAreaName = seatingAreaName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeatingAreaName() {
		return seatingAreaName;
	}
	public void setSeatingAreaName(String seatingAreaName) {
		this.seatingAreaName = seatingAreaName;
	}
}
