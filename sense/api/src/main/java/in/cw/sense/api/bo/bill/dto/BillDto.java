package in.cw.sense.api.bo.bill.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import in.cw.sense.api.bo.bill.type.BillStatusType;
import in.cw.sense.api.bo.bill.type.CloudSyncStatusType;
import in.cw.sense.api.bo.bill.type.PaymentModeType;
import in.cw.sense.api.bo.table.dto.ItemDto;

public class BillDto implements Serializable, Comparable<BillDto> {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String tableNumber;
	private String personName;
	private Integer covers;
	private BillStatusType status;
	private String reasonForCancel;
	private List<ItemDto> orders;
	private ItemCountDto itemCount;
	private DiscountDto discount;
	private TotalDto subTotal;
	private List<ChargeDto> internalCharges;
	private TotalDto subTotalExclusive;
	private List<ChargeDto> taxes;
	private TotalDto subTotalInclusive;
	private BigDecimal grandTotal;
	private PaymentModeType paymentMode;
	private CloudSyncStatusType syncStatus;
	private Date createdDateTime;
	private Date settledDateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public BillStatusType getStatus() {
		return status;
	}

	public void setStatus(BillStatusType status) {
		this.status = status;
	}

	public List<ItemDto> getOrders() {
		return orders;
	}

	public void setOrders(List<ItemDto> orders) {
		this.orders = orders;
	}

	public List<ChargeDto> getInternalCharges() {
		return internalCharges;
	}

	public void setInternalCharges(List<ChargeDto> internalCharges) {
		this.internalCharges = internalCharges;
	}

	public List<ChargeDto> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<ChargeDto> taxes) {
		this.taxes = taxes;
	}

	public TotalDto getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(TotalDto subTotal) {
		this.subTotal = subTotal;
	}

	public TotalDto getSubTotalExclusive() {
		return subTotalExclusive;
	}

	public void setSubTotalExclusive(TotalDto subTotalExclusive) {
		this.subTotalExclusive = subTotalExclusive;
	}

	public TotalDto getSubTotalInclusive() {
		return subTotalInclusive;
	}

	public void setSubTotalInclusive(TotalDto subTotalInclusive) {
		this.subTotalInclusive = subTotalInclusive;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public PaymentModeType getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentModeType paymentMode) {
		this.paymentMode = paymentMode;
	}

	public CloudSyncStatusType getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(CloudSyncStatusType syncStatus) {
		this.syncStatus = syncStatus;
	}

	public ItemCountDto getItemCount() {
		return itemCount;
	}

	public void setItemCount(ItemCountDto itemCount) {
		this.itemCount = itemCount;
	}

	public DiscountDto getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountDto discount) {
		this.discount = discount;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getSettledDateTime() {
		return settledDateTime;
	}

	public void setSettledDateTime(Date settledDateTime) {
		this.settledDateTime = settledDateTime;
	}

	public String getReasonForCancel() {
		return reasonForCancel;
	}

	public void setReasonForCancel(String reasonForCancel) {
		this.reasonForCancel = reasonForCancel;
	}

	public Integer getCovers() {
		return covers;
	}

	public void setCovers(Integer covers) {
		this.covers = covers;
	}

	@Override
	public int compareTo(BillDto o) {
		// sort in descending order of settledDateTime
		// if settlement datetime is same, sort on bill id (desc)
		int result = o.getSettledDateTime().compareTo(this.getSettledDateTime());
		if (result == 0) {
			return o.getId().compareTo(this.getId());
		} else {
			return result;
		}
	}
}
