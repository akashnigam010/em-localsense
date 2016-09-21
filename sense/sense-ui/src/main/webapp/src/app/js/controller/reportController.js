app.controller('ReportController', ['$scope', '$location', 'Services', '$rootScope', '$route', 'growl', '$mdDialog', '$mdMedia', '$window', function($scope, $location, Services, $rootScope, $route, growl, $mdDialog, $mdMedia, $window) {
    $rootScope.pageTitle = 'Sales Report';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;

    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.labels = [];
    $scope.data = [[]];
    $scope.series = ['Daliy sales'];
    $scope.startDate = new Date();
    $scope.endDate = new Date();
    $scope.startDate.setDate($scope.startDate.getDate()-7);

    $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }];
    $scope.options = {
        scales: {
          yAxes: [
            {
              id: 'y-axis-1',
              type: 'linear',
              display: true,
              position: 'left'
            }
          ]
        }
    };

    $scope.searchReportData = function(startDate, endDate) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        var data = {
            startDate: startDate,
            endDate: endDate
        };
        Services.generateSalesReport($rootScope.hosturl, data, headers).success(function(data) {
            if (data.result) {
                setupReportData(data.sales);
                $("#report-search-panel").removeClass('in');
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    };

    $scope.getReportData = function(ev) {
        if ($scope.startDate == '' || $scope.startDate == undefined || $scope.startDate == null) {
            growl.addErrorMessage('Please select start date');
            return;
        }
        if ($scope.endDate == '' || $scope.endDate == undefined || $scope.endDate == null) {
            growl.addErrorMessage('Please select end date');
            return;
        }
        $scope.searchReportData($scope.startDate, $scope.endDate);
    };

    function setupReportData(sales) {
        $.each(sales, function (index, sale) {
            $scope.labels[index] = sale.dateString;
            $scope.data[0][index] = sale.totalSales;
        }); 
    }    

}]);
