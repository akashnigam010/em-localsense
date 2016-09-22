app.controller('SupportController', ['$scope', '$location', 'Services', '$rootScope', '$route', 'growl', '$mdDialog', '$mdMedia', '$window', function($scope, $location, Services, $rootScope, $route, growl, $mdDialog, $mdMedia, $window) {
    $rootScope.pageTitle = 'Help and Support';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;

    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

    $scope.getSupportDetails = function() {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.getSupportDetails($rootScope.hosturl, headers).success(function(data) {
            if (data.result) {
                $scope.contactNumber = data.contactNumber;
                $scope.emailAddress = data.emailAddress;
                $scope.messages = data.messages;
                setDate();
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    };

    function setDate() {
        for (message of $scope.messages) {
            message.convertedDate = new Date(message.dateIso);
        }
    }

    var messageModalObj = {};
    $scope.sendMessage = function(ev) {
        messageModalObj = {};
        var userAction = $scope.showSendMessageModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.sendMessage($rootScope.hosturl, messageModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.messages = data.messages;
                        setDate();
                        growl.addSuccessMessage("Message sent successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.viewMessage = function(message, ev) {
        messageModalObj = {};
        messageModalObj = angular.copy(message);
        if (message.isNew) {
            $window.scrollTo(0, 0);
            $rootScope.loading = true;
            Services.markMessageAsRead($rootScope.hosturl, messageModalObj, headers).success(function(data) {
                if (data.result) {
                    message.isNew = false;
                    $rootScope.newMessagesCount--;
                    $rootScope.loading = false;
                    $scope.showViewMessageModal(ev);
                } else {
                    growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                    $rootScope.loading = false;
                }
            }); 
        } else {
            $scope.showViewMessageModal(ev);
        }               
    };

    var deleteModalObj = {};
    $scope.deleteMessage = function(message, ev) {
        deleteModalObj = {};
        deleteModalObj.deleteText = "Are you sure you want to delete this message ?";
        deleteModalObj.id = message.id;
        var userAction = $scope.$parent.showDeleteModal(ev, deleteModalObj, $scope.customFullscreen);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.deleteMessage($rootScope.hosturl, deleteModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.messages = data.messages;
                        setDate();
                        growl.addSuccessMessage("Message deleted successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    };

    $scope.showSendMessageModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = messageModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.messageModalObj = dataToPass;
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
            templateUrl: 'app/views/support/sendMessage.html',
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

    $scope.showViewMessageModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = messageModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.messageModalObj = dataToPass;
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
            templateUrl: 'app/views/support/viewMessage.html',
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
