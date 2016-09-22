'use strict';

var app = angular.module('sense', ['ngRoute', 'ngSanitize', 'angular-growl', 'ngAnimate', 'ngMaterial', 'ngMessages', 'ngMdIcons', 'chart.js']).
config(['$routeProvider', '$httpProvider', 'growlProvider', '$mdThemingProvider', '$mdDateLocaleProvider', function($routeProvider, $httpProvider, growlProvider, $mdThemingProvider, $mdDateLocaleProvider) {

    growlProvider.globalTimeToLive(3000);
    growlProvider.globalEnableHtml(true);
    
    $httpProvider.interceptors.push('httpInterceptor');
    
    $mdDateLocaleProvider.formatDate = function(date) {
        return moment(date).format('DD-MM-YYYY');
    };

    $routeProvider.
    when('/', {
        templateUrl: function(route) {
            return 'app/views/login.html';
        },
        controller: 'LoginController'
    }).

    when('/dashboard', {
        templateUrl: function(route) {
            return 'app/views/dashboard/dashboard.html';
        },
        controller: 'DashboardController'
    }).

    when('/billList', {
        templateUrl: function(route) {
            return 'app/views/bill/billList.html';
        },
        controller: 'BillListController'
    }).

    when('/goToBill/:isBill?/:id?', {
        templateUrl: function(route) {
            return 'app/views/bill/bill.html';
        },
        controller: 'BillController'
    }).

    when('/personnel', {
        templateUrl: function(route) {
            return 'app/views/manage/personnel/viewPersonnel.html';
        },
        controller: 'PersonController'
    }).
    when('/tables', {
        templateUrl: function(route) {
            return 'app/views/manage/tables/viewTables.html';
        },
        controller: 'TableController'
    }).

    when('/manageMenu', {
        templateUrl: function(route) {
            return 'app/views/manage/menu/menu.html';
        },
        controller: 'ManageMenuController'
    }).

    when('/menu', {
        templateUrl: function(route) {
            return 'app/views/dashboard/menu.html';
        },
        controller: 'MenuController'
    }).

    when('/orderSummary', {
        templateUrl: function(route) {
            return 'app/views/dashboard/orderSummary.html';
        },
        controller: 'OrderSummaryController'
    }).

    when('/taxDetails', {
        templateUrl: function(route) {
            return 'app/views/manage/taxDetails/viewTaxDetails.html';
        },
        controller: 'TaxController'
    }).

    when('/report', {
        templateUrl: function(route) {
            return 'app/views/report/report.html';
        },
        controller: 'ReportController'
    }).

    when('/restaurantInfo', {
        templateUrl: function(route) {
            return 'app/views/restaurantInfo/restaurantInfo.html';
        },
        controller: 'RestaurantInfoController'
    }).

    when('/supportCenter', {
        templateUrl: function(route) {
            return 'app/views/support/support.html';
        },
        controller: 'SupportController'
    }).

    when('/cloudSettings', {
        templateUrl: function(route) {
            return 'app/views/settings/cloudSetting.html';
        },
        controller: 'CloudSettingController'
    }).

    otherwise({
        redirectTo: '/'
    });
}]);

app.run(function($rootScope, $location, $http) {
    $rootScope.$on('$locationChangeSuccess', function() {
        if (!$rootScope.isauthsuccess) {
            $location.path('/');
        }
        if ($location.path() == '/') {
            $rootScope.isauthsuccess = false;
        }
    });

    $rootScope.$on('$locationChangeStart', function(event, next, current) {
        if (/^\/menu(?:.*)/.test($location.path())) {
            $rootScope.showSearchIcon = true;
        } else {
            $rootScope.showSearchIcon = false;
            $rootScope.showSearch = false;
        }
    });
});
