'use strict';
/*global app*/

app.factory('Services', ['$http', function($http) {

    var data = {
        table: '',
        orderData: {},
        orderQuantity: 0,
        bill: {}
    };

    var menu = {
        fnbCategories: null,
        fnbSubCategories: null,
        fnbItems: null,
        barCategories: null,
        barSubCategories: null,
        barItems: null
    };

    var billSearchFilterCache = {};


    return {

        /* Destroy order data after adding/editing order */
        destroyOrderData: function() {
            data.orderData = {};
            data.orderQuantity = 0;
        },
        /* Destroy order data at logout */
        destroyAllData: function() {
            data.orderData = {};
            data.orderQuantity = 0;
            data.table = '';
        },

        /****************************** LOCAL CACHE STARTS ***************************/
        getTable: function() {
            return data.table;
        },
        setTable: function(table) {
            data.table = table;
        },
        getOrderData: function() {
            return data.orderData;
        },
        setOrderData: function(orderData) {
            data.orderData = orderData;
        },
        getOrderQuantity: function() {
            return data.orderQuantity;
        },
        setOrderQuantity: function(orderQuantity) {
            data.orderQuantity = orderQuantity;
        },

        getCachedCategories: function(type) {
            if (type == 'fnb') {
                return menu.fnbCategories;
            } else {
                return menu.barCategories;
            }
        },
        setCachedCategories: function(type, categories) {
            if (type == 'fnb') {
                menu.fnbCategories = categories;
            } else {
                menu.barCategories = categories;
            }
        },

        getCachedSubCategories: function(type) {
            if (type == 'fnb') {
                return menu.fnbSubCategories;
            } else {
                return menu.barSubCategories;
            }
        },
        setCachedSubCategories: function(type, subCategories) {
            if (type == 'fnb') {
                menu.fnbSubCategories = subCategories;
            } else {
                menu.barSubCategories = subCategories;
            }
        },

        getCachedItems: function(type) {
            if (type == 'fnb') {
                return menu.fnbItems;
            } else {
                return menu.barItems;
            }
        },
        setCachedItems: function(type, items) {
            if (type == 'fnb') {
                menu.fnbItems = items;
            } else {
                menu.barItems = items;
            }
        },

        getBill: function() {
            return data.bill;
        },
        setBill: function(bill) {
            data.bill = bill;
        },

        getBillSearchFilterCache: function() {
            return billSearchFilterCache;
        },
        setBillSearchFilterCache: function(billSearchObj) {
            billSearchFilterCache.startDate = billSearchObj.startDate;
            billSearchFilterCache.endDate = billSearchObj.endDate;
            billSearchFilterCache.billId = billSearchObj.id;
        },


        /****************************** LOCAL CACHE ENDS ***************************/


        /****************************** SERVICE CALLS START ***************************/

        /****** Login ******/
        login: function(user, url) {
            var loginUrl;
            if (user.accessCode === undefined || user.accessCode === '') {
                loginUrl = 'login/pinSignOn';
            } else {
                loginUrl = 'login/signOn';
            }
            return $http({
                method: 'POST',
                data: user,
                url: url + loginUrl,
                async: false
            });
        },

        isBarAvailable: function(url) {
            return $http({
                method: 'GET',
                url: url + 'login/isBarAvailable',
                async: false
            });
        },

        getNewMessagesCount: function(url) {
            return $http({
                method: 'GET',
                url: url + 'login/getNewMessagesCount',
                async: false
            });
        },

        /****** Dashboard ******/
        loadDashboard: function(url, headers) {
            return $http({
                method: 'GET',
                url: url + 'dashboard/loadDashboard',
                headers: headers,
                async: false
            });
        },

        openTable: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'dashboard/openTable',
                headers: headers,
                async: false
            });
        },

        cancelTable: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'dashboard/cancelTable',
                headers: headers,
                async: false
            });
        },

        getOrders: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'dashboard/viewOrderSummary',
                headers: headers,
                async: false
            });
        },

        addEditOrder: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'dashboard/addEditOrder',
                headers: headers,
                async: false
            });
        },

        /****** Bills ******/
        searchBillsByDate: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/searchBillsByDate',
                headers: headers,
                async: false
            });
        },

        searchBillById: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/searchBillById',
                headers: headers,
                async: false
            });
        },

        goToBill: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/goToBill',
                headers: headers,
                async: false
            });
        },

        addEditDiscount: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/addEditDiscount',
                headers: headers,
                async: false
            });
        },

        removeDiscount: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/removeDiscount',
                headers: headers,
                async: false
            });
        },

        settleBill: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/settleBill',
                headers: headers,
                async: false
            });
        },

        editBill: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/editBill',
                headers: headers,
                async: false
            });
        },

        splitBill: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/splitBill',
                headers: headers,
                async: false
            });
        },

        emailBill: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'bill/emailBill',
                headers: headers,
                async: false
            });
        },

        /****** Menu ******/
        getCategories: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'dashboard/getCategories',
                headers: headers,
                async: false
            });
        },

        addEditCategory: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'menu/addEditCategory',
                headers: headers,
                async: false
            });
        },

        deleteCategory: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'menu/deleteCategory',
                headers: headers,
                async: false
            });
        },

        getSubCategories: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'dashboard/getSubCategories',
                headers: headers,
                async: false
            });
        },

        addEditSubCategory: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'menu/addEditSubCategory',
                headers: headers,
                async: false
            });
        },

        deleteSubCategory: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'menu/deleteSubCategory',
                headers: headers,
                async: false
            });
        },

        getItems: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'dashboard/getItems',
                headers: headers,
                async: false
            });
        },

        addEditItem: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'menu/addEditItem',
                headers: headers,
                async: false
            });
        },

        deleteItem: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'menu/deleteItem',
                headers: headers,
                async: false
            });
        },

        /****** Personnel ******/
        getPersonnels: function(url, headers) {
            return $http({
                method: 'GET',
                data: {},
                url: url + 'personnel/getPersonnels',
                headers: headers,

                async: false
            });
        },

        addEditPersonnel: function(url, person, headers) {
            return $http({
                method: 'POST',
                data: person,
                url: url + 'personnel/addEditPersonnel',
                headers: headers,

                async: false
            });
        },

        deletePersonnel: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'personnel/deletePersonnel',
                headers: headers,
                async: false
            });
        },

        /****** Tables ******/
        getAllTables: function(url, headers) {
            return $http({
                method: 'GET',
                url: url + 'table/getAllTables',
                headers: headers,
                async: false
            });
        },

        addEditSeatingArea: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'table/addEditSeatingArea',
                headers: headers,
                async: false
            });
        },

        deleteSeatingArea: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'table/deleteSeatingArea',
                headers: headers,
                async: false
            });
        },

        deleteTable: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'table/deleteTable',
                headers: headers,
                async: false
            });
        },

        addEditTable: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'table/addEditTable',
                headers: headers,
                async: false
            });
        },

        /****** Tax Details ******/
        getTaxDetails: function(url, headers) {
            return $http({
                method: 'GET',
                url: url + 'taxdetails/getTaxDetails',
                headers: headers,
                async: false
            });
        },

        addEditTax: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'taxdetails/addEditTax',
                headers: headers,
                async: false
            });
        },

        deleteTax: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'taxdetails/deleteTax',
                headers: headers,
                async: false
            });
        },

        /****** Help and Support ******/
        getSupportDetails: function(url, headers) {
            return $http({
                method: 'GET',
                url: url + 'support/getSupportDetails',
                headers: headers,
                async: false
            });
        },
        
        sendMessage: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'support/sendMessage',
                headers: headers,
                async: false
            });
        },
        
        deleteMessage: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'support/deleteMessage',
                headers: headers,
                async: false
            });
        },
        
        markMessageAsRead: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'support/markMessageAsRead',
                headers: headers,
                async: false
            });
        },

        /****** Restaurant Info ******/
        getRestaurantInfo: function(url, headers) {
            return $http({
                method: 'GET',
                url: url + 'restaurantinfo/getRestaurantInfo',
                headers: headers,
                async: false
            });
        },
        
        saveRestaurantInfo: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'restaurantinfo/saveRestaurantInfo',
                headers: headers,
                async: false
            });
        },

        /****** Cloud Settings ******/
        getCloudSettings: function(url, headers) {
            return $http({
                method: 'GET',
                url: url + 'setting/getCloudSettings',
                headers: headers,
                async: false
            });
        },
        
        saveCloudSettings: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'setting/saveCloudSettings',
                headers: headers,
                async: false
            });
        },

        /********** Report **********/
        generateSalesReport: function(url, obj, headers) {
            return $http({
                method: 'POST',
                data: obj,
                url: url + 'report/generateSalesReport',
                headers: headers,
                async: false
            });
        }
        /****************************** SERVICE CALLS END ***************************/

    };
}]);

app.factory('httpInterceptor', ['$rootScope', 'growl', '$location', function($rootScope, growl, $location) {

    function handleErrorResponse(response) {
        $rootScope.loading = false;
        growl.addErrorMessage('Sorry please try again after sometime');
        $rootScope.$broadcast('logout');
    }

    return {
        request: function(config) {
            return config;
        },

        requestError: function(config) {
            return config;
        },

        response: function(response) {
            if (/\b2\d\d/.test(response.status)) {
                return response;
            } else {
                handleErrorResponse(response);
            }
        },

        responseError: function(response) {
            handleErrorResponse(response);
            return response;
        }
    }
}]);
