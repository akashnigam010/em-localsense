package in.cw.sense.api.bo.bill.request;

public class SettleBillRequest {
	private Integer billId;
	private Integer paymentMode;
	private String reasonForCancel;

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Integer getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getReasonForCancel() {
		return reasonForCancel;
	}

	public void setReasonForCancel(String reasonForCancel) {
		this.reasonForCancel = reasonForCancel;
	}
}
