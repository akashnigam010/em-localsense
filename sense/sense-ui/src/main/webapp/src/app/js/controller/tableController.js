app.controller('TableController', ['$scope', '$location', 'Services', '$rootScope', '$route', 'growl', '$mdDialog', '$mdMedia', '$window', function($scope, $location, Services, $rootScope, $route, growl, $mdDialog, $mdMedia, $window) {
    $rootScope.pageTitle = 'Tables';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;

    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

    $scope.loadTables = function() {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.getAllTables($rootScope.hosturl, headers).success(function(data) {
            if (data.result) {
                $scope.seatingAreas = data.seatingAreas;
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    };

    var seatingAreaModalObj = {};
    $scope.addSeatingArea = function(ev) {
        seatingAreaModalObj = {};
        seatingAreaModalObj.actionType = 'Add new seating area';
        var userAction = $scope.showSeatingAreaModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.addEditSeatingArea($rootScope.hosturl, seatingAreaModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.seatingAreas = data.seatingAreas;
                        growl.addSuccessMessage("Seating area added successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.editSeatingArea = function(seatingArea, ev) {
        seatingAreaModalObj = angular.copy(seatingArea);
        seatingAreaModalObj.actionType = 'Edit seating area';
        var userAction = $scope.showSeatingAreaModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.addEditSeatingArea($rootScope.hosturl, seatingAreaModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.seatingAreas = data.seatingAreas;
                        growl.addSuccessMessage("Seating area updated successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    tableModalObj = {};
    $scope.addTable = function(seatingArea, ev) {
        tableModalObj = {};
        tableModalObj.actionType = 'Add table to ' + seatingArea.name;
        tableModalObj.seatingAreaId = seatingArea.id;
        var userAction = $scope.showTableModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.addEditTable($rootScope.hosturl, tableModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.seatingAreas = data.seatingAreas;
                        growl.addSuccessMessage("Table added successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.editTable = function(table, seatingArea, ev) {
        tableModalObj = angular.copy(table);
        tableModalObj.seatingAreaId = seatingArea.id;
        tableModalObj.actionType = 'Edit table in ' + seatingArea.name;        
        var userAction = $scope.showTableModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.addEditTable($rootScope.hosturl, tableModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.seatingAreas = data.seatingAreas;
                        growl.addSuccessMessage("Table updated successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.showSeatingAreaModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = seatingAreaModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.seatingAreaModalObj = dataToPass;
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
            templateUrl: 'app/views/manage/tables/addEditSeatingArea.html',
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

    $scope.showTableModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = tableModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.tableModalObj = dataToPass;
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
            templateUrl: 'app/views/manage/tables/addEditTable.html',
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

    var deleteModalObj = {};
    $scope.deleteSeatingArea = function(seatingArea, ev) {
        deleteModalObj = {};
        deleteModalObj.deleteText = "Are you sure you want to delete " + seatingArea.name.toUpperCase() + " ? All tables under this area will be deleted.";
        deleteModalObj.seatingAreaId = seatingArea.id;
        var userAction = $scope.$parent.showDeleteModal(ev, deleteModalObj, $scope.customFullscreen);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.deleteSeatingArea($rootScope.hosturl, deleteModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.seatingAreas = data.seatingAreas;
                        growl.addSuccessMessage("Seating area deleted successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.deleteTable = function(table, ev) {
        deleteModalObj = {};
        deleteModalObj.deleteText = "Are you sure you want to delete table " + table.tableNumber.toUpperCase() + " ?";
        deleteModalObj.tableId = table.id;
        var userAction = $scope.$parent.showDeleteModal(ev, deleteModalObj, $scope.customFullscreen);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.deleteTable($rootScope.hosturl, deleteModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.seatingAreas = data.seatingAreas;
                        growl.addSuccessMessage("Table deleted successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };
}]);
