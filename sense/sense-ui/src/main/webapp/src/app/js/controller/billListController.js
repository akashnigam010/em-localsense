app.controller('BillListController', ['$scope', '$location', 'Services', '$rootScope', '$route', 'growl', '$mdDialog', '$mdMedia', '$window', function($scope, $location, Services, $rootScope, $route, growl, $mdDialog, $mdMedia, $window) {
    $scope.location = $location.path();
    $rootScope.pageTitle = 'Bills';
    var token = $rootScope.Authtoken;

    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    if (jQuery.isEmptyObject(Services.getBillSearchDates())) {
        $scope.startDate = $scope.endDate = new Date();
    } else {
        $scope.startDate = Services.getBillSearchDates().startDate;
        $scope.endDate = Services.getBillSearchDates().endDate;
    }
    

    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

    $scope.getBills = function(startDate, endDate) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        var data = {
            startDate: startDate,
            endDate: endDate
        };
        Services.setBillSearchDates(data);
        Services.searchBills($rootScope.hosturl, data, headers).success(function(data) {
            if (data.result) {
                $scope.openTables = data.openTables;
                $scope.bills = data.settledBills;
                $("#bill-search-panel").removeClass('in');
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    };

    $scope.searchBill = function(ev) {
        if ($scope.when == 1) {
            // today
            var today = new Date();
            $scope.getBills(today, today);
        } else if ($scope.when == 2) {
            // yesterday
            var today = new Date();
            var yesterday = new Date(today);
            yesterday.setDate(today.getDate() - 1);
            $scope.getBills(yesterday, yesterday);
        } else if ($scope.when == 3) {
            // date range
            if ($scope.startDate == '' || $scope.startDate == undefined || $scope.startDate == null) {
                growl.addErrorMessage('Please select start date');
                return;
            }
            if ($scope.endDate == '' || $scope.endDate == undefined || $scope.endDate == null) {
                growl.addErrorMessage('Please select end date');
                return;
            }
            $scope.getBills($scope.startDate, $scope.endDate);
        }
    }

    $scope.goToBill = function(data, isSettledBill) {
        if (isSettledBill) {
            Services.setBill(data);
        }
        $location.path('/goToBill/' + isSettledBill + '/' + data.id);
    };

}]);
