package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.table.request.AddSeatingAreaRequest;
import in.cw.sense.api.bo.table.request.AddTableDetailsRequest;
import in.cw.sense.api.bo.table.request.SeatingAreaIdRequest;
import in.cw.sense.api.bo.table.request.TableIdRequest;
import in.cw.sense.api.bo.table.response.TableDetailsResponse;
import in.cw.sense.app.tabledetails.TableDetailsDelegate;

@RestController
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OWNER') or hasAuthority('MANAGER')")
@RequestMapping(value = "/table")
public class TableService {
	@Autowired ResponseHelper helper;
	@Autowired TableDetailsDelegate delegate;

	@RequestMapping(value = "/getAllTables", method = RequestMethod.GET, headers = "Accept=application/json")
	public TableDetailsResponse getAllTables() {
		TableDetailsResponse response = new TableDetailsResponse();
		try {
			response = delegate.getAllTableDetails();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addEditSeatingArea", method = RequestMethod.POST, headers = "Accept=application/json")
	public TableDetailsResponse addEditSeatingArea(@RequestBody AddSeatingAreaRequest request) {
		TableDetailsResponse response = new TableDetailsResponse();
		try {
			response = delegate.addEditSeatingArea(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addEditTable", method = RequestMethod.POST, headers = "Accept=application/json")
	public TableDetailsResponse addEditTable(@RequestBody AddTableDetailsRequest request) {
		TableDetailsResponse response = new TableDetailsResponse();
		try {
			response = delegate.addEditTableDetails(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/deleteSeatingArea", method = RequestMethod.POST, headers = "Accept=application/json")
	public TableDetailsResponse deleteSeatingArea(@RequestBody SeatingAreaIdRequest request) {
		TableDetailsResponse response = new TableDetailsResponse();
		try {
			response = delegate.deleteSeatingArea(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/deleteTable", method = RequestMethod.POST, headers = "Accept=application/json")
	public TableDetailsResponse deleteTable(@RequestBody TableIdRequest request) {
		TableDetailsResponse response = new TableDetailsResponse();
		try {
			response = delegate.deleteTableDetails(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
