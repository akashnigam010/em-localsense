app.controller('RestaurantInfoController', ['$scope', '$location', 'Services', '$rootScope', '$route', 'growl', '$mdDialog', '$mdMedia', '$window', function($scope, $location, Services, $rootScope, $route, growl, $mdDialog, $mdMedia, $window) {
    $rootScope.pageTitle = 'Restaurant Info';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;

    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

    $scope.getRestaurantInfo = function() {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.getRestaurantInfo($rootScope.hosturl, headers).success(function(data) {
            if (data.result) {
                $scope.restaurantInfo = data.restaurantInfo;
                $rootScope.loading = false;                           
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    };

    var restaurantInfoModalObj = {};
    $scope.editRestaurantInfo = function(ev) {
        restaurantInfoModalObj = {};
        if ($scope.restaurantInfo != null) {
            restaurantInfoModalObj.name = $scope.restaurantInfo.name;
            restaurantInfoModalObj.tagline = $scope.restaurantInfo.tagline;
            restaurantInfoModalObj.barInfo = $scope.restaurantInfo.barInfoType;
            restaurantInfoModalObj.addressLine1 = $scope.restaurantInfo.address.addressLine1;
            restaurantInfoModalObj.addressLine2 = $scope.restaurantInfo.address.addressLine2;
            restaurantInfoModalObj.city = $scope.restaurantInfo.address.city;
            restaurantInfoModalObj.pinCode = $scope.restaurantInfo.address.pinCode;
            restaurantInfoModalObj.phone1 = $scope.restaurantInfo.phone.phone1;
            restaurantInfoModalObj.phone2 = $scope.restaurantInfo.phone.phone2;
            restaurantInfoModalObj.serviceTaxNumber = $scope.restaurantInfo.additionalDetails.serviceTaxNumber;
            restaurantInfoModalObj.tinNumber = $scope.restaurantInfo.additionalDetails.tinNumber;
            restaurantInfoModalObj.group = $scope.restaurantInfo.additionalDetails.group;
        }
        var userAction = $scope.showRestaurantInfoModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.saveRestaurantInfo($rootScope.hosturl, restaurantInfoModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.restaurantInfo = data.restaurantInfo;
                        setBarDetails($scope.restaurantInfo);
                        growl.addSuccessMessage("Restaurant details updated successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    function setBarDetails(restaurantInfo) {
        $rootScope.isBarAvailable = restaurantInfo.barInfoType == 1?false:true;
    };


    $scope.showRestaurantInfoModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = restaurantInfoModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.restaurantInfoModalObj = dataToPass;
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
            templateUrl: 'app/views/restaurantinfo/editRestaurantInfo.html',
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
