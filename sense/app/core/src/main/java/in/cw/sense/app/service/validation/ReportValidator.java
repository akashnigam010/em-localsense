package in.cw.sense.app.service.validation;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.request.SearchBillRequest;
import in.cw.sense.app.report.type.ReportErrorCodeType;

@Component
public class ReportValidator {
	private static final Logger LOG = Logger.getLogger(ReportValidator.class);

	public void validateReportRequest(SearchBillRequest request) throws BusinessException {
		if (request.getStartDate() == null || request.getEndDate() == null) {
			LOG.error("Start or End Date is null");
			throw new BusinessException(ReportErrorCodeType.START_DATE_OR_END_DATE_MISSING);
		}
	}

}
