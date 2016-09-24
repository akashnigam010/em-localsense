package in.cw.sense.app.bill.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.dto.ChargeDto;
import in.cw.sense.api.bo.bill.dto.DiscountDto;
import in.cw.sense.api.bo.bill.dto.RawBill;
import in.cw.sense.api.bo.bill.dto.TypeValueDto;
import in.cw.sense.api.bo.emailTemplate.dto.EmailTemplateDto;
import in.cw.sense.api.bo.emailTemplate.type.EmailTemplateType;
import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.api.bo.table.dto.ItemDto;
import in.cw.sense.app.bill.EmailTemplateDao;

@Service
public class BillEmailCreator {
	@Autowired
	EmailTemplateDao emailTemplateDao;
	private Map<EmailTemplateType, EmailTemplateDto> emailTemplateMap;
	private static final String DISCOUNT_NAME = "Discount";
	private static final String FNB = "Food & Beverage";
	private static final String BAR = "Bar";
	private static final String IMAGE_SERVER_VALUE = "http://www.whitebay.in/BillEmailImages";
	private static final String FACEBOOK_URL_VALUE = "https://www.facebook.com/ExcuseMe.co.in";
	private static final String TWITTER_URL_VALUE = "https://twitter.com/ExcuseMeIN";
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
	private static final String RESTAURANT_NAME = "${restaurantName}";
	private static final String ITEM_LIST = "${itemList}";
	private static final String GRAND_TOTAL = "${grandTotal}";
	private static final String PROMOTION = "${promotion}";
	private static final String FOOTER = "${footer}";
	private static final String IMAGE_SERVER = "${image-server}";
	private static final String FACEBOOK_URL = "${facebookUrl}";
	private static final String TWITTER_URL = "${twitterUrl}";

	public String getBillEmailBody(RawBill rawBill) throws BusinessException {
		emailTemplateMap = emailTemplateDao.getBillEmailTemplates();
		String parentHtml = emailTemplateMap.get(EmailTemplateType.PARENT).getValue();
		parentHtml = replaceParams(parentHtml, IMAGE_SERVER, IMAGE_SERVER_VALUE);
		parentHtml = replaceParams(parentHtml, RESTAURANT_NAME, rawBill.getRestaurantInfo().getName());
		parentHtml = replaceParams(parentHtml, ITEM_LIST, getItemList(rawBill));
		parentHtml = replaceParams(parentHtml, GRAND_TOTAL, rawBill.getBill().getGrandTotal().toString());
		parentHtml = replaceParams(parentHtml, PROMOTION, getPromotionString());
		parentHtml = replaceParams(parentHtml, FOOTER, getFooterString());
		return parentHtml;
	}

	private String getItemList(RawBill rawBill) {
		String itemsListHtml = "";
		if (!rawBill.getFnbItems().isEmpty()) {
			itemsListHtml += getCategoryBodyString(MenuType.FNB, rawBill);
		}
		if (!rawBill.getBarItems().isEmpty()) {
			itemsListHtml += getCategoryBodyString(MenuType.BAR, rawBill);
		}
		return itemsListHtml;
	}

	private String getCategoryBodyString(MenuType type, RawBill bill) {
		String heading = type == MenuType.FNB ? FNB : BAR;
		List<ItemDto> items = type == MenuType.FNB ? bill.getFnbItems() : bill.getBarItems();
		String categoryString = emailTemplateMap.get(EmailTemplateType.CATEGORY).getValue();
		categoryString = replaceParams(categoryString, HEADING, heading);
		categoryString = replaceParams(categoryString, ITEMS, getItemsListString(items));
		categoryString = replaceParams(categoryString, DISCOUNT, getDiscountString(bill.getBill(), type));
		categoryString = replaceParams(categoryString, SUBTOTAL, getSubtotalString(bill.getBill(), type));
		categoryString = replaceParams(categoryString, CHARGE_LIST, getChargeList(bill.getBill(), type, false));
		categoryString = replaceParams(categoryString, TOTAL_EXCLUSIVE, getTotalExclusive(bill.getBill(), type));
		categoryString = replaceParams(categoryString, TAX_LIST, getChargeList(bill.getBill(), type, true));
		categoryString = replaceParams(categoryString, TOTAL_INCLUSIVE, getTotalInclusive(bill.getBill(), type));
		return categoryString;
	}

	private String getItemsListString(List<ItemDto> items) {
		String itemListHtml = "";
		for (ItemDto item : items) {
			String itemHtml = emailTemplateMap.get(EmailTemplateType.ITEM).getValue();
			itemHtml = replaceParams(itemHtml, NAME, item.getName());
			itemHtml = replaceParams(itemHtml, QUANTITY, item.getQuantity().toString());
			itemHtml = replaceParams(itemHtml, VALUE, item.getValue().toString());
			itemListHtml += itemHtml;
		}
		return itemListHtml;
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
					discountString = replaceParams(discountString, RATE, typeValueDto.getValue().toString() + "%");
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
			itemCount = billDto.getItemCount().getLiquor().toString();
		}
		String subTotalString = emailTemplateMap.get(EmailTemplateType.SUBTOTAL).getValue();
		subTotalString = replaceParams(subTotalString, ITEM_COUNT, itemCount);
		subTotalString = replaceParams(subTotalString, VALUE, subtotal);
		return subTotalString;
	}

	private String getChargeList(BillDto billDto, MenuType type, Boolean isTaxes) {
		String chargeListString = "";
		List<ChargeDto> chargeList = new ArrayList<>();
		if (isTaxes) {
			chargeList = billDto.getTaxes();
		} else {
			chargeList = billDto.getInternalCharges();
		}
		for (ChargeDto charge : chargeList) {
			String chargeHtml = emailTemplateMap.get(EmailTemplateType.CHARGE).getValue();
			if (type == MenuType.FNB) {
				if (charge.getFnb() != null) {
					chargeHtml = replaceParams(chargeHtml, NAME, charge.getName());
					chargeHtml = replaceParams(chargeHtml, RATE, charge.getFnb().getRate().toString());
					chargeHtml = replaceParams(chargeHtml, VALUE, charge.getFnb().getValue().toString());
				}
			} else {
				if (charge.getLiquor() != null) {
					chargeHtml = replaceParams(chargeHtml, NAME, charge.getName());
					chargeHtml = replaceParams(chargeHtml, RATE, charge.getLiquor().getRate().toString());
					chargeHtml = replaceParams(chargeHtml, VALUE, charge.getLiquor().getValue().toString());
				}
			}

			chargeListString += chargeHtml;
		}
		return chargeListString;
	}

	private String getTotalExclusive(BillDto billDto, MenuType type) {
		String totalExcHtml = emailTemplateMap.get(EmailTemplateType.TOTAL_EXCLUSIVE).getValue();
		String totalExc = "";
		if (type == MenuType.FNB) {
			totalExc = billDto.getSubTotalExclusive().getFnb().toString();
		} else {
			totalExc = billDto.getSubTotalExclusive().getLiquor().toString();
		}
		totalExcHtml = replaceParams(totalExcHtml, VALUE, totalExc);
		return totalExcHtml;
	}

	private String getTotalInclusive(BillDto billDto, MenuType type) {
		String totalIncHtml = emailTemplateMap.get(EmailTemplateType.TOTAL_INCLUSIVE).getValue();
		String totalInc = "";
		if (type == MenuType.FNB) {
			totalInc = billDto.getSubTotalInclusive().getFnb().toString();
		} else {
			totalInc = billDto.getSubTotalInclusive().getLiquor().toString();
		}
		totalIncHtml = replaceParams(totalIncHtml, VALUE, totalInc);
		return totalIncHtml;
	}

	private String getPromotionString() {
		String promotionHtml = emailTemplateMap.get(EmailTemplateType.PROMOTION).getValue();
		promotionHtml = replaceParams(promotionHtml, IMAGE_SERVER, IMAGE_SERVER_VALUE);
		promotionHtml = replaceParams(promotionHtml, FACEBOOK_URL, FACEBOOK_URL_VALUE);
		promotionHtml = replaceParams(promotionHtml, TWITTER_URL, TWITTER_URL_VALUE);
		return promotionHtml;
	}

	private String getFooterString() {
		String footerHtml = emailTemplateMap.get(EmailTemplateType.FOOTER).getValue();
		return footerHtml;
	}

	private String replaceParams(String text, String textToReplace, String replaceWith) {
		return text.replace(textToReplace, replaceWith);
	}
}
