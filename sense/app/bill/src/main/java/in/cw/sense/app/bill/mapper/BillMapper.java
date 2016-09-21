package in.cw.sense.app.bill.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cwf.date.CwfClock;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.dto.DiscountDto;
import in.cw.sense.api.bo.bill.dto.ItemCountDto;
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
import in.cw.sense.api.bo.table.dto.Charge;
import in.cw.sense.api.bo.table.dto.Item;
import in.cw.sense.api.bo.table.dto.RateValue;
import in.cw.sense.api.bo.table.dto.TableDto;

@Component
public class BillMapper {
	@Autowired
	CwfClock clock;
	
	public void mapTableOrderDetailsToBill(TableDto tableDto, BillEntity to) {
		to.setPersonName("DEAFULT");
		to.setTableId(tableDto.getId());
		to.setTableNumber(tableDto.getTableNumber());
		if (to.getCreatedDateTime() == null) {
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

	private List<Charge> mapChargeEntityToDto(List<ChargeEntity> chargeEntities) {
		List<Charge> chargeList =  new ArrayList<>();
		if (chargeEntities != null && !chargeEntities.isEmpty()) {
			for (ChargeEntity entity : chargeEntities) {
				if (entity != null) {
					Charge charge = new Charge();
					charge = new Charge();
					charge.setName(entity.getName());
					charge.setFnb(mapRateValueEntityToDto(entity.getFnb()));
					charge.setLiquor(mapRateValueEntityToDto(entity.getLiquor()));
					chargeList.add(charge);
				}
			}
		}
		return chargeList;
	}
	
	private RateValue mapRateValueEntityToDto(RateValueEntity from) {
		RateValue value = new RateValue();
		if (from != null) {
			value.setRate(from.getRate());
			value.setValue(from.getValue());
		}
		return value;
	}
	
	/*private List<Order> mapOrderEntityToDto(List<OrderEntity> orderEntities) {
		List<Order> orders = new ArrayList<>();
		for (OrderEntity entity : orderEntities) {
			Order order = new Order();
			order.setItems(mapOrderEntityItemsToDto(entity.getOrderUnits()));
			order.setId(entity.getId());
			orders.add(order);
		}
		return orders;
	}*/
	
	private List<Item> mapOrderEntityItemsToDto(List<OrderUnit> orderUnits) {
		List<Item> items = new ArrayList<>();
		for (OrderUnit unit : orderUnits) {
			Item item = new Item();
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
			total.setFnb(totalEntity.getFnb());
			total.setLiquor(totalEntity.getLiquor());
		}
		return total;
	}
}