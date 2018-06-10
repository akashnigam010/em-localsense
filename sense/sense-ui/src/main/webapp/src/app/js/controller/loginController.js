
app.controller('LoginController', ['$scope', '$rootScope', 'Services', 'growl', '$log', function($scope, $rootScope, Services, growl, $log) {
    $scope.user = {
        pin: '',
        accessCode: ''
    };
    $scope.clicked = function(tile) {
        var val = $scope.user.pin;
        if (tile.action == 'backspace' && val.length > 0) {
            val = val.substring(0, val.length - 1);
        }
        if (tile.action == 'delete') {
            val = '';
        }
        if (val.length < 4) {
            val = val + tile.display;

        }
        $scope.user.pin = val;
    }

    $scope.pinPad = (function() {
        var tiles = [];
        for (var i = 1; i <= 9; i++) {
            tiles.push({
                display: i,
                icon: '',
                action: '',
                number: true
            });
        }
        tiles.push({
            display: '',
            icon: 'fa-reply',
            action: 'backspace',
            number: false
        });
        tiles.push({
            display: '0',
            icon: '',
            action: '',
            number: true
        });
        tiles.push({
            display: '',
            icon: 'fa-trash-o',
            action: 'delete',
            number: false
        });
        return tiles;
    })();

    $scope.login = function(user) {
        if (user.pin.length == 0) {
            growl.addInfoMessage('Please enter PIN');
            return;
        }
        $rootScope.loading = true;
        Services.login(user, $rootScope.hosturl).success(function(data) {
            if (data.result) {
                $rootScope.isauthsuccess = true;
                $rootScope.user = data.user;
                getNewMessagesCount();
                openManagementNavigationMenus();
                $rootScope.Authtoken = 'Bearer' + ' ' + data.token;
                location.href = '#/dashboard';
            } else {
                $rootScope.loading = false;
                growl.addErrorMessage('Invalid User');
                $scope.user = {
                    pin: '',
                    accessCode: ''
                };
            }
        });
        setBarDetails();
    };

    function setBarDetails() {
        Services.isBarAvailable($rootScope.hosturl).success(function(data) {
            if (data.result) {
                $rootScope.isBarAvailable = data.barAvailable;
            } else {
                $log.error('Error occurred : ' + data.statusCodes.statusCode[0].description);
            }
        });
    }

    $scope.enter = function(event) {
        if (event.which === 13) {
            $scope.login($scope.user);
        }        
    }

    function getNewMessagesCount() {
        if (($rootScope.user.roleId == 1 || $rootScope.user.roleId == 2 || $rootScope.user.roleId == 3) 
            && $rootScope.user.authorizedToManage) {
            Services.getNewMessagesCount($rootScope.hosturl).success(function(data) {
                if (data.result) {
                    $rootScope.newMessagesCount = data.count;
                } else {
                    $log.error('Error occurred : ' + data.statusCodes.statusCode[0].description);
                }
            });
        }
    }

    function openManagementNavigationMenus() {
        // display only for admin(1), owner(2) and manager(3)
        if (($rootScope.user.roleId == 1 || $rootScope.user.roleId == 2 || $rootScope.user.roleId == 3) 
            && $rootScope.user.authorizedToManage) {
            var manageNavMenuItems = [{
                title: 'Menu',
                icon: 'restaurant_menu',
                href: '/manageMenu'
            }, {
                title: 'Personnels',
                icon: 'group',
                href: '/personnel'
            }, {
                title: 'Tables',
                icon: 'bookmark',
                href: '/tables'
            }, {
                title: 'Tax Details',
                icon: 'account_balance',
                href: '/taxDetails'
            }, {
                title: 'Reports',
                icon: 'insert_chart',
                href: '/report'
            }, {
                title: 'Restaurant Info',
                icon: 'info_outline',
                href: '/restaurantInfo'
            }, {
                title: 'Support Center',
                icon: 'contacts',
                href: '/supportCenter'
            }];

            var settingsNavMenuItems = [{
                title: 'Cloud Settings',
                icon: 'settings',
                href: '/cloudSettings'
            }];

            $.merge($scope.$parent.manageNavMenu, manageNavMenuItems);
            $.merge($scope.$parent.settingsNavMenu, settingsNavMenuItems);
        }
    }
}]);
