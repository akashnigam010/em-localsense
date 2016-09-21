app.controller('DashboardController', ['$scope', '$location', '$rootScope', 'Services', '$mdToast', '$timeout', '$mdMedia', '$mdDialog', '$window', 'growl', function($scope, $location, $rootScope, Services, $mdToast, $timeout, $mdMedia, $mdDialog, $window, growl) {
    $rootScope.pageTitle = 'Dashboard';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;
    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.loadTables = function() {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.loadDashboard($rootScope.hosturl, headers).success(function(data) {
            if (data.result) {
                $scope.seatingAreas = data.seatingAreas;
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    }


    $scope.redirect = function(location, table, ev) {
        Services.setTable(table);    
        Services.setOrderData({});    
        Services.setOrderQuantity(0);
        $rootScope.resetItemsQuantities = true;
        if (table.status == 'VACANT') {
            openTable(table, location, ev);
        } else {
            $location.path(location);
        }
          
    }

    var openTableModalObj = {};
    function openTable(table, location, ev) {
        openTableModalObj = {};
        openTableModalObj.tableId = table.id;
        openTableModalObj.actionType = 'Open table ' + table.tableNumber;
        var userAction = $scope.showOpenTableModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.openTable($rootScope.hosturl, openTableModalObj, headers).success(function(data) {
                    if (data.result) {
                        $location.path(location);
                        growl.addSuccessMessage("Table "+ table.tableNumber +" open");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');
    $scope.showOpenTableModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = openTableModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.openTableModalObj = dataToPass;
                $scope.hide = function() {
                    $mdDialog.hide();
                };
                $scope.cancel = function() {
                    $mdDialog.cancel();
                };
                $scope.answer = function(answer) {
                    $mdDialog.hide(answer);
                };
            }],
            templateUrl: 'app/views/dashboard/openTable.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: false,
            fullscreen: useFullScreen
        });
        $scope.$watch(function() {
            return $mdMedia('xs') || $mdMedia('sm');
        }, function(wantsFullScreen) {
            $scope.customFullscreen = (wantsFullScreen === true);
        });
    };

}]);
