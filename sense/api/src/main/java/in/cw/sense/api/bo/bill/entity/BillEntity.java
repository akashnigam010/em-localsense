package in.cw.sense.api.bo.bill.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.bill.type.BillStatusType;
import in.cw.sense.api.bo.bill.type.CloudSyncStatusType;
import in.cw.sense.api.bo.bill.type.PaymentModeType;

@Document(collection = "bill")
public class BillEntity {
	@Id
	private Integer id;

	@Field("tableId")
	private Integer tableId;
	
	@Field("tableNumber")
	private String tableNumber;

	@Field("personName")
	private String personName;

	@Field("status")
	private BillStatusType status = BillStatusType.UNSETTLED;
	
	@Field("reasonForCancel")
	private String reasonForCancel;
	
	@Field("orders")
	private List<OrderUnit> orders;
	
	@Field("itemCount")
	private ItemCountEntity itemCount;

	
	@Field("discount")
	private DiscountEntity discount;
	
	@Field("subTotal")
	private TotalEntity subTotal;
	
	@Field("internalCharges")
	private List<ChargeEntity> internalCharges;

	@Field("subTotalExclusive")
	private TotalEntity subTotalExclusive;
	
	@Field("tax")
	private List<ChargeEntity> tax;
	
	@Field("subTotalInclusive")
	private TotalEntity subTotalInclusive;

	@Field("grandTotal")
	private BigDecimal grandTotal = BigDecimal.ZERO;
	
	@Field("paymentMode")
	private PaymentModeType paymentMode;

	@Field("syncStatus")
	private CloudSyncStatusType syncStatus;
	
	@Field("createdDateTime")
	private Date createdDateTime;
	
	@Field("createdDateTimeToDisplay")
	private String createdDateTimeToDisplay;
	
	@Field("settledDateTime")
	private Date settledDateTime;
	
	@Field("settledDateTimeToDisplay")
	private String settledDateTimeToDisplay;

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

	public List<OrderUnit> getOrders() {
		if (orders == null) {
			return new ArrayList<>();
		}
		return orders;
	}

	public void setOrders(List<OrderUnit> orders) {
		this.orders = orders;
	}

	public List<ChargeEntity> getInternalCharges() {
		if (internalCharges == null) {
			return new ArrayList<>();
		}
		return internalCharges;
	}

	public void setInternalCharges(List<ChargeEntity> internalCharges) {
		this.internalCharges = internalCharges;
	}

	public List<ChargeEntity> getTax() {
		if (tax == null) {
			return new ArrayList<>();
		}
		return tax;
	}

	public void setTax(List<ChargeEntity> tax) {
		this.tax = tax;
	}

	public TotalEntity getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(TotalEntity subTotal) {
		this.subTotal = subTotal;
	}

	public TotalEntity getSubTotalExclusive() {
		return subTotalExclusive;
	}

	public void setSubTotalExclusive(TotalEntity subTotalExclusive) {
		this.subTotalExclusive = subTotalExclusive;
	}

	public TotalEntity getSubTotalInclusive() {
		return subTotalInclusive;
	}

	public void setSubTotalInclusive(TotalEntity subTotalInclusive) {
		this.subTotalInclusive = subTotalInclusive;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
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

	public ItemCountEntity getItemCount() {
		return itemCount;
	}

	public void setItemCount(ItemCountEntity itemCount) {
		this.itemCount = itemCount;
	}

	public DiscountEntity getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountEntity discount) {
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

	public String getCreatedDateTimeToDisplay() {
		return createdDateTimeToDisplay;
	}

	public void setCreatedDateTimeToDisplay(String createdDateTimeToDisplay) {
		this.createdDateTimeToDisplay = createdDateTimeToDisplay;
	}

	public String getSettledDateTimeToDisplay() {
		return settledDateTimeToDisplay;
	}

	public void setSettledDateTimeToDisplay(String settledDateTimeToDisplay) {
		this.settledDateTimeToDisplay = settledDateTimeToDisplay;
	}

	public String getReasonForCancel() {
		return reasonForCancel;
	}

	public void setReasonForCancel(String reasonForCancel) {
		this.reasonForCancel = reasonForCancel;
	}
}
