package in.cw.sense.app.bill.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.entity.BillEntity;
import in.cw.sense.api.bo.bill.entity.ChargeEntity;
import in.cw.sense.api.bo.bill.entity.RateValueEntity;
import in.cw.sense.api.bo.bill.entity.TotalEntity;
import in.cw.sense.api.bo.tax.entity.TaxDetailsEntity;
import in.cw.sense.app.bill.type.BillDetailsErrorCodeType;
import in.cw.sense.app.taxdetails.TaxDetailsDelegate;
import in.cw.sense.app.taxdetails.type.TaxType;

@Service
public class InternalChargesAndTaxCalculationEngine {
	private static final Logger LOG = Logger.getLogger(InternalChargesAndTaxCalculationEngine.class);
	private static final String SUB_TOTAL_EXCLUSIVE = "SUB TOTAL EXC. TAXES";
	private static final String SUB_TOTAL_INCLUSIVE = "SUB TOTAL INC. TAXES";

	@Autowired
	TaxDetailsDelegate taxDetailsDelegate;

	public void calculateChargesAndTaxesForBill(BillEntity bill, List<TaxDetailsEntity> taxDetails) 
			throws BusinessException {
		List<TaxDetailsEntity> internalCharges = new ArrayList<>();
		List<TaxDetailsEntity> externalTaxes = new ArrayList<>();
		if (bill == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_DETAILS_NOT_FOUND);
		}
		for (TaxDetailsEntity tax : taxDetails) {
			if (TaxType.INTERNAL == TaxType.getTaxByType(tax.getTaxType())) {
				internalCharges.add(tax);
			} else if (TaxType.EXTERNAL == TaxType.getTaxByType(tax.getTaxType())) {
				externalTaxes.add(tax);
			}
		}
		if (!internalCharges.isEmpty()) {
			calculateInternalCharges(bill, internalCharges);
		}
		if (!externalTaxes.isEmpty()) {
			calculateExternalTaxes(bill, externalTaxes);
		}
	}

	private void calculateInternalCharges(BillEntity bill, List<TaxDetailsEntity> taxDetails) 
			throws BusinessException {
		List<ChargeEntity> internalCharges = new ArrayList<>();
		ChargeEntity internalCharge = null;
		TotalEntity subTotalExclusive = new TotalEntity();
		subTotalExclusive.setName(SUB_TOTAL_EXCLUSIVE);
		for (TaxDetailsEntity tax : taxDetails) {
			internalCharge = new ChargeEntity();
			internalCharge.setName(tax.getTaxName());

			BigDecimal fnbSubTotal = bill.getSubTotal().getFnb();
			RateValueEntity fnbRateValue = calculateRateValue(fnbSubTotal, tax.getFnbTax());
			internalCharge.setFnb(fnbRateValue);
			subTotalExclusive.setFnb(fnbSubTotal.add(fnbRateValue.getValue()));

			BigDecimal liquorSubTotal = bill.getSubTotal().getLiquor();
			RateValueEntity liquorRateValue = calculateRateValue(liquorSubTotal, tax.getLiquorTax());
			internalCharge.setLiquor(liquorRateValue);
			subTotalExclusive.setLiquor(liquorSubTotal.add(liquorRateValue.getValue()));
			internalCharges.add(internalCharge);
		}
		bill.setInternalCharges(internalCharges);
		bill.setSubTotalExclusive(subTotalExclusive);

		BigDecimal grandTotal = BigDecimal.ZERO;
		grandTotal = grandTotal.add(subTotalExclusive.getFnb()).add(subTotalExclusive.getLiquor());
		bill.setGrandTotal(grandTotal);
	}

	private void calculateExternalTaxes(BillEntity bill, List<TaxDetailsEntity> taxDetails)
			throws BusinessException {
		List<ChargeEntity> taxes = new ArrayList<>();
		ChargeEntity externalTaxes = null;
		BigDecimal fnbSubTotalWithIntCharges = null;
		BigDecimal liquorSubTotalWithIntCharges = null;
		TotalEntity subTotalInclusive = new TotalEntity();
		subTotalInclusive.setName(SUB_TOTAL_INCLUSIVE);
		TotalEntity subTotalExclusive = bill.getSubTotalExclusive();
		if (subTotalExclusive == null) {
			subTotalExclusive = bill.getSubTotal();
		}
		for (TaxDetailsEntity tax : taxDetails) {
			externalTaxes = new ChargeEntity();
			externalTaxes.setName(tax.getTaxName());

			fnbSubTotalWithIntCharges = subTotalExclusive.getFnb();
			RateValueEntity fnbRateValue = calculateRateValue(fnbSubTotalWithIntCharges, tax.getFnbTax());
			externalTaxes.setFnb(fnbRateValue);
			subTotalInclusive.setFnb(fnbSubTotalWithIntCharges.add(fnbRateValue.getValue()));

			liquorSubTotalWithIntCharges = subTotalExclusive.getLiquor();
			RateValueEntity liquorRateValue = calculateRateValue(liquorSubTotalWithIntCharges, tax.getLiquorTax());
			externalTaxes.setLiquor(liquorRateValue);
			subTotalInclusive.setLiquor(liquorSubTotalWithIntCharges.add(liquorRateValue.getValue()));

			taxes.add(externalTaxes);
		}
		bill.setTax(taxes);
		bill.setSubTotalInclusive(subTotalInclusive);

		BigDecimal grandTotal = BigDecimal.ZERO;
		grandTotal = grandTotal.add(subTotalInclusive.getFnb()).add(subTotalInclusive.getLiquor());
		bill.setGrandTotal(grandTotal);
	}

	private RateValueEntity calculateRateValue(BigDecimal price, BigDecimal rate) {
		RateValueEntity rateValue = new RateValueEntity();
		rateValue.setRate(rate);
		BigDecimal value = (price.multiply(rate)).divide(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
		rateValue.setValue(value);
		return rateValue;
	}
}
