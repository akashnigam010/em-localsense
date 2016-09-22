package in.cw.sense.app.service.validation;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.tax.request.TaxDetailsRequest;
import in.cw.sense.app.taxdetails.type.TaxDetailsErrorCodeType;
import in.cw.sense.app.taxdetails.type.TaxType;

@Component
public class TaxValidator {
	private static final Logger LOG = Logger.getLogger(TaxValidator.class);
	
	public void validate(TaxDetailsRequest request) throws BusinessException {
		if(StringUtils.isBlank(request.getTaxName())) {
			LOG.error("Tax name cannot be blank while adding Tax details");
			throw new BusinessException(TaxDetailsErrorCodeType.TAX_NAME_CANT_BE_EMPTY);
		}
		if(StringUtils.isBlank(request.getTaxType()) || 
				StringUtils.isBlank(TaxType.getTaxCodeByType(request.getTaxType()))) {
			LOG.error("Tax type mentioned doesnot match with backend");
			throw new BusinessException(TaxDetailsErrorCodeType.INCORRECT_TAX_TYPE);
		} 
		if(request.getFnbTax() == null && request.getLiquorTax() == null) {
			LOG.error("FnbTax and LiquorTax cannot be empty/null");
			throw new BusinessException(TaxDetailsErrorCodeType.FOOD_AND_LIQUOR_TAX_CANT_BE_EMPTY);
		}
		if(request.getFnbTax() == null) {
			request.setFnbTax(BigDecimal.ZERO);
		}
		if(request.getLiquorTax() == null) {
			request.setLiquorTax(BigDecimal.ZERO);
		}
	}
}
