package in.cw.sense.app.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.request.BillIdRequest;
import in.cw.sense.api.bo.bill.request.DiscountRequest;
import in.cw.sense.api.bo.bill.request.GoToBillRequest;
import in.cw.sense.api.bo.bill.request.SearchBillRequest;
import in.cw.sense.api.bo.bill.request.SettleBillRequest;
import in.cw.sense.api.bo.bill.request.SpliBillRequest;
import in.cw.sense.api.bo.bill.response.BillResponse;
import in.cw.sense.api.bo.bill.response.SearchBillResponse;
import in.cw.sense.api.bo.bill.response.SettleBillResponse;
import in.cw.sense.api.bo.bill.response.SplitBillResponse;
import in.cw.sense.api.bo.response.StatusResponse;
import in.cw.sense.app.bill.BillDelegate;
import in.cw.sense.app.bill.mapper.BillMapper;
import in.cw.sense.app.service.validation.BillValidator;

@RestController
@RequestMapping(value = "/bill")
public class BillService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	BillDelegate delegate;
	@Autowired
	BillMapper mapper;
	@Autowired
	BillValidator validator;

	@RequestMapping(value = "/searchBills", method = RequestMethod.POST, headers = "Accept=application/json")
	public SearchBillResponse searchBills(@RequestBody SearchBillRequest request) {
		SearchBillResponse response = new SearchBillResponse();
		try {
			validator.validateSearchBillRequest(request);
			response = delegate.searchBills(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/goToBill", method = RequestMethod.POST, headers = "Accept=application/json")
	public BillResponse goToBill(@RequestBody GoToBillRequest request) {
		BillResponse response = new BillResponse();
		try {
			validator.validateGoToBillRequest(request);
			List<BillDto> billDtos = delegate.goToBill(request.getTableId());
			response.setBills(billDtos);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addEditDiscount", method = RequestMethod.POST, headers = "Accept=application/json")
	public BillResponse addEditDiscount(@RequestBody DiscountRequest request) {
		BillResponse response = new BillResponse();
		try {
			validator.validateAddEditDiscountRequest(request);
			BillDto bill = delegate.addEditDiscount(request);
			response.setBills(Collections.singletonList(bill));
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/removeDiscount", method = RequestMethod.POST, headers = "Accept=application/json")
	public BillResponse removeDiscount(@RequestBody DiscountRequest request) {
		BillResponse response = new BillResponse();
		try {
			validator.validateRemoveDiscountRequest(request);
			BillDto bill = delegate.removeDiscount(request);
			response.setBills(Collections.singletonList(bill));
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/splitBill", method = RequestMethod.POST, headers = "Accept=application/json")
	public SplitBillResponse splitBill(@RequestBody SpliBillRequest request) {
		SplitBillResponse response = new SplitBillResponse();
		try {
			validator.validateSplitBillRequest(request);
			response = delegate.splitBill(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/settleBill", method = RequestMethod.POST, headers = "Accept=application/json")
	public SettleBillResponse settleBill(@RequestBody SettleBillRequest request) {
		SettleBillResponse response = new SettleBillResponse();
		try {
			validator.validateSettleBillRequest(request);
			BillDto billDto = delegate.settleOrEditBill(request, true);
			response.setBill(billDto);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/editBill", method = RequestMethod.POST, headers = "Accept=application/json")
	public SettleBillResponse editBill(@RequestBody SettleBillRequest request) {
		SettleBillResponse response = new SettleBillResponse();
		try {
			validator.validateSettleBillRequest(request);
			BillDto billDto = delegate.settleOrEditBill(request, false);
			response.setBill(billDto);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/printBill", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse printBill(@RequestBody BillIdRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateBillIdRequest(request);
			delegate.printBill(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/emailBill", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse emailBill(@RequestBody BillIdRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validateBillIdRequest(request);
			delegate.emailBill(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
