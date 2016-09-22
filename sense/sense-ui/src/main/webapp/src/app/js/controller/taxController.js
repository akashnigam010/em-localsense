app.controller('TaxController', ['$scope', '$location', '$rootScope', 'Services', '$mdToast', '$timeout', '$mdMedia', '$mdDialog', '$window', 'growl', function($scope, $location, $rootScope, Services, $mdToast, $timeout, $mdMedia, $mdDialog, $window, growl) {
    $rootScope.pageTitle = 'Taxes';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;
    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.internalTaxes = [];
    $scope.externalTaxes = [];

    $scope.getTaxDetails = function() {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.getTaxDetails($rootScope.hosturl, headers).success(function(data) {
            if (data.result) {
                setTaxes(data.taxDetails);
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    }

    function setTaxes(taxes) {
        $scope.internalTaxes = [];
        $scope.externalTaxes = [];
        for (var tax of taxes) {
            if (tax.taxType == 'I') {
                $scope.internalTaxes.push(tax);
            } else {
                $scope.externalTaxes.push(tax);
            }
        }
    }

    var taxModalObj = {};
    $scope.addTax = function(taxType, ev) {
        taxModalObj = {};
        taxModalObj.taxType = taxType;
        taxModalObj.display = taxType == 'I'? ' Internal Charge' : 'Tax';
        taxModalObj.actionType = 'Add new' + (taxType == 'I'? ' Internal Charge' : ' Tax');
        var userAction = $scope.showTaxModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.addEditTax($rootScope.hosturl, taxModalObj, headers).success(function(data) {
                    if (data.result) {
                        setTaxes(data.taxDetails);
                        growl.addSuccessMessage("New " + (taxType == 'I'? 'internal charge' : 'tax') + " added successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.editTax = function(tax, ev) {
        taxModalObj = angular.copy(tax);
        taxModalObj.display = tax.taxType == 'I'? ' Internal Charge' : 'Tax';
        taxModalObj.actionType = 'Edit' + (tax.taxType == 'I'? ' Internal Charge' : ' Tax');
        var userAction = $scope.showTaxModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.addEditTax($rootScope.hosturl, taxModalObj, headers).success(function(data) {
                    if (data.result) {
                        setTaxes(data.taxDetails);
                        growl.addSuccessMessage((tax.taxType == 'I'? 'Internal charge' : 'Tax') + " updated successfully");
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
    $scope.showTaxModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = taxModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.taxModalObj = dataToPass;
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
            templateUrl: 'app/views/manage/taxDetails/addEditTaxDetails.html',
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
    $scope.deleteTax = function(tax, ev) {
        deleteModalObj = {};
        deleteModalObj.deleteText = "Are you sure you want to delete " + tax.taxName.toUpperCase() + " ?";
        deleteModalObj.id = tax.id;
        var userAction = $scope.$parent.showDeleteModal(ev, deleteModalObj, $scope.customFullscreen);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.deleteTax($rootScope.hosturl, deleteModalObj, headers).success(function(data) {
                    if (data.result) {
                        setTaxes(data.taxDetails);
                        growl.addSuccessMessage(tax.taxName.toUpperCase() + " deleted successfully");
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
