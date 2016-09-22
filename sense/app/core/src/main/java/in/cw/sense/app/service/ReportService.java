package in.cw.sense.app.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.request.SearchBillRequest;
import in.cw.sense.api.bo.report.response.ReportResponse;
import in.cw.sense.app.report.ReportDelegate;
import in.cw.sense.app.service.validation.ReportValidator;

@RestController
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OWNER') or hasAuthority('MANAGER')")
@RequestMapping(value = "/report")
public class ReportService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	ReportDelegate delegate;
	@Autowired
	ReportValidator validator;

	@RequestMapping(value = "/generateSalesReport", method = RequestMethod.POST, headers = "Accept=application/json")
	public ReportResponse generateSalesReport(@RequestBody SearchBillRequest request) {
		ReportResponse response = new ReportResponse();
		try {
			validator.validateReportRequest(request);
			response = delegate.generateSalesReport(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		} catch (ParseException e) {
			//TODO: add a generic exception
			BusinessException be = new BusinessException();
			return helper.failure(response, be);
		}
	}
}
