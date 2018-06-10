package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.tax.request.TaxDetailsRequest;
import in.cw.sense.api.bo.tax.response.TaxDetailsResponse;
import in.cw.sense.app.service.validation.TaxValidator;
import in.cw.sense.app.taxdetails.TaxDetailsDelegate;

@RestController
@RequestMapping(value = "/taxdetails")
public class TaxService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	TaxDetailsDelegate delegate;
	@Autowired
	TaxValidator validator;

	@RequestMapping(value = "/getTaxDetails", method = RequestMethod.GET, headers = "Accept=application/json")
	public TaxDetailsResponse getTaxDetails() {
		TaxDetailsResponse response = new TaxDetailsResponse();
		try {
			response = delegate.getTaxDetails();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addEditTax", method = RequestMethod.POST, headers = "Accept=application/json")
	public TaxDetailsResponse addEditTax(@RequestBody TaxDetailsRequest taxDetailsRequest) {
		TaxDetailsResponse response = new TaxDetailsResponse();
		try {
			validator.validate(taxDetailsRequest);
			response = delegate.addOrEditTaxDetails(taxDetailsRequest);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/deleteTax", method = RequestMethod.POST, headers = "Accept=application/json")
	public TaxDetailsResponse deleteTax(@RequestBody TaxDetailsRequest taxDetailsRequest) {
		TaxDetailsResponse response = new TaxDetailsResponse();
		try {
			response = delegate.removeTaxDetails(taxDetailsRequest);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
