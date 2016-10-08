package in.cw.sense.app.bill.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.codec.Base64.OutputStream;

import in.cw.sense.api.bo.bill.dto.RawBill;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class BillPdfGenerator {
	private static final String path = "/src/main/resources/pdf/";

	public Boolean generatePDF(RawBill bill){
		String jasperDesign = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		ArrayList<RawBill> billPrints = new ArrayList<RawBill>();
		billPrints.add(bill);
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(billPrints);

		if (bill.getFnbItems().size() > 0) {

			System.out.println("fnb size:" + bill.getFnbItems().size());
			JRBeanCollectionDataSource fnbListDataSource = new JRBeanCollectionDataSource(bill.getFnbItems());
			parameters.put("fnbListDataSource", fnbListDataSource);

			JRBeanCollectionDataSource fnbChargeDataSource = new JRBeanCollectionDataSource(
					bill.getFnbInternalCharges());
			parameters.put("fnbChargeDataSource", fnbChargeDataSource);

			JRBeanCollectionDataSource fnbTaxesDataSource = new JRBeanCollectionDataSource(bill.getFnbTaxes());
			parameters.put("fnbTaxesDataSource", fnbTaxesDataSource);
			jasperDesign = "fnbReport.jrxml";
		}
		/*
		 * DataSourceList bar
		 */
		if (bill.getBarItems().size() > 0) {
			System.out.println("bar size:" + bill.getBarItems().size());
			JRBeanCollectionDataSource barListDataSource = new JRBeanCollectionDataSource(bill.getBarItems());
			parameters.put("barListDataSource", barListDataSource);

			JRBeanCollectionDataSource barChargeDataSource = new JRBeanCollectionDataSource(
					bill.getBarInternalCharges());
			parameters.put("barChargeDataSource", barChargeDataSource);

			JRBeanCollectionDataSource barTaxesDataSource = new JRBeanCollectionDataSource(bill.getBarTaxes());
			parameters.put("barTaxesDataSource", barTaxesDataSource);
			jasperDesign = "barReport.jrxml";
		}
		/*
		 * for both fnb and bar
		 */

		if (bill.getFnbItems().size() > 0 && bill.getBarItems().size() > 0) {
			jasperDesign = "MasterReport.jrxml";

		}
		JasperReport jasperHeaderReport;
		try {
			InputStream is = BillPdfGenerator.class.getClassLoader().getResourceAsStream("master.jrxml");
			jasperHeaderReport = JasperCompileManager.compileReport(is);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperHeaderReport, parameters, beanColDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/aknigam/Documents/Work/" + bill.getBill().getId() + ".pdf");
			return Boolean.TRUE;
		} catch (JRException e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}

	}
}