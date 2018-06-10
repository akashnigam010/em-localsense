app.controller('OrderSummaryController', ['$scope', '$location', '$rootScope', 'Services', '$window', 'growl', '$mdMedia', '$mdDialog', function($scope, $location, $rootScope, Services, $window, growl, $mdMedia, $mdDialog) {
    $rootScope.pageTitle = 'Order Summary';
    $scope.location = $location.path();
    var token = $rootScope.Authtoken;
    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };

    $scope.orderMap = {};
    $scope.totalItems = 0;
    $scope.totalPrice = 0.00;
    $scope.table = {};
    $scope.orders = [];
    $scope.cancelTable = false;
    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

    function getTotalPrice(orderMap) {
        var totalPrice = 0.00;
        for (var key in orderMap) {
            totalPrice += orderMap[key].item.price * orderMap[key].quantity;
        }
        return totalPrice;
    }

    function getItemsFromOrderMap(orderMap) {
        var items = [];
        var orderItem = {};
        for (var key in orderMap) {
            item = orderMap[key].item;
            orderItem = {
                id : item.id,
                name : item.name,                
                price : item.price,
                quantity : orderMap[key].quantity
            };
            items.push(orderItem);            
        }
        return items;
    }

    $scope.loadOrders = function() {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        $scope.orderMap = Services.getOrderData(); 
        $scope.totalItems = Services.getOrderQuantity();       
        $scope.totalPrice = getTotalPrice($scope.orderMap);
        $scope.table = Services.getTable();
        var obj = {};
        obj.tableId = $scope.table.id;
        Services.getOrders($rootScope.hosturl, obj, headers).success(function(data) {
            if (data.result) {
                $scope.orders = data.orderDetails;
                if (data.orderDetails.length == 0) {
                    $scope.cancelTable = true;
                } else {
                    $scope.cancelTable = false;
                }
                $rootScope.loading = false;
                if (data.status == 'VACANT') {
                    $location.path('/dashboard');
                }
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    }

    var cancelTableModalObj = {};
    $scope.cancelThisTable = function(ev) {
        cancelTableModalObj = {};
        cancelTableModalObj.tableId = $scope.table.id;
        var userAction = $scope.showCancelTableModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.cancelTable($rootScope.hosturl, cancelTableModalObj, headers).success(function(data) {
                    if (data.result) {
                        $location.path('/dashboard');
                        growl.addSuccessMessage("Table closed successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    }

    var printKotModalObj = {};
    $scope.printKot = function(ev, order) {
        printKotModalObj = {};
        printKotModalObj.id = order.id;
        var userAction = $scope.showPrintKotModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                Services.printKot($rootScope.hosturl, printKotModalObj, headers).success(function(data) {
                    if (data.result) {
                        growl.addSuccessMessage("KOT printed successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    }

    var editKotModalObj = {};
    $scope.editKot = function(ev, order) {
        editKotModalObj = {};
        editKotModalObj = angular.copy(order);
        var userAction = $scope.showEditKotModal(ev);
        userAction.then(function(answer) {
            if (answer) {
                $window.scrollTo(0, 0);
                $rootScope.loading = true;
                editKotModalObj.orderId = editKotModalObj.id;
                editKotModalObj.tableId = $scope.table.id;
                Services.addEditOrder($rootScope.hosturl, editKotModalObj, headers).success(function(data) {
                    if (data.result) {
                        $scope.orders = data.orderDetails;
                        if (data.orderDetails.length == 0) {
                            $scope.cancelTable = true;
                        } else {
                            $scope.cancelTable = false;
                        }
                        growl.addSuccessMessage("KOT updated successfully");
                        $rootScope.loading = false;
                    } else {
                        growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                        $rootScope.loading = false;
                    }
                });
            }
        });
    }

    $scope.goToBill = function(ev) {
        $location.path('/goToBill/' + false + '/' + $scope.table.id);
    }

    var orderOb = {};
    $scope.addToBill = function(ev) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        orderOb.tableId = $scope.table.id;
        orderOb.items = getItemsFromOrderMap($scope.orderMap);
        Services.addEditOrder($rootScope.hosturl, orderOb, headers).success(function(data) {
            if (data.result) {
                $scope.orders = data.orderDetails;
                if (data.orderDetails.length == 0) {
                    $scope.cancelTable = true;
                } else {
                    $scope.cancelTable = false;
                }
                $scope.totalItems = 0;
                Services.destroyOrderData();
                $rootScope.resetItemsQuantities = true;
                growl.addSuccessMessage("Successfully added to bill");
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });
    };

    $scope.addQuantity = function(key, value) {
        value.quantity++;
        $scope.totalItems++;
        Services.setOrderQuantity($scope.totalItems);
        $scope.totalPrice = getTotalPrice($scope.orderMap);
    };

    $scope.minusQuantity = function(key, value) {
        value.quantity--;
        $scope.totalItems--;
        Services.setOrderQuantity($scope.totalItems);
        $scope.totalPrice = getTotalPrice($scope.orderMap);
    };

    $scope.addMore = function(loc) {
        Services.setOrderData($scope.orderMap);
        Services.setOrderQuantity($scope.totalItems);
        $location.path(loc);
    };

    $scope.getTotalsOfOrder = function(order) {
        var totalItemsInOrder = 0;
        var totalPriceOfOrder = 0.00;
        order.items.forEach(function(item){
            totalItemsInOrder += item.quantity;
            totalPriceOfOrder += item.quantity*item.price;
        });
        return {
            totalItemsInOrder: totalItemsInOrder,
            totalPriceOfOrder: totalPriceOfOrder
        };
    };

    $scope.showCancelTableModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = cancelTableModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.cancelTableModalObj = dataToPass;
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
            templateUrl: 'app/views/dashboard/cancelTable.html',
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

    $scope.showPrintKotModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = printKotModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.printKotModalObj = dataToPass;
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
            templateUrl: 'app/views/dashboard/printKot.html',
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

    $scope.showEditKotModal = function(ev) {
        var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
        var dataObject = editKotModalObj;
        return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) {
                $scope.editKotModalObj = dataToPass;
                $scope.hide = function() {
                    $mdDialog.hide();
                };
                $scope.cancel = function() {
                    $mdDialog.cancel();
                };
                $scope.answer = function(answer) {
                    $mdDialog.hide(answer);
                };
                $scope.getTotalsOfOrder = function(order) {
                    var totalItemsInOrder = 0;
                    var totalPriceOfOrder = 0.00;
                    order.items.forEach(function(item){
                        totalItemsInOrder += item.quantity;
                        totalPriceOfOrder += item.quantity*item.price;
                    });
                    return {
                        totalItemsInOrder: totalItemsInOrder,
                        totalPriceOfOrder: totalPriceOfOrder
                    };
                };
                $scope.addQuantity = function(item) {
                    item.quantity++;
                };

                $scope.minusQuantity = function(item) {
                    if (item.quantity == 0) {
                        return;
                    }
                    item.quantity--;
                };
            }],
            templateUrl: 'app/views/dashboard/editKot.html',
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
