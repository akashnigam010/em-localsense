app.controller('PersonController', ['$scope', '$location', 'Services', '$rootScope', '$route', 'growl', '$mdDialog', '$mdMedia', '$window', function($scope, $location, Services, $rootScope, $route, growl, $mdDialog, $mdMedia, $window) {
    $rootScope.pageTitle = 'Personnels';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;

    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

    $scope.getpersonnel = function() {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.getPersonnels($rootScope.hosturl, headers).success(function(data) {
            if (data.result) {
                $scope.personnels = data.personnels;
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });

    };

    $scope.isLimitedAccessForDelete = function(personnel) {
        if ($rootScope.user.id == personnel.id) {
            return true;
        } else if ($rootScope.user.roleId < personnel.roleId) {
            return false;
        } else {
            return true;
        }
    };

    $scope.isLimitedAccessForEdit = function(personnel) {
        if ($rootScope.user.roleId <= personnel.roleId) {
            return false;
        } else {
            return true;
        }
    };

    function getRole(roleId) {
        if (roleId == 1) {
            return 'Admin';
        } else if(roleId == 2) {
            return 'Owner';
        } else if(roleId == 3) {
            return 'Manager';
        } else if(roleId == 4) {
            return 'Steward';
        }
    }

    var personnelModalObj = {};
    $scope.addPersonnel = function(ev) {
        personnelModalObj = {};
        personnelModalObj.actionType = 'Add';
        personnelModalObj.roleId = 4;
        var userAction = $scope.showPersonnelModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.addEditPersonnel($rootScope.hosturl, personnelModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.personnels = data.personnels;
                        growl.addSuccessMessage("Personnel added successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.editPersonnel = function(personnel, ev) {
        personnelModalObj = angular.copy(personnel);
        personnelModalObj.actionType = 'Edit';
        personnelModalObj.role = getRole(personnel.roleId);
        var userAction = $scope.showPersonnelModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.addEditPersonnel($rootScope.hosturl, personnelModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.personnels = data.personnels;
                        growl.addSuccessMessage("Personnel updated successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    var deleteModalObj = {};
    $scope.deletePersonnel = function(personnel, ev) {
        deleteModalObj = {};
        deleteModalObj.deleteText = "Are you sure you want to delete " + personnel.name.toUpperCase() + "?";
        deleteModalObj.id = personnel.id;
        var userAction = $scope.$parent.showDeleteModal(ev, deleteModalObj, $scope.customFullscreen);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.deletePersonnel($rootScope.hosturl, deleteModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.personnels = data.personnels;
                        growl.addSuccessMessage("Personnel deleted successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.showPersonnelModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = personnelModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', '$rootScope', function($scope, dataToPass, $rootScope) {
                $scope.personnelModalObj = dataToPass;
                $scope.hide = function() {
                    $mdDialog.hide();
                };
                $scope.cancel = function() {
                    $mdDialog.cancel();
                };
                $scope.answer = function(answer) {
                    $mdDialog.hide(answer);
                };
                $scope.limitedAccessToAdd = function(roleId) {
                    if ($rootScope.user.roleId <= roleId) {
                        return true;
                    } else {
                        return false;
                    }
                } ;               
            }],
            templateUrl: 'app/views/manage/personnel/addEditPersonnel.html',
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
