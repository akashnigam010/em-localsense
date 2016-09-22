package in.cw.sense.api.bo.tax.response;

import java.util.List;

import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.tax.dto.TaxDetailsDto;

@SuppressWarnings("rawtypes")
public class TaxDetailsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<TaxDetailsDto> taxDetails;

	public List<TaxDetailsDto> getTaxDetails() {
		return taxDetails;
	}

	public void setTaxDetails(List<TaxDetailsDto> taxDetails) {
		this.taxDetails = taxDetails;
	}
}
