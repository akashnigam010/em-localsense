package in.cw.sense.app.bill.print;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cwf.date.CwfClock;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.dto.ChargeDto;
import in.cw.sense.api.bo.bill.dto.RawBill;
import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.api.bo.restaurant.dto.RestaurantInfoDto;
import in.cw.sense.api.bo.table.dto.ItemDto;

@Component
public class LowLevelBillPrinter {
	@Autowired CwfClock cwfClock;

	int FONT_SIZE_6 = 6;
	int FONT_SIZE_8 = 8;
	int FONT_SIZE_9 = 9;
	int FONT_SIZE_10 = 10;
	int FONT_SIZE_11 = 11;
	int FONT_STYLE_PLAIN = Font.PLAIN;
	int FONT_STYLE_BOLD = Font.BOLD;
	int ORIENTATION = PageFormat.PORTRAIT;
	int START_POS = 3;
	int pointer;
	double PAGE_WIDTH = 7.0;
	String FONT_FAMILY = Font.SANS_SERIF;
	Color PEN_COLOR = Color.BLACK;
	RawBill printObj;

	/**
	 * TODO : Get Items list size
	 *
	 * @param print
	 *
	 * @param args
	 * @throws BusinessException
	 */
	public void actualPrint(RawBill rawBill) throws BusinessException {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		PageFormat pageFormat = printJob.defaultPage();
		pageFormat.setOrientation(ORIENTATION);
		Paper paper = pageFormat.getPaper();
		//Set this based on the printable paper width
		double width = fromCmToPpi(PAGE_WIDTH);
		//Set this based on the items ordered
		double height = calculatePaperHeight(rawBill);
		paper.setSize(width, height);
		//Below settings are used to providing the boxing for printable area on the bill
		paper.setImageableArea(
				fromCmToPpi(0.25),
				fromCmToPpi(0.50),
				width - fromCmToPpi(0.35),
				height - fromCmToPpi(1));
		/*paper.setImageableArea(0,
				fromCmToPpi(0.50),
				width, //0.35
				height - fromCmToPpi(1));*/
		pageFormat.setPaper(paper);
		//Currently this below code is not being used
		//dumpPageFormat(pageFormat);
		printJob.setPrintable(new MyPrintable(), pageFormat);
		printObj = rawBill;
		try {
			printJob.print();
		} catch (PrinterException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}

	/**
	 * Calculates number of lines required for setting paper, considers only
	 * header, footer lines
	 *
	 * @param print
	 * @return double
	 */
	private double calculatePaperHeight(RawBill rawBill) {
		// '10' is for leaving empty paper above the header and below the footer
		double paperHeight = (10 * FONT_SIZE_10);
		paperHeight = calculateHeaderFooterHeight(rawBill, paperHeight);
		paperHeight = calculateOrderItemsHeight(rawBill, paperHeight);
		paperHeight = calculateAdditionalDetailsHeight(rawBill, paperHeight);
		return paperHeight;
	}
	
	/**
	 * Checks and returns number of lines present in header and footer
	 *
	 * @param print
	 * @return double
	 */
	private double calculateHeaderFooterHeight(RawBill bill, double paperHeight) {
		RestaurantInfoDto restaurantInfo = bill.getRestaurantInfo();
		double count = 0;
		if (StringUtils.isNotEmpty(restaurantInfo.getName())) {
			count++;
		}
		if (StringUtils.isNotEmpty(restaurantInfo.getTagline())) {
			count++;
		}
		if (StringUtils.isNotEmpty(restaurantInfo.getAddress().getAddressLine1())) {
			count++;
		}
		if (StringUtils.isNotEmpty(restaurantInfo.getAddress().getAddressLine2())) {
			count++;
		}
		if (StringUtils.isNotEmpty(restaurantInfo.getPhone().getPhone1())) {
			count++;
		}
		if (StringUtils.isNotEmpty(restaurantInfo.getAdditionalDetails().getServiceTaxNumber())) {
			count++;
		}
		if (StringUtils.isNotEmpty(restaurantInfo.getAdditionalDetails().getTinNumber())) {
			count++;
		}
		if (StringUtils.isNotEmpty(restaurantInfo.getAdditionalDetails().getGroup())) {
			count++;
		}
		
		return paperHeight + (count * FONT_SIZE_10);
	}
	
	/**
	 * Checks and returns number of lines required for order items
	 *
	 * @param print
	 * @return double
	 */
	private double calculateOrderItemsHeight(RawBill bill, double paperHeight) {
		BillDto billDetails = bill.getBill();
		return paperHeight + ((billDetails.getOrders().size()) * FONT_SIZE_10) + (13 * FONT_SIZE_10);
	}
	
	/**
	 * Checks and returns number of lines required for tax details
	 *
	 * @param print
	 * @return double
	 */
	private double calculateAdditionalDetailsHeight(RawBill bill, double paperHeight) {
		BillDto billDetails = bill.getBill();
		int counter = 0;
		if (billDetails.getDiscount().getFnb().getValue() != null) {
			counter++;
		}
		if (billDetails.getDiscount().getBar().getValue() != null) {
			counter++;
		}
		if (billDetails.getSubTotal().getFnb() != null) {
			counter++;
		}
		if (billDetails.getSubTotal().getLiquor() != null) {
			counter++;
		}
		if (billDetails.getInternalCharges()!= null && billDetails.getInternalCharges().size() != 0) {
			for (ChargeDto internalCharge : billDetails.getInternalCharges()) {
				if (internalCharge.getFnb() != null && internalCharge.getFnb().getValue() != null) {
					counter++;
				}
				if (internalCharge.getLiquor() != null && internalCharge.getLiquor().getValue() != null) {
					counter++;
				}
			}
		}
		if (billDetails.getSubTotalExclusive() != null && billDetails.getSubTotalExclusive().getFnb() != null) {
			counter++;
		}
			
		if (billDetails.getTaxes()!= null && billDetails.getTaxes().size() != 0) {
			for (ChargeDto tax : billDetails.getTaxes()) {
				if (tax.getFnb() != null && tax.getFnb().getValue() != null) {
					counter++;
				}
				if (tax.getLiquor() != null && tax.getLiquor().getValue() != null) {
					counter++;
				}
			}
		}
		
		if (billDetails.getSubTotalExclusive() != null && billDetails.getSubTotalExclusive().getLiquor() != null) {
			counter++;
		}

		if (billDetails.getGrandTotal() != null) {
			counter++;
		}
		
		if (billDetails.getOrders() != null) {
			counter = counter + billDetails.getOrders().size();
		}
		
		return paperHeight + (counter * FONT_SIZE_10) + (13 * FONT_SIZE_10);
	}

	/*
	 * Convert CM(Centimeter) to PPI (Pixels Per Inch)
	 */
	private double fromCmToPpi(double cm) {
		return toPPI(cm * 0.393700787);
	}

	private double toPPI(double inch) {
		return inch * 72d;
	}

	//Currently this method is not being used
	/*private String dumpPageFormat(PageFormat pageFormat) {
		Paper paper = pageFormat.getPaper();
		return dumpPaper(paper);
	}

	private String dumpPaper(Paper paper) {
		StringBuilder sb = new StringBuilder(64);
		sb.append(paper.getWidth()).append("x")
		.append(paper.getHeight()).append("/").append(paper.getImageableX())
				.append("x").append(paper.getImageableY()).append(" - ")
				.append(paper.getImageableWidth()).append("x")
				.append(paper.getImageableHeight());
		return sb.toString();
	}*/

	public class MyPrintable implements Printable {
		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			int result = NO_SUCH_PAGE;
			if (pageIndex < 1) {
				graphics.setColor(PEN_COLOR);
				graphics.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 10));
				Graphics2D pen = (Graphics2D) graphics;
				int width = (int) pageFormat.getImageableWidth();
				int height = (int) pageFormat.getImageableHeight();
				pen.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
				Rectangle border = new Rectangle(1, 1, width - 1, height - 1);
				//Change this line for printing borders
				//pen.draw(border);
				FontMetrics metrics = pen.getFontMetrics();
				pointer = metrics.getAscent();
				drawBillHeaderDetails(pen, border);
				lineSeperator(pen);
				drawBillOrderDetails(pen, border);
				lineSeperator(pen);

				BillDto billDto = printObj.getBill();
				if (billDto.getItemCount().getFnb() != 0) {
					alignToCenter(pen, "Food and  Beverage", border, 
							createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 8));
					drawBillItemsDescription(pen, border);
					drawItems(MenuType.FNB, pen, border, metrics);
					drawFandBAmountdetails(pen, border, metrics);
				}
				if (billDto.getItemCount().getLiquor() != 0) {
					alignToCenter(pen, "Bar", border, createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 10));
					drawBillItemsDescription(pen, border);
					drawItems(MenuType.BAR, pen, border, metrics);
					drawBarAmountdetails(pen, border, metrics);
				}

				drawGrandTotal(pen, border);
				pointer = drawFooter(pen, border);
				result = PAGE_EXISTS;
			}
			return result;
		}

		/**
		 * Prints Header Values, Sets max length to 33(Based on Font size)
		 *
		 * @param info
		 * @param pen
		 * @param pointer
		 * @param border
		 * @return
		 */
		private int drawBillHeaderDetails(Graphics2D pen, Rectangle border) {
			RestaurantInfoDto restaurantInfo = printObj.getRestaurantInfo();
			if (StringUtils.isNotEmpty(restaurantInfo.getName())) {
				alignToCenter(pen, restaurantInfo.getName(), 
						border, createFont(FONT_FAMILY, FONT_STYLE_BOLD, 11));
			}

			if (StringUtils.isNotEmpty(restaurantInfo.getTagline())) {
				alignToCenter(pen, restaurantInfo.getTagline(), 
						border, createFont(FONT_FAMILY, FONT_STYLE_BOLD, 8));
			}

			if (StringUtils.isNotEmpty(restaurantInfo.getAddress().getAddressLine1())) {
				alignToCenter(pen, restaurantInfo.getAddress().getAddressLine1(), 
						border, createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			}

			if (StringUtils.isNotEmpty(restaurantInfo.getAddress().getAddressLine2())) {
				alignToCenter(pen, restaurantInfo.getAddress().getAddressLine2(), 
						border, createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			}

			if (StringUtils.isNotEmpty(restaurantInfo.getAddress().getCity())) {
				alignToCenter(pen, 
						restaurantInfo.getAddress().getCity() + "-" + restaurantInfo.getAddress().getPinCode(), 
						border, createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			}

			if (StringUtils.isNotEmpty(restaurantInfo.getPhone().getPhone1())) {
				alignToCenter(pen, restaurantInfo.getPhone().getPhone1(), 
						border, createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			}
			return pointer;
		}

		/**
		 * Draws a line with length adjusted to 33 characters
		 *
		 * @param pen
		 * @param pointer
		 * @return
		 */
		private void lineSeperator(Graphics2D pen) {
			pen.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", START_POS, pointer);
			getPointerPosition();
		}

		/**
		 * Draws Order details
		 *
		 * @param printObj
		 * @param pen
		 * @param pointer
		 * @param rect
		 * @return
		 */
		private void drawBillOrderDetails(Graphics2D pen, Rectangle rect) {
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			BillDto billDto = printObj.getBill();
			int idealspace = 15;
			pen.drawString("Bill", START_POS, pointer);
			pen.drawString(":", (rect.width / 4) - 5, pointer);
			pen.drawString(billDto.getId().toString(), rect.width / 4, pointer);
			pen.drawString("Table", ((rect.width / 4) * 2) + idealspace, pointer);
			pen.drawString(":", ((rect.width / 4) * 3) + idealspace - 5, pointer);
			pen.drawString(billDto.getTableNumber(), ((rect.width / 4) * 3) + idealspace, pointer);
			getPointerPosition();

			pen.drawString("Date", START_POS, pointer);
			pen.drawString(":", (rect.width / 4) - 5, pointer);
			pen.drawString(cwfClock.formatDateToDDMMYYYY(billDto.getCreatedDateTime()), rect.width / 4, pointer);
			pen.drawString("Steward", ((rect.width / 4) * 2) + idealspace, pointer);
			pen.drawString(":", ((rect.width / 4) * 3) + idealspace - 5, pointer);
			pen.drawString(billDto.getPersonName().substring(0, 6)+"...", ((rect.width / 4) * 3) + idealspace, pointer);
			getPointerPosition();

			pen.drawString("Time", START_POS, pointer);
			pen.drawString(":", (rect.width / 4) - 5, pointer);
			pen.drawString(cwfClock.getTimeAsHHMMAMPM(billDto.getCreatedDateTime()), rect.width / 4, pointer);
			pen.drawString("Covers:", ((rect.width / 4) * 2) + idealspace, pointer);
			pen.drawString(":", ((rect.width / 4) * 3) + idealspace - 5, pointer);
			pen.drawString(billDto.getCovers().toString(), ((rect.width / 4) * 3) + idealspace, pointer);
			getPointerPosition();
		}

		/**
		 * Draws Item description
		 *
		 * @param printObj
		 * @param pen
		 * @param rect
		 */
		private void drawBillItemsDescription(Graphics2D pen, Rectangle rect) {
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_BOLD, 8));
			int area = rect.width / 10;
			pen.drawString("Item", area * 2, pointer);
			pen.drawString("Qty", (int) (area * 6.5), pointer);
			pen.drawString("Value", area * 8, pointer);
			getPointerPosition();
		}

		/**
		 * Draws F&B Items
		 *
		 * @param pen
		 * @param rect
		 * @param metrics
		 */
		private void drawItems(MenuType menuType, Graphics2D pen, Rectangle rect, FontMetrics metrics) {
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			BillDto billDto = printObj.getBill();
			for (ItemDto item : billDto.getOrders()) {
				if (menuType == item.getType()) {
					if (item.getName().length() <= 21) {
						pen.drawString(item.getName(), START_POS, pointer);
					} else {
						pen.drawString(item.getName().substring(0, 18) + " . . . ", START_POS, pointer);
					}
					pen.drawString(item.getQuantity().toString(), (rect.width / 10) * 7, pointer);
					pen.drawString(item.getPrice().toString(), 
							rect.width - metrics.stringWidth(item.getPrice().toString()), pointer);
					getPointerPosition();
				}
			}
			getPointerPosition();
		}

		/**
		 * Draws F&B Object details
		 *
		 * @param pen
		 * @param rect
		 * @param metrics
		 * @return
		 */
		private void drawFandBAmountdetails(Graphics2D pen, Rectangle rect, FontMetrics metrics) {
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			BillDto billDto = printObj.getBill();
			//int idealspace = 15;
			if (billDto.getDiscount().getFnb() != null && billDto.getDiscount().getFnb().getValue() != null) {
				String discountValue = billDto.getDiscount().getFnb().getValue().toString();
				pen.drawString("Discount", START_POS, pointer);
				pen.drawString(discountValue, rect.width - metrics.stringWidth(discountValue), pointer);
				getPointerPosition();
			}

			if (billDto.getSubTotal() != null && billDto.getSubTotal().getFnb() != null) {
				String subTotalValue = billDto.getSubTotal().getFnb().toString();
				pen.drawString("Sub Total", START_POS, pointer);
				pen.drawString(subTotalValue, rect.width - metrics.stringWidth(subTotalValue), pointer);
				getPointerPosition();
			}

			for (ChargeDto internalCharge : billDto.getInternalCharges()) {
				if (internalCharge.getFnb() != null && internalCharge.getFnb().getValue() != null) {
					pen.drawString(internalCharge.getName(), START_POS, pointer);
					String fnbInternalShargeValue = internalCharge.getFnb().getValue().toString();
					pen.drawString(fnbInternalShargeValue, 
							rect.width - metrics.stringWidth(fnbInternalShargeValue), pointer);
					getPointerPosition();
				}
			}

			String fnbSubTotalExclusive = billDto.getSubTotalExclusive().getFnb().toString();
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_BOLD, 9));
			pen.drawString("Total (Exc. Taxes)", START_POS, pointer);
			pen.drawString(fnbSubTotalExclusive, rect.width - metrics.stringWidth(fnbSubTotalExclusive), pointer);
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			getPointerPosition();

			for (ChargeDto tax : billDto.getTaxes()) {
				if (tax.getFnb() != null && tax.getFnb().getValue() != null) {
					pen.drawString(tax.getName(), START_POS, pointer);
					String fnbExternalTax = tax.getFnb().getValue().toString();
					pen.drawString(fnbExternalTax, rect.width - metrics.stringWidth(fnbExternalTax), pointer);
					getPointerPosition();
				}
			}

			String fnbSubTotalInclusive = billDto.getSubTotalInclusive().getFnb().toString();
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_BOLD, 9));
			pen.drawString("Total (Inc. Taxes)", START_POS, pointer);
			pen.drawString(fnbSubTotalInclusive, rect.width - metrics.stringWidth(fnbSubTotalInclusive), pointer);
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			getPointerPosition();

			lineSeperator(pen);
		}

		private void drawBarAmountdetails(Graphics2D pen, Rectangle rect, FontMetrics metrics) {
			BillDto billDto = printObj.getBill();
			//int idealspace = 15;
			if (billDto.getDiscount().getBar() != null && billDto.getDiscount().getBar().getValue() != null) {
				String discountValue = billDto.getDiscount().getBar().getValue().toString();
				pen.drawString("Discount", START_POS, pointer);
				pen.drawString(discountValue, rect.width - metrics.stringWidth(discountValue), pointer);
				getPointerPosition();
			}

			if (billDto.getSubTotal() != null && billDto.getSubTotal().getLiquor() != null) {
				String subTotalValue = billDto.getSubTotal().getLiquor().toString();
				pen.drawString("Sub Total", START_POS, pointer);
				pen.drawString(subTotalValue, rect.width - metrics.stringWidth(subTotalValue), pointer);
				getPointerPosition();
			}

			for (ChargeDto internalCharge : billDto.getInternalCharges()) {
				if (internalCharge.getLiquor() != null && internalCharge.getLiquor().getValue() != null) {
					pen.drawString(internalCharge.getName(), START_POS, pointer);
					String barInternalCharges = internalCharge.getLiquor().getValue().toString();
					pen.drawString(barInternalCharges, 
							rect.width - metrics.stringWidth(barInternalCharges), pointer);
					getPointerPosition();
				}
			}

			String barSubTotalExclusive = billDto.getSubTotalExclusive().getLiquor().toString();
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_BOLD, 9));
			pen.drawString("Total (Exc. Taxes)", START_POS, pointer);
			pen.drawString(barSubTotalExclusive, rect.width - metrics.stringWidth(barSubTotalExclusive), pointer);
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			getPointerPosition();

			for (ChargeDto tax : billDto.getTaxes()) {
				if (tax.getLiquor() != null && tax.getLiquor().getValue() != null) {
					pen.drawString(tax.getName(), START_POS, pointer);
					String barExternalTaxes = tax.getLiquor().getValue().toString();
					pen.drawString(barExternalTaxes, rect.width - metrics.stringWidth(barExternalTaxes), pointer);
					getPointerPosition();
				}
			}

			String barSubTotalInclusive = billDto.getSubTotalInclusive().getLiquor().toString();
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_BOLD, 9));
			pen.drawString("Total (Inc. Taxes)", START_POS, pointer);
			pen.drawString(barSubTotalInclusive, rect.width - metrics.stringWidth(barSubTotalInclusive), pointer);
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 9));
			getPointerPosition();
		}

		private void drawGrandTotal(Graphics2D pen, Rectangle rect) {
			int idealspace = 20;
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_BOLD, 10));
			lineSeperator(pen);
			pen.drawString("Grand Total : Rs. ", START_POS + idealspace, pointer);
			pen.drawString(printObj.getBill().getGrandTotal().toString(), rect.width / 2 + idealspace, pointer);
			getPointerPosition();
			lineSeperator(pen);
			pen.setFont(createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 10));
			getPointerPosition();
		}

		private int drawFooter(Graphics2D pen, Rectangle rect) {
			RestaurantInfoDto restaurantInfo = printObj.getRestaurantInfo();
			if (StringUtils.isNotEmpty(restaurantInfo.getAdditionalDetails().getServiceTaxNumber())) {
				alignToCenter(
						pen, "ST No : " + restaurantInfo.getAdditionalDetails().getServiceTaxNumber(), rect, 
						createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 6));
			}
			if (StringUtils.isNotEmpty(restaurantInfo.getAdditionalDetails().getTinNumber())) {
				alignToCenter(
						pen, "Tin No : " + restaurantInfo.getAdditionalDetails().getTinNumber(), rect, 
						createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 8));
			}
			if (StringUtils.isNotEmpty(restaurantInfo.getAdditionalDetails().getGroup())) {
				alignToCenter(
						pen, restaurantInfo.getAdditionalDetails().getGroup(), rect, 
						createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 6));
			}
			alignToCenter(pen, "Thank You.. Visit Again", rect, createFont(FONT_FAMILY, FONT_STYLE_PLAIN, 6));
			return pointer;
		}

		private void alignToCenter(Graphics pen, String text, Rectangle rect, Font fontSize) {
			FontMetrics metrics = pen.getFontMetrics(fontSize);
			int x = (rect.width - metrics.stringWidth(text)) / 2;
			pen.setFont(fontSize);
			pen.drawString(text, x, pointer);
			getPointerPosition();
		}

		/**
		 * Returns pointer location
		 */
		private void getPointerPosition() {
			pointer = pointer + FONT_SIZE_10;
		}
		
		private Font createFont(String fontFamily, int fontStylePlain, int fontSize) {
			return new Font(fontFamily, fontStylePlain, fontSize);
		}
	}
}
