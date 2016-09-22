package in.cw.sense.app.taxdetails.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import in.cw.sense.api.bo.tax.dto.TaxDetailsDto;
import in.cw.sense.api.bo.tax.entity.TaxDetailsEntity;
import in.cw.sense.api.bo.tax.request.TaxDetailsRequest;
import in.cw.sense.api.bo.tax.response.TaxDetailsResponse;

@Service
public class TaxDetailsMapper {

	public TaxDetailsDto mapEntityToDto(TaxDetailsEntity from, TaxDetailsDto to) {
		to.setFnbTax(from.getFnbTax() != null ? from.getFnbTax().setScale(2, RoundingMode.CEILING) :
			BigDecimal.ZERO.setScale(2, RoundingMode.CEILING));
		to.setLiquorTax(from.getLiquorTax() != null ? from.getLiquorTax().setScale(2, RoundingMode.CEILING) :
			BigDecimal.ZERO.setScale(2, RoundingMode.CEILING));
		to.setTaxName(from.getTaxName());
		to.setTaxType(from.getTaxType());
		to.setId(from.getId());
		return to;
	}

	public void mapTaxDetailsToResponse(List<TaxDetailsDto> taxDetails, TaxDetailsResponse response) {
		response.setTaxDetails(taxDetails);
	}

	public void map(TaxDetailsRequest taxDetailsRequest, TaxDetailsEntity taxDetails) {
		taxDetails.setFnbTax(taxDetailsRequest.getFnbTax());
		taxDetails.setLiquorTax(taxDetailsRequest.getLiquorTax());
		taxDetails.setTaxName(taxDetailsRequest.getTaxName());
		taxDetails.setTaxType(taxDetailsRequest.getTaxType());
	}
}
