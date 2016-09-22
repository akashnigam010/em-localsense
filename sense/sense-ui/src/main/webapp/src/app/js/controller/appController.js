'use strict';
/*global app*/

app.controller('AppCtrl', ['$scope', '$mdSidenav', '$rootScope', '$location', 'Services', '$mdToast', '$mdMedia', '$mdDialog', '$http', '$timeout', function($scope, $mdSidenav, $rootScope, $location, Services, $mdToast, $mdMedia, $mdDialog, $http, $timeout) {
    $rootScope.isBarAvailable = false;
    $rootScope.newMessagesCount = 0;
    $rootScope.showSearchIcon = false;
    $rootScope.showSearch = false;
    $rootScope.pageTitle = 'Dashboard';
    $scope.searchText = '';

    $http.get('app/config/resource.json').then(function(data) {
        $rootScope.configurationData = data.data.sense;
        $rootScope.hosturl = $rootScope.configurationData.hosturl;
        $rootScope.errorcodes = $rootScope.configurationData.errorcodes;
    });

    $scope.onSwipeRight = function(ev) {
        // do not toggle side Nav if menu tab is swiped to right
        if ($scope.isauthsuccess && ev.target.localName != 'md-tab-item') {
            if (ev.target.parentElement.localName != 'md-tab-item') {
                $mdSidenav('left').toggle();    
            }            
        }        
    }

    $scope.onSwipeLeft = function() {
        $mdSidenav('left').toggle();
    }

    $scope.showSearchBox = function() {
        $rootScope.showSearch = !$rootScope.showSearch;
        $timeout(function() {
            $('#searchInputContainer').children('.md-errors-spacer').remove();
            $('#searchInputBox').focus();
        }, 10);
    }

    $scope.showMainToolbar = function() {
        return ($rootScope.isauthsuccess && !$rootScope.showSearch);
    }

    $scope.showSearchToolbar = function() {
        return ($rootScope.isauthsuccess && $rootScope.showSearch);
    }

    $scope.$on('logout', function(event, data) {
        $scope.logout();
    })

    $scope.logout = function() {
        $rootScope.isauthsuccess = false;
        Services.destroyAllData();
        $scope.activeItem = $scope.sideNavMenu[0];
        $scope.manageNavMenu = [];
        $scope.settingsNavMenu = [];
        $location.path('/');
    };

    $scope.toggleSidenav = function(item) {
        $mdSidenav('left').toggle();
        if (item != undefined) {
            $scope.activeItem = item;
            $location.path(item.href);
        }
    };

    $scope.sideNavMenu = [{
        title: 'Dashboard',
        icon: 'dashboard',
        href: '/dashboard'
    }, {
        title: 'Bills',
        icon: 'receipt',
        href: '/billList'
    }];

    $scope.settingsNavMenu = [];

    $scope.manageNavMenu = [];
    
    $scope.activeItem = $scope.sideNavMenu[0];

    $scope.showStatus = function(text) {
        if (text != '' && text != null) {
            $mdToast.show(
                $mdToast.simple()
                .textContent(text)
                .position('top right')
                .capsule(true)
                .hideDelay(2000)
            );
        }
    };

    $scope.showDeleteModal = function(ev, deleteModalObj, customFullscreen) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen;
        var dataObject = deleteModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.deleteModalObj = dataToPass;
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
            templateUrl: 'app/views/manage/deleteConfirm.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: false,
            fullscreen: useFullScreen
        });
        $scope.$watch(function() {
            return $mdMedia('xs') || $mdMedia('sm');
        }, function(wantsFullScreen) {
            customFullscreen = (wantsFullScreen === true);
        });
    };

}]);
