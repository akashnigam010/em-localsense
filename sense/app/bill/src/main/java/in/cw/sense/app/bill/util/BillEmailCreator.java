package in.cw.sense.app.bill.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.dto.DiscountDto;
import in.cw.sense.api.bo.bill.dto.RawBill;
import in.cw.sense.api.bo.bill.dto.TypeValueDto;
import in.cw.sense.api.bo.emailTemplate.dto.EmailTemplateDto;
import in.cw.sense.api.bo.emailTemplate.type.EmailTemplateType;
import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.api.bo.table.dto.Item;
import in.cw.sense.app.bill.EmailTemplateDao;

@Service
public class BillEmailCreator {
	@Autowired
	EmailTemplateDao emailTemplateDao;
	private Map<EmailTemplateType, EmailTemplateDto> emailTemplateMap;
	private static final String DISCOUNT_NAME = "Discount";
	private static final String FNB = "Food & Beverage";
	private static final String BAR = "Bar";
	private static final String HEADING = "${heading}";
	private static final String NAME = "${name}";
	private static final String QUANTITY = "${quantity}";
	private static final String VALUE = "${value}";
	private static final String ITEMS = "${items}";
	private static final String DISCOUNT = "${discount}";
	private static final String SUBTOTAL = "${subTotal}";
	private static final String CHARGE_LIST = "${chargeList}";
	private static final String TOTAL_EXCLUSIVE = "${totalExclusive}";
	private static final String TAX_LIST = "${taxList}";
	private static final String TOTAL_INCLUSIVE = "${totalIncl}";
	private static final String RATE = "${rate}";
	private static final String ITEM_COUNT = "${itemCount}";

	public String getBillEmailBody(RawBill bill) throws BusinessException {
		emailTemplateMap = emailTemplateDao.getBillEmailTemplates();
		if (!bill.getFnbItems().isEmpty()) {
			// String fnbCategory
		}
		return null;
	}

	private String getCategoryBodyString(MenuType type, RawBill bill) {
		String heading = type == MenuType.FNB ? FNB : BAR;
		List<Item> items = bill.getFnbItems();
		String categoryString = emailTemplateMap.get(EmailTemplateType.CATEGORY).getValue();
		categoryString = replaceParams(categoryString, HEADING, heading);
		categoryString = replaceParams(categoryString, ITEMS, getItemsListString(items));
		categoryString = replaceParams(categoryString, DISCOUNT, getDiscountString(bill.getBill(), type));
		//TODO : from here		
		categoryString = replaceParams(categoryString, SUBTOTAL, heading);
		categoryString = replaceParams(categoryString, CHARGE_LIST, heading);
		categoryString = replaceParams(categoryString, TOTAL_EXCLUSIVE, heading);
		categoryString = replaceParams(categoryString, TAX_LIST, heading);
		categoryString = replaceParams(categoryString, TOTAL_INCLUSIVE, heading);
		return categoryString;
	}

	private String getItemsListString(List<Item> items) {
		String itemsHtml = "";
		for (Item item : items) {
			String itemHtml = emailTemplateMap.get(EmailTemplateType.ITEM).getValue();
			itemHtml = replaceParams(itemHtml, NAME, item.getName());
			itemHtml = replaceParams(itemHtml, QUANTITY, item.getQuantity().toString());
			itemHtml = replaceParams(itemHtml, VALUE, item.getValue().toString());
			itemHtml.concat(itemHtml);
		}
		return itemsHtml;
	}
	
	private String getDiscountString(BillDto billDto, MenuType type) {
		String discountString = "";
		DiscountDto discountDto = billDto.getDiscount();
		if (discountDto != null) {
			TypeValueDto typeValueDto = null;
			if (type == MenuType.FNB) {
				typeValueDto = discountDto.getFnb();
			} else {
				typeValueDto = discountDto.getBar();
			}
			
			if (typeValueDto != null) {
				discountString = emailTemplateMap.get(EmailTemplateType.CHARGE).getValue();
				discountString = replaceParams(discountString, DISCOUNT, DISCOUNT_NAME);
				if (typeValueDto.getValue() != null) {
					discountString = replaceParams(discountString, RATE, typeValueDto.getValue().toString()+"%");
				}
				discountString = replaceParams(discountString, VALUE, typeValueDto.getAmount().toString());
			}
		}
		
		return discountString;
	}
	
	private String getSubtotalString(BillDto billDto, MenuType type) {
		String subtotal = "";
		String itemCount = "";
		if (type == MenuType.FNB) {
			subtotal = billDto.getSubTotal().getFnb().toString();
			itemCount = billDto.getItemCount().getFnb().toString();
		} else {
			subtotal = billDto.getSubTotal().getLiquor().toString();
		}
		String subTotalString = emailTemplateMap.get(EmailTemplateType.SUBTOTAL).getValue();
		subTotalString = replaceParams(subTotalString, ITEM_COUNT, itemCount);
		subTotalString = replaceParams(subTotalString, VALUE, subtotal);
		return subTotalString;
	}

	private String getParentBodyString(EmailTemplateDto parentBodyTemplate) {

		return null;
	}

	private String replaceParams(String text, String textToReplace, String replaceWith) {
		return text.replace(textToReplace, replaceWith);
	}
}
