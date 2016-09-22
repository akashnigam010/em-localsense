app.controller('CloudSettingController', ['$scope', '$location', 'Services', '$rootScope', '$route', 'growl', '$mdDialog', '$mdMedia', '$window', function($scope, $location, Services, $rootScope, $route, growl, $mdDialog, $mdMedia, $window) {
    $rootScope.pageTitle = 'Cloud Settings';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;

    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

    $scope.getCloudSettings = function() {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.getCloudSettings($rootScope.hosturl, headers).success(function(data) {
            if (data.result) {
                $scope.cloudConnect = data.cloudConnect;
                $rootScope.loading = false;                           
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    };

    var cloudSettingsModalObj = {};
    $scope.editcloudSettings = function(ev) {
        cloudSettingsModalObj = {};
        if ($scope.cloudConnect != null) {
            cloudSettingsModalObj = angular.copy($scope.cloudConnect);
        }        
        var userAction = $scope.showCloudSettingModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                var request = {
                    "cloudConnect" : cloudSettingsModalObj
                };
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.saveCloudSettings($rootScope.hosturl, request, headers).success(function(data) {
                    if (data.result) {
                        $scope.cloudConnect = data.cloudConnect;
                        growl.addSuccessMessage("Cloud settings updated successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };


    $scope.showCloudSettingModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = cloudSettingsModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.cloudSettingsModalObj = dataToPass;
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
            templateUrl: 'app/views/settings/editCloudSetting.html',
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
