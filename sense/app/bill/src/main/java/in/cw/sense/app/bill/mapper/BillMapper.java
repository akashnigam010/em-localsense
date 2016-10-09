package in.cw.sense.app.bill.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cwf.date.CwfClock;
import cwf.helper.security.jwt.JwtTokenHelper;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.dto.ChargeDto;
import in.cw.sense.api.bo.bill.dto.DiscountDto;
import in.cw.sense.api.bo.bill.dto.ItemCountDto;
import in.cw.sense.api.bo.bill.dto.RateValueDto;
import in.cw.sense.api.bo.bill.dto.TotalDto;
import in.cw.sense.api.bo.bill.dto.TypeValueDto;
import in.cw.sense.api.bo.bill.entity.BillEntity;
import in.cw.sense.api.bo.bill.entity.ChargeEntity;
import in.cw.sense.api.bo.bill.entity.DiscountEntity;
import in.cw.sense.api.bo.bill.entity.ItemCountEntity;
import in.cw.sense.api.bo.bill.entity.OrderUnit;
import in.cw.sense.api.bo.bill.entity.RateValueEntity;
import in.cw.sense.api.bo.bill.entity.TotalEntity;
import in.cw.sense.api.bo.bill.entity.TypeValueEntity;
import in.cw.sense.api.bo.table.dto.ItemDto;
import in.cw.sense.api.bo.table.dto.TableDto;

@Component
public class BillMapper {
	@Autowired CwfClock clock;
	@Autowired JwtTokenHelper jwtTokenHelper;

	public void mapTableOrderDetailsToBill(TableDto tableDto, BillEntity to, BillDto originalBill) {
		to.setPersonName(jwtTokenHelper.getUserName());
		to.setTableId(tableDto.getId());
		to.setCovers(tableDto.getCovers());
		to.setTableNumber(tableDto.getTableNumber());
		
		if (originalBill != null) {
			// split bill - creating second bill from bill one info
			to.setStatus(originalBill.getStatus());
			to.setSettledDateTime(originalBill.getSettledDateTime());
			to.setSettledDateTimeToDisplay(to.getSettledDateTime().toString());
			to.setPaymentMode(originalBill.getPaymentMode());
			if (StringUtils.isNotEmpty(originalBill.getReasonForCancel())) {
				to.setReasonForCancel(originalBill.getReasonForCancel());
			}			
			to.setCreatedDateTime(originalBill.getCreatedDateTime());
			to.setCreatedDateTimeToDisplay(to.getCreatedDateTime().toString());
		} else if (to.getCreatedDateTime() == null) {
			to.setCreatedDateTime(clock.cal().getTime());
			to.setCreatedDateTimeToDisplay(to.getCreatedDateTime().toString());
		}
	}

	public List<BillDto> mapBillEntitiesToDtos(List<BillEntity> entities) {
		List<BillDto> dtos = new ArrayList<>();
		BillDto billDto = null;
		for (BillEntity entity : entities) {
			billDto = new BillDto();
			mapBillEntityToDto(entity, billDto);
			dtos.add(billDto);
		}
		return dtos;
	}

	public void mapBillEntityToDto(BillEntity from, BillDto to) {
		to.setId(from.getId());
		to.setStatus(from.getStatus());
		to.setReasonForCancel(from.getReasonForCancel());
		to.setPaymentMode(from.getPaymentMode());
		to.setSyncStatus(from.getSyncStatus());
		to.setTaxes(mapChargeEntityToDto(from.getTax()));
		to.setInternalCharges(mapChargeEntityToDto(from.getInternalCharges()));
		to.setOrders(mapOrderEntityItemsToDto(from.getOrders()));
		to.setGrandTotal(from.getGrandTotal());
		to.setPersonName(from.getPersonName());
		to.setSubTotal(mapBillTotalEntityToDto(from.getSubTotal()));
		to.setTableNumber(from.getTableNumber());
		to.setCovers(from.getCovers());
		to.setSubTotalExclusive(mapBillTotalEntityToDto(from.getSubTotalExclusive()));
		to.setSubTotalInclusive(mapBillTotalEntityToDto(from.getSubTotalInclusive()));
		to.setItemCount(mapItemCount(from.getItemCount()));
		to.setDiscount(mapDiscountEntityToDto(from.getDiscount()));
		to.setCreatedDateTime(from.getCreatedDateTime());
		to.setSettledDateTime(from.getSettledDateTime());
	}

	private DiscountDto mapDiscountEntityToDto(DiscountEntity discount) {
		DiscountDto discountDto = new DiscountDto();
		if (discount != null) {
			discountDto.setFnb(mapTypeValueEntityToDto(discount.getFnb()));
			discountDto.setBar(mapTypeValueEntityToDto(discount.getLiquor()));
		}
		return discountDto;
	}

	private TypeValueDto mapTypeValueEntityToDto(TypeValueEntity typeValue) {
		TypeValueDto dto = new TypeValueDto();
		if (typeValue != null) {
			dto.setDiscountType(typeValue.getType());
			dto.setValue(typeValue.getValue());
			dto.setAmount(typeValue.getAmount());
		}
		return dto;
	}

	private ItemCountDto mapItemCount(ItemCountEntity itemCount) {
		ItemCountDto dto = new ItemCountDto();
		dto.setFnb(itemCount.getFnbItemCount());
		dto.setLiquor(itemCount.getLiqourItemCount());
		return dto;
	}

	private List<ChargeDto> mapChargeEntityToDto(List<ChargeEntity> chargeEntities) {
		List<ChargeDto> chargeList = new ArrayList<>();
		if (chargeEntities != null && !chargeEntities.isEmpty()) {
			for (ChargeEntity entity : chargeEntities) {
				if (entity != null) {
					ChargeDto charge = new ChargeDto();
					charge.setName(entity.getName());
					charge.setFnb(mapRateValueEntityToDto(entity.getFnb()));
					charge.setLiquor(mapRateValueEntityToDto(entity.getLiquor()));
					chargeList.add(charge);
				}
			}
		}
		return chargeList;
	}

	private RateValueDto mapRateValueEntityToDto(RateValueEntity from) {
		RateValueDto value = new RateValueDto();
		if (from != null) {
			value.setRate(from.getRate());
			value.setValue(from.getValue());
		}
		return value;
	}

	/*
	 * private List<Order> mapOrderEntityToDto(List<OrderEntity> orderEntities)
	 * { List<Order> orders = new ArrayList<>(); for (OrderEntity entity :
	 * orderEntities) { Order order = new Order();
	 * order.setItems(mapOrderEntityItemsToDto(entity.getOrderUnits()));
	 * order.setId(entity.getId()); orders.add(order); } return orders; }
	 */

	private List<ItemDto> mapOrderEntityItemsToDto(List<OrderUnit> orderUnits) {
		List<ItemDto> items = new ArrayList<>();
		for (OrderUnit unit : orderUnits) {
			ItemDto item = new ItemDto();
			item.setId(unit.getItemId());
			item.setName(unit.getItemName());
			item.setPrice(unit.getPrice());
			item.setQuantity(unit.getQuantity());
			item.setType(unit.getType());
			item.setValue(unit.getValue());
			items.add(item);
		}
		return items;
	}

	private TotalDto mapBillTotalEntityToDto(TotalEntity totalEntity) {
		TotalDto total = new TotalDto();
		if (totalEntity != null) {
			total.setName(totalEntity.getName());
			total.setFnb(totalEntity.getFnb());
			total.setLiquor(totalEntity.getLiquor());
		}
		return total;
	}
}
