package in.cw.sense.app.service.validation;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.request.BillIdRequest;
import in.cw.sense.api.bo.bill.request.DiscountRequest;
import in.cw.sense.api.bo.bill.request.GoToBillRequest;
import in.cw.sense.api.bo.bill.request.SearchBillRequest;
import in.cw.sense.api.bo.bill.request.SettleBillRequest;
import in.cw.sense.api.bo.bill.request.SpliBillRequest;
import in.cw.sense.api.bo.bill.type.PaymentModeType;
import in.cw.sense.app.bill.type.BillDetailsErrorCodeType;

@Component
public class BillValidator {
	private static final Logger LOG = Logger.getLogger(BillValidator.class);
	
	public void validateSearchBillRequest(SearchBillRequest request) throws BusinessException {
		if (request.getStartDate() == null || request.getEndDate() == null) {
			LOG.error("Start or End Date is null");
			throw new BusinessException(BillDetailsErrorCodeType.START_DATE_OR_END_DATE_MISSING);
		}
	}
	
	public void validateGoToBillRequest(GoToBillRequest request) throws BusinessException {
		if (request.getTableId() == null) {
			LOG.error("Table Id is not populated in GoToBillRequest");
			throw new BusinessException(BillDetailsErrorCodeType.TABLE_ID_CANNOT_BE_EMPTY);
		}
	}
	
	public void validateAddEditDiscountRequest(DiscountRequest request) throws BusinessException {
		 if (request.getBillId() == null || request.getDiscountType() == null ||
				 request.getMenuType() == null || request.getValue() == null) {
			 LOG.error("One or more details are not populated in AddEditDiscountRequest");
			throw new BusinessException(BillDetailsErrorCodeType.DETAILS_NOT_POPULATED);
		 }
	}
	
	public void validateRemoveDiscountRequest(DiscountRequest request) throws BusinessException {
		 if (request.getBillId() == null || request.getMenuType() == null) {
			 LOG.error("One or more details are not populated in RemoveDiscountRequest");
			throw new BusinessException(BillDetailsErrorCodeType.DETAILS_NOT_POPULATED);
		 }
	}

	public void validateSplitBillRequest(SpliBillRequest request) throws BusinessException {
		if (request.getBillId() == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_ID_CANNOT_BE_EMPTY);
		}
		if (request.getItemListOne().isEmpty() || request.getItemListTwo().isEmpty()) {
			throw new BusinessException(BillDetailsErrorCodeType.NO_ITEMS_FOUND_IN_BILL);
		}
	}

	public void validateSettleBillRequest(SettleBillRequest request) throws BusinessException {
		if (request.getBillId() == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_ID_CANNOT_BE_EMPTY);
		}
		if (request.getPaymentMode() == null || !PaymentModeType.contains(request.getPaymentMode())) {
					throw new BusinessException(BillDetailsErrorCodeType.PAYMENT_MODE_NOT_FOUND);
		}
		if (PaymentModeType.CANCELLED == PaymentModeType.getPaymentModeByCode(request.getPaymentMode()) &&
				request.getReasonForCancel() == null) {
			throw new BusinessException(BillDetailsErrorCodeType.EMPTY_REASON_FOR_CANCELLATION);
		}
	}

	public void validateBillIdRequest(BillIdRequest request) throws BusinessException {
		if (request.getBillId() == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_ID_CANNOT_BE_EMPTY);
		}
	}
}
