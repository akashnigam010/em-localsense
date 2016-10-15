package in.cw.sense.app.bill;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.date.CwfClock;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.dto.RawBill;
import in.cw.sense.api.bo.bill.entity.BillEntity;
import in.cw.sense.api.bo.bill.entity.DiscountEntity;
import in.cw.sense.api.bo.bill.entity.ItemCountEntity;
import in.cw.sense.api.bo.bill.entity.OrderEntity;
import in.cw.sense.api.bo.bill.entity.OrderUnit;
import in.cw.sense.api.bo.bill.entity.TotalEntity;
import in.cw.sense.api.bo.bill.entity.TypeValueEntity;
import in.cw.sense.api.bo.bill.request.BillIdRequest;
import in.cw.sense.api.bo.bill.request.DiscountRequest;
import in.cw.sense.api.bo.bill.request.SearchBillRequest;
import in.cw.sense.api.bo.bill.request.SettleBillRequest;
import in.cw.sense.api.bo.bill.request.SpliBillRequest;
import in.cw.sense.api.bo.bill.response.SearchBillResponse;
import in.cw.sense.api.bo.bill.response.SplitBillResponse;
import in.cw.sense.api.bo.bill.type.BillStatusType;
import in.cw.sense.api.bo.bill.type.CloudSyncStatusType;
import in.cw.sense.api.bo.bill.type.PaymentModeType;
import in.cw.sense.api.bo.kot.dto.KotDto;
import in.cw.sense.api.bo.kot.entity.Kot;
import in.cw.sense.api.bo.menu.type.DiscountType;
import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.api.bo.restaurant.response.RestaurantInfoResponse;
import in.cw.sense.api.bo.table.dto.OrderDto;
import in.cw.sense.api.bo.table.entity.Table;
import in.cw.sense.api.bo.tax.entity.TaxDetailsEntity;
import in.cw.sense.app.bill.mapper.BillMapper;
import in.cw.sense.app.bill.type.BillDetailsErrorCodeType;
import in.cw.sense.app.bill.util.BillEmailCreator;
import in.cw.sense.app.bill.util.BillPdfGenerator;
import in.cw.sense.app.bill.util.InternalChargesAndTaxCalculationEngine;
import in.cw.sense.app.restaurantinfo.RestaurantInfoDelegate;
import in.cw.sense.app.tabledetails.TableDetailsDelegate;
import in.cw.sense.app.taxdetails.TaxDetailsDelegate;

@Service
public class BillDelegate {
	private static final String SUB_TOTAL_WO_CHARGES_TAXES = "SUB TOTAL W/O CHARGES & TAXES";

	@Autowired BillDao dao;
	@Autowired BillMapper mapper;
	@Autowired TableDetailsDelegate tableDelegate;
	@Autowired TaxDetailsDelegate taxDetailsDelegate;
	@Autowired InternalChargesAndTaxCalculationEngine taxCalculator;
	@Autowired CwfClock clock;
	@Autowired RestaurantInfoDelegate restaurantInfoDelegate;
	@Autowired BillEmailCreator emailCreator;
	@Autowired BillPdfGenerator pdfGenerator;

	public List<BillDto> goToBill(Integer tableId) throws BusinessException {
		List<BillDto> bills = new ArrayList<>();
		// Fetch TableDetails for preparing Bill
		Table tableDetails = tableDelegate.getOneTableDetails(tableId);
		validateTableDetails(tableDetails);
		List<Integer> billIds = tableDetails.getBillIds();
		// Check whether a Bill already exist for a given TableId
		BillEntity billEntity = null;
		if (billIds == null || billIds.isEmpty()) {
			bills.add(addOrderItemsAndCalculateBill(consolidateOrderedItems(tableDetails.getOrders()), tableId, null));
		} else {
			for (Integer billId : billIds) {
				BillDto billDto = new BillDto();
				billEntity = dao.getBillForATable(tableDetails.getId(), billId);
				if (billEntity == null) {
					billDto = addOrderItemsAndCalculateBill(consolidateOrderedItems(tableDetails.getOrders()), tableId, null);
				} else if (tableDetails.getIsBillDirty()) {
					billDto = updateOrderItemsAndCalculateBill(consolidateOrderedItems(tableDetails.getOrders()),
							billId, tableId);
				} else {
					mapper.mapBillEntityToDto(billEntity, billDto);
				}
				bills.add(billDto);
			}
		}
		return bills;
	}
	
	public SearchBillResponse searchBillById(SearchBillRequest request, List<String> billStatuses) throws BusinessException {
		SearchBillResponse response = new SearchBillResponse();
		BillDto billDto = dao.getBillById(request.getId(), billStatuses);
		List<BillDto> billDtos = response.getSettledBills();
		billDtos.add(billDto);
		response.setSettledBills(billDtos);
		return response;
	}

	
	/**
	 * 
	 * @param request
	 * @param billStatuses - Filter bills on status
	 * @return
	 * @throws BusinessException
	 */
	public SearchBillResponse searchBills(SearchBillRequest request, List<String> billStatuses) throws BusinessException {
		SearchBillResponse response = new SearchBillResponse();
		response.setOpenTables(tableDelegate.getAllOpenTables());
		LocalDateTime startDateMidnight = LocalDateTime.of(
				request.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalTime.MIDNIGHT);
		LocalDateTime endDateMidnight = LocalDateTime
				.of(request.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalTime.MIDNIGHT)
				.plusDays(1);
		Date startDate = Date.from(startDateMidnight.atZone(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(endDateMidnight.atZone(ZoneId.systemDefault()).toInstant());
		List<BillDto> billDtos = dao.getBillsByDate(startDate, endDate, billStatuses);
		Collections.sort(billDtos);
		response.setSettledBills(billDtos);
		return response;
	}

	public SplitBillResponse splitBill(SpliBillRequest request) throws BusinessException {
		SplitBillResponse response = new SplitBillResponse();
		BillEntity bill = dao.getBill(request.getBillId());
		if (bill == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_DETAILS_NOT_FOUND);
		}
		Integer tableId = bill.getTableId();
		List<OrderUnit> orderListOne = tableDelegate.mapOrderItemsToOrderUnitEntity(request.getItemListOne());
		BillDto billOne = updateOrderItemsAndCalculateBill(orderListOne, request.getBillId(), tableId);
		List<OrderUnit> orderListTwo = tableDelegate.mapOrderItemsToOrderUnitEntity(request.getItemListTwo());
		BillDto billTwo = addOrderItemsAndCalculateBill(orderListTwo, tableId, billOne);
		updateAssociatedKotDetails(billOne.getId(), billTwo.getId());
		response.setBillOne(billOne);
		response.setBillTwo(billTwo);
		return response;
	}

	private void updateAssociatedKotDetails(Integer billIdOne, Integer billIdTwo) throws BusinessException {
		List<KotDto> kots = dao.getAssociatedKotDetails(billIdOne);
		if (kots == null || kots.isEmpty()) {
			throw new BusinessException(BillDetailsErrorCodeType.ASSOCIATED_KOT_NOT_FOUND);
		}
		for (KotDto kot : kots) {
			dao.updateKot(kot.getId(), billIdTwo);
		}
	}

	public BillDto addEditDiscount(DiscountRequest request) throws BusinessException {
		BillDto dto = new BillDto();
		BillEntity bill = dao.getBill(request.getBillId());
		if (bill == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_DETAILS_NOT_FOUND);
		}
		BigDecimal discountAmount = BigDecimal.ZERO;
		BigDecimal subTotalValue = BigDecimal.ZERO;
		if (MenuType.FNB == request.getMenuType()) {
			subTotalValue = bill.getSubTotal().getFnb();
		} else if (MenuType.BAR == request.getMenuType()) {
			subTotalValue = bill.getSubTotal().getLiquor();
		}
		if (DiscountType.PERCENT == request.getDiscountType()) {
			discountAmount = (subTotalValue.multiply(request.getValue())).divide(new BigDecimal(100));
		} else if (DiscountType.DIRECT == request.getDiscountType()) {
			discountAmount = request.getValue();
		}
		DiscountEntity discount = null;
		if (bill.getDiscount() == null) {
			discount = new DiscountEntity();
		} else {
			discount = bill.getDiscount();
		}
		TypeValueEntity typeValue = new TypeValueEntity();
		typeValue.setType(request.getDiscountType());
		typeValue.setValue(request.getValue());
		typeValue.setAmount(discountAmount);
		if (MenuType.FNB == request.getMenuType()) {
			discount.setFnb(typeValue);
		} else if (MenuType.BAR == request.getMenuType()) {
			discount.setLiquor(typeValue);
		}
		bill.setDiscount(discount);
		calculateTaxesOnBill(bill);
		mapper.mapBillEntityToDto(bill, dto);
		return dto;
	}

	public BillDto removeDiscount(DiscountRequest request) throws BusinessException {
		BillDto dto = new BillDto();
		BillEntity bill = dao.getBill(request.getBillId());
		if (bill == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_DETAILS_NOT_FOUND);
		}
		if (MenuType.FNB == request.getMenuType()) {
			bill.getDiscount().setFnb(null);
		} else if (MenuType.BAR == request.getMenuType()) {
			bill.getDiscount().setLiquor(null);
		}
		calculateTaxesOnBill(bill);
		mapper.mapBillEntityToDto(bill, dto);
		return dto;
	}

	/**
	 * @param request  
	 */
	public void emailBill(BillIdRequest request) {
//		RestaurantInfoResponse restaurantInfo = restaurantInfoDelegate.getRestaurantInformation();
//		BillEntity bill = dao.getBill(request.getBillId());
//		BillDto billDto = new BillDto();
//		mapper.mapBillEntityToDto(bill, billDto);
//		RawBill rawBill = new RawBill(restaurantInfo.getRestaurantInfo(), billDto);
	}

	public void printBill(BillIdRequest request) throws BusinessException {
		RestaurantInfoResponse restaurantInfo = restaurantInfoDelegate.getRestaurantInformation();
		BillEntity bill = dao.getBill(request.getBillId());
		BillDto billDto = new BillDto();
		mapper.mapBillEntityToDto(bill, billDto);
		RawBill rawBill = new RawBill(restaurantInfo.getRestaurantInfo(), billDto);
		pdfGenerator.generatePDF(rawBill);
	}

	private BillDto addOrderItemsAndCalculateBill(List<OrderUnit> orders, Integer tableId, BillDto originalBill) 
			throws BusinessException {
		BillDto billDto = new BillDto();
		BillEntity billEntity = dao.addOrderItemsToBill(orders, tableId, originalBill);
		calculateTaxesOnBill(billEntity);
		mapper.mapBillEntityToDto(billEntity, billDto);
		// commented this as table is closed at the time of bill settlement
		updateTableWithBillDetails(tableId, billEntity.getId(), Boolean.FALSE);
		return billDto;
	}

	private BillDto updateOrderItemsAndCalculateBill(List<OrderUnit> orders, Integer billId, Integer tableId)
			throws BusinessException {
		BillDto billDto = new BillDto();
		BillEntity billEntity = dao.updateTableOrdersToBill(orders, billId, tableId);
		calculateTaxesOnBill(billEntity);
		mapper.mapBillEntityToDto(billEntity, billDto);
		// commented this as table is closed at the time of bill settlement
		updateTableWithBillDetails(tableId, billId, Boolean.FALSE);
		return billDto;
	}

	private List<OrderUnit> consolidateOrderedItems(List<OrderEntity> orders) {
		Map<Integer, OrderUnit> consolidatedMap = new HashMap<>();
		for (OrderEntity order : orders) {
			for (OrderUnit unit : order.getOrderUnits()) {
				if (!consolidatedMap.containsKey(unit.getItemId())) {
					consolidatedMap.put(unit.getItemId(), unit);
				} else {
					OrderUnit savedUnit = consolidatedMap.get(unit.getItemId());
					savedUnit.setQuantity(savedUnit.getQuantity() + unit.getQuantity());
					savedUnit.setValue(savedUnit.getPrice().multiply(new BigDecimal(savedUnit.getQuantity())));
					consolidatedMap.replace(unit.getItemId(), savedUnit);
				}
			}
		}
		return new ArrayList<>(consolidatedMap.values());
	}

	private void validateTableDetails(Table tableDetails) throws BusinessException {
		// Check if orders are assigned to the respective table
		if (tableDetails == null) {
			throw new BusinessException(BillDetailsErrorCodeType.TABLE_DETAILS_NOT_FOUND);
		}
		if (tableDetails.getOrders() == null || tableDetails.getOrders().isEmpty()) {
			throw new BusinessException(BillDetailsErrorCodeType.ORDERS_NOT_ASSIGNED_TO_TABLE);
		}
	}

	private void calculateTaxesOnBill(BillEntity billEntity) throws BusinessException {
		calculateSubTotal(billEntity);
		List<TaxDetailsEntity> taxDetails = taxDetailsDelegate.getAllTaxDetails();
		if (taxDetails != null && !taxDetails.isEmpty()) {
			taxCalculator.calculateChargesAndTaxesForBill(billEntity, taxDetails);
		}
		dao.saveBill(billEntity);
	}

	// This method is used to update table details with updated bill details
	private void updateTableWithBillDetails(Integer tableId, Integer billId, Boolean flag) throws BusinessException {
		tableDelegate.updateTableWithBillDetails(tableId, billId, flag);
	}

	private void calculateSubTotal(BillEntity bill) {
		TotalEntity subTotal = new TotalEntity();
		BigDecimal fnbSubTotal = BigDecimal.ZERO;
		BigDecimal liquorSubTotal = BigDecimal.ZERO;
		ItemCountEntity itemCount = new ItemCountEntity();
		Integer fnbItemCount = 0;
		Integer liquorItemCount = 0;
		for (OrderUnit item : bill.getOrders()) {
			// Condition for checking an item whether it is
			// FoodNBeverage/Liquor
			if (MenuType.BAR == item.getType()) {
				liquorSubTotal = liquorSubTotal.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
				liquorItemCount += item.getQuantity();
			} else {
				fnbSubTotal = fnbSubTotal.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
				fnbItemCount += item.getQuantity();
			}
		}
		itemCount.setFnbItemCount(fnbItemCount);
		itemCount.setLiqourItemCount(liquorItemCount);
		bill.setItemCount(itemCount);
		subTotal.setName(SUB_TOTAL_WO_CHARGES_TAXES);
		if (bill.getDiscount() != null) {
			DiscountEntity discount = bill.getDiscount();
			TypeValueEntity fnbDiscount = discount.getFnb();
			TypeValueEntity liquorDiscount = discount.getLiquor();
			if (fnbDiscount != null) {
				fnbSubTotal = fnbSubTotal
						.subtract((fnbDiscount.getAmount() != null ? fnbDiscount.getAmount() : BigDecimal.ZERO));
			}
			if (liquorDiscount != null) {
				liquorSubTotal = liquorSubTotal
						.subtract((liquorDiscount.getAmount() != null ? liquorDiscount.getAmount() : BigDecimal.ZERO));
			}
		}
		subTotal.setFnb(fnbSubTotal);
		subTotal.setLiquor(liquorSubTotal);
		bill.setSubTotal(subTotal);

		BigDecimal grandTotal = BigDecimal.ZERO;
		grandTotal = grandTotal.add(subTotal.getFnb()).add(subTotal.getLiquor());
		bill.setGrandTotal(grandTotal);
	}

	public BillDto settleOrEditBill(SettleBillRequest request, boolean isSettle) throws BusinessException {
		BillDto billDto = new BillDto();
		BillEntity bill = dao.getBill(request.getBillId());
		if (bill == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_DETAILS_NOT_FOUND);
		}
		PaymentModeType paymentModeType = PaymentModeType.getPaymentModeByCode(request.getPaymentMode());
		if (PaymentModeType.CANCELLED == paymentModeType) {
			bill.setStatus(BillStatusType.CANCELLED);
			bill.setReasonForCancel(request.getReasonForCancel());
		} else {
			bill.setStatus(BillStatusType.SETTLED);
		}
		bill.setPaymentMode(PaymentModeType.getPaymentModeByCode(request.getPaymentMode()));
		bill.setSyncStatus(CloudSyncStatusType.NOT_IN_SYNC);
		Date settleDateTime = clock.getCurrentDate();
		bill.setSettledDateTime(settleDateTime);
		bill.setSettledDateTimeToDisplay(settleDateTime.toString());
		bill = dao.saveBill(bill);
		if (isSettle) {
			closeTableAndBackUpKotDetails(bill.getTableId(), bill.getId());
		}		
		mapper.mapBillEntityToDto(bill, billDto);
		return billDto;
	}

	private void closeTableAndBackUpKotDetails(Integer tableId, Integer billId) throws BusinessException {
		tableDelegate.closeTable(tableId, billId);
	}

	public List<OrderDto> getAssociatedKots(BillIdRequest request) {
		List<KotDto> associatedKots = dao.getAssociatedKotDetails(request.getBillId());
		List<OrderDto> orderDtos = new ArrayList<>();
		for (KotDto dto : associatedKots) {
			orderDtos.add(dto.getOrder());
		}
		return orderDtos;
	}
}
