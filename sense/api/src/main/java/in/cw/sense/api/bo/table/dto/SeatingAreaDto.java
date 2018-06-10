package in.cw.sense.api.bo.table.dto;

import java.util.ArrayList;
import java.util.List;

public class SeatingAreaDto {
	private Integer id;
	private String name;
	private List<TableDto> tables = new ArrayList<>();

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TableDto> getTables() {
		if (tables == null) {
			return new ArrayList<>();
		}
		return tables;
	}
	public void setTables(List<TableDto> tables) {
		this.tables = tables;
	}
}
