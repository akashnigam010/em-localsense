package in.cw.sense.app.taxdetails;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.tax.dto.TaxDetailsDto;
import in.cw.sense.api.bo.tax.entity.TaxDetailsEntity;
import in.cw.sense.api.bo.tax.request.TaxDetailsRequest;
import in.cw.sense.api.bo.tax.response.TaxDetailsResponse;
import in.cw.sense.app.taxdetails.mapper.TaxDetailsMapper;
import in.cw.sense.app.taxdetails.type.TaxType;

@Service
public class TaxDetailsDelegate {
	@Autowired
	TaxDetailsDao dao;
	@Autowired
	TaxDetailsMapper mapper;

	public TaxDetailsResponse getTaxDetails() throws BusinessException {
		TaxDetailsResponse response = new TaxDetailsResponse();
		List<TaxDetailsDto> listTaxDetailsDto = new ArrayList<TaxDetailsDto>();
		List<TaxDetailsEntity> entities = getAllTaxDetails();
		for (TaxDetailsEntity entity : entities) {
			TaxDetailsDto detailsDto = new TaxDetailsDto();
			detailsDto = mapper.mapEntityToDto(entity, detailsDto);
			listTaxDetailsDto.add(detailsDto);
		}
		mapper.mapTaxDetailsToResponse(listTaxDetailsDto, response);
		return response;
	}

	public TaxDetailsResponse addOrEditTaxDetails(TaxDetailsRequest taxDetailsRequest) throws BusinessException {
		dao.addOrEditTaxDetails(taxDetailsRequest);
		//Tax details once changed must be reflected in existing orders, so deleting all UNSETTLED bills
		deleteUnsettledBillDetails();
		return getTaxDetails();
	}
	
	public TaxDetailsResponse removeTaxDetails(TaxDetailsRequest taxDetailsRequest) throws BusinessException {
		dao.removeTaxDetails(taxDetailsRequest);
		//Tax details once changed must be reflected in existing orders, so deleting all UNSETTLED bills
		deleteUnsettledBillDetails();
		return getTaxDetails();
	}
	
	private void deleteUnsettledBillDetails() throws BusinessException {
		dao.deleteUnsettledBillDetails();
	}
	
	public List<TaxDetailsEntity> getTaxDetailsInternalOrExternal(TaxType taxType) throws BusinessException {
		return dao.getTaxDetailsInternalOrExternal(taxType);
	}
	
	public List<TaxDetailsEntity> getAllTaxDetails() throws BusinessException {
		return dao.getAllTaxDetails();
	}
}
