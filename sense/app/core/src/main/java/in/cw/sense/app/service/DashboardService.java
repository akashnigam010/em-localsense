package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.menu.request.CategoryRequest;
import in.cw.sense.api.bo.menu.request.ItemRequest;
import in.cw.sense.api.bo.menu.request.SubCategoryRequest;
import in.cw.sense.api.bo.menu.response.GetCategoriesResponse;
import in.cw.sense.api.bo.menu.response.GetItemsResponse;
import in.cw.sense.api.bo.menu.response.GetSubCategoriesResponse;
import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.api.bo.response.StatusResponse;
import in.cw.sense.api.bo.table.request.AddOrderRequest;
import in.cw.sense.api.bo.table.request.TableIdRequest;
import in.cw.sense.api.bo.table.request.ViewOrderSummaryRequest;
import in.cw.sense.api.bo.table.response.OrderSummaryResponse;
import in.cw.sense.api.bo.table.response.TableDetailsResponse;
import in.cw.sense.app.menu.MenuDelegate;
import in.cw.sense.app.service.validation.MenuValidator;
import in.cw.sense.app.tabledetails.TableDetailsDelegate;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	TableDetailsDelegate tableDelegate;
	@Autowired
	MenuDelegate menuDelegate;
	@Autowired
	MenuValidator menuValidator;

	@RequestMapping(value = "/loadDashboard", method = RequestMethod.GET, headers = "Accept=application/json")
	public TableDetailsResponse loadDashboard() {
		TableDetailsResponse response = new TableDetailsResponse();
		try {
			response = tableDelegate.loadDashboardData();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addEditOrder", method = RequestMethod.POST, headers = "Accept=application/json")
	public OrderSummaryResponse addEditOrder(@RequestBody AddOrderRequest request) {
		OrderSummaryResponse response = new OrderSummaryResponse();
		try {
			response = tableDelegate.addEditOrderToTable(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/viewOrderSummary", method = RequestMethod.POST, headers = "Accept=application/json")
	public OrderSummaryResponse viewOrderSummary(@RequestBody ViewOrderSummaryRequest request) {
		OrderSummaryResponse response = new OrderSummaryResponse();
		try {
			response = tableDelegate.viewOrderSummary(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/openTable", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse openTable(@RequestBody TableIdRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			tableDelegate.openTable(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/cancelTable", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse cancelTable(@RequestBody TableIdRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			tableDelegate.cancelTable(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getItems", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetItemsResponse getItems(@RequestBody ItemRequest request) {
		GetItemsResponse response = new GetItemsResponse();
		try {
			menuValidator.validateGetItems(request);
			response = menuDelegate.getItems(MenuType.getType(request.getType()));
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getCategories", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetCategoriesResponse getCategories(@RequestBody CategoryRequest request) {
		GetCategoriesResponse response = new GetCategoriesResponse();
		try {
			menuValidator.validateGetCategories(request);
			response = menuDelegate.getCategories(MenuType.getType(request.getType()));
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getSubCategories", method = RequestMethod.POST, headers = "Accept=application/json")
	public GetSubCategoriesResponse getSubCategories(@RequestBody SubCategoryRequest request) {
		GetSubCategoriesResponse response = new GetSubCategoriesResponse();
		try {
			menuValidator.validateGetSubCategories(request);
			response = menuDelegate.getSubCategories(MenuType.getType(request.getType()));
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
