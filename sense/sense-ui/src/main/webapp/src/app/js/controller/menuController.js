var categoryItemsMap = {};
var orderItemsMap = {};
function addItemToOrder(card) {
    var id = $(card).attr('id');
    var categoryId = $(card).attr('categoryId');
    var quantity = $('#item-quantity-'+id).text();
    quantity = quantity*1 + 1;
    $('#item-quantity-'+id).text(quantity);
    var categoryItems = categoryItemsMap[categoryId].items;
    var item = {};
    for (var i=0; i<categoryItems.length; i++) {
        if (categoryItems[i].id == card.id) {
            item = categoryItems[i];
            categoryItemsMap[categoryId].items[i].quantity = quantity;
            if (item.id in orderItemsMap) {
                orderItemsMap[item.id].quantity++;
            } else {
                orderItemsMap[item.id] = {
                    item: item,
                    quantity: 1
                }
            }
            break;
        }
    }
    var scope = angular.element('#item-quantity-'+id).scope();
    scope.$apply(function() {
        scope.updateTotalItems(item);
    });
}

app.controller('MenuController', ['$scope', '$location', '$rootScope', 'Services', '$mdToast', '$timeout', '$q', '$window', '$routeParams', 'growl', function($scope, $location, $rootScope, Services, $mdToast, $timeout, $q, $window, $routeParams, growl) {
    $rootScope.pageTitle = 'Order';
    $scope.location = $location.path();
    $scope.$parent.searchText = '';

    $scope.$watch('$parent.searchText', function(text){
        if (text.length > 1) {
            $scope.selectedIndex = -1;
            searchFilterItems(text.toLowerCase());
        }        
    });

    var token = $rootScope.Authtoken;
    var headers = {
        'Accept': 'application/json',
        'Authorization': token
    };
    var isToggle = false;

    $scope.noMenu = false;
    $scope.menuType = 'fnb';
    $scope.totalItems = 0;
    

    $scope.toggleMenu = function() {
        isToggle = true;
        Services.setOrderQuantity($scope.totalItems);
        Services.setOrderData(orderItemsMap);
        if ($scope.menuType == 'fnb') {            
            $scope.fetchMenu('bar');
            $scope.menuType = 'bar';
        } else {
            $scope.fetchMenu('fnb');
            $scope.menuType = 'fnb';
        }
    }
    
    $scope.redirect = function(loc) {
        Services.setOrderQuantity($scope.totalItems);
        Services.setOrderData(orderItemsMap);
        $location.path(loc);
    }

    $scope.getItemsFromMap = function(category) {
        filterAndDisplayItems(category);        
    }

    $scope.updateTotalItems = function(item) {
        $scope.totalItems++;
        growl.addInfoMessage(item.name + ' added to order', {ttl: 500});
    }

    $scope.fetchMenu = function(type) {
        menuType = {"type" : type};
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        $scope.table = Services.getTable();
        $scope.totalItems = Services.getOrderQuantity();
        orderItemsMap = Services.getOrderData();
        getAndCacheMenu(menuType);
        isToggle = false;
        $rootScope.resetItemsQuantities = false;
    }

    function searchFilterItems(searchText) {
        var cards = '';
        $.each(categoryItemsMap, function(key, value) {
            $.each(value.items, function(index, item) {
                if (item.name.toLowerCase().indexOf(searchText) !== -1 && item.type.toLowerCase() == $scope.menuType) {
                    if (item.quantity == undefined) {
                        item.quantity = 0;
                    }
                    cards += getCard(item, key);
                }
            })          
        });
        $('#subCategoryDiv').html('');
        $('#itemsDiv').html(cards);
    }
    
    function filterAndDisplayItems(category) {
        var itemCards = '';
        var subCategories = {};
        if (Object.keys(categoryItemsMap).length == 0 || category == undefined) {
            $scope.noMenu = true;
            itemCards = 'No Menu Found. Please go to Manage Menu Screen to add menu.';
        } else {
            var items = categoryItemsMap[category.id].items;
            subCategories = categoryItemsMap[category.id].subCategories;
            for (var item of items) {
                if (item.quantity == undefined) {
                    item.quantity = 0;
                }
                var itemPresent = false;            
                if (item.subCategoryId != null) {
                    $.each(subCategories[item.subCategoryId].items, function(index, subCategoryItem) {
                        if (subCategoryItem.id == item.id) {
                            itemPresent = true;
                            return false;
                        }
                    });
                    if (!itemPresent) {
                        subCategories[item.subCategoryId].items.push(item);
                    }          
                } else {
                    itemCards += getCard(item, category.id);
                }
            }
        }
        
        subCategoriesDivs = getSubCategoriesDivs(subCategories);
        $('#subCategoryDiv').html(subCategoriesDivs);
        $('#subCategoryDiv').show();
        $('#itemsDiv').html(itemCards);
    }

    function getSubCategoriesDivs(subCategories) {
        var subCategoriesDivs = '';
        var itemCards = '';
        $.each(subCategories, function(key, value) {
            subCategoriesDivs += '<div flex layout="column" layout-padding style="background: azure;">'+
                                    '<h4 flex="80" class="panel-title">'+
                                      value.subCategory.name+                                                          
                                    '</h4>'+
                                    '<div layout="row" layout-wrap style="padding: 0px;">';
            itemCards = '';
            $.each(value.items, function(index, item) {
                itemCards += getCard(item, value.subCategory.categoryId);
            })        
            subCategoriesDivs += itemCards + '</div></div>';
        });

        return subCategoriesDivs;
    }

    function getCard(item, categoryId) {
        return '<md-card md-whiteframe="8" id="'+item.id+'" categoryId="'+categoryId+'" flex="10" layout="column" class="item-card cursorPointer" onclick="addItemToOrder(this);">'+
                    '<md-card-title flex="85" class="card-title">'+
                        item.name+                                
                    '</md-card-title>'+
                    '<md-card-actions flex="15" layout="row">'+
                        '<div flex>'+
                            '<i id="item-price-'+item.id+'" class="fa fa-inr" aria-hidden="true">'+item.price+'</i>'+
                        '</div>'+
                        '<div flex>'+
                            '<p id="item-quantity-'+item.id+'" align="right">'+item.quantity+'</p>'+
                        '</div>'+
                    '</md-card-actions>'+
                '</md-card>';
    }    

    function getAndCacheMenu(menuType) {
        var type = menuType.type; 
        if (Services.getCachedCategories(type) == null || Services.getCachedSubCategories(type) == null || Services.getCachedItems(type) == null) {
            Services.getCategories($rootScope.hosturl, menuType,  headers).success(function(categoryData) {
                if (categoryData.result) {
                    $scope.categories = categoryData.categories;
                    setCategoriesToMap($scope.categories);
                    Services.setCachedCategories(type, $scope.categories);
                    Services.getSubCategories($rootScope.hosturl, menuType,  headers).success(function(subCategoryData) {
                        if (subCategoryData.result) {
                            setSubCategoriesToMap(subCategoryData.subCategories);
                            Services.setCachedSubCategories(type, subCategoryData.subCategories);
                            Services.getItems($rootScope.hosturl, menuType,  headers).success(function(itemData) {
                                if (itemData.result) {
                                    setItemsToMap(itemData.items);
                                    Services.setCachedItems(type, itemData.items);
                                    filterAndDisplayItems($scope.categories[0]);
                                    $rootScope.loading = false;
                                } else {
                                    growl.addErrorMessage(itemData.statusCodes.statusCode[0].description);
                                    $rootScope.loading = false;
                                }
                            });
                        } else {
                            growl.addErrorMessage(subCategoryData.statusCodes.statusCode[0].description);
                            $rootScope.loading = false;
                        }
                    });                    
                } else {
                    growl.addErrorMessage(categoryData.statusCodes.statusCode[0].description);
                    $rootScope.loading = false;
                }
            });
        } else {
            $scope.categories = Services.getCachedCategories(type);
            //only reset quantities when call is from a page, not while toggling menu between fnb/bar
            if ($rootScope.resetItemsQuantities == true && isToggle == false) {
                resetItemsQuantityInMap(Services.getCachedItems(type));
            } else if (Object.keys(orderItemsMap).length > 0) {
                setItemsQuantityInMap(orderItemsMap);
            }          
            filterAndDisplayItems($scope.categories[0]);
            $rootScope.loading = false;
        } 
    }

    function setCategoriesToMap(categories) {
        for (var category of categories) {
            categoryItemsMap[category.id] = {
                items : [],
                subCategories : {}
            };
        }
    }

    function setSubCategoriesToMap(subCategories) {
        for (var subCategory of subCategories) {
            categoryItemsMap[subCategory.categoryId].subCategories[subCategory.id] = {
                subCategory : subCategory,
                items : []
            };
        }
    }

    function setItemsToMap(items) {
        for (var item of items) {
            categoryItemsMap[item.categoryId].items.push(item);
        }
    }

    function resetItemsQuantityInMap(items) {
        $.each(categoryItemsMap, function(key, value) {
            $.each(value.items, function(index, item) {
                item.quantity = 0;
            })          
        });
    }

    function setItemsQuantityInMap(orderMap) {
        $.each(orderMap, function(key, value) {
            var categoryItems = categoryItemsMap[value.item.categoryId].items;
            for (var i=0; i<categoryItems.length; i++) {
                if (categoryItems[i].id == value.item.id) {
                    item = value.item;
                    categoryItemsMap[value.item.categoryId].items[i].quantity = value.quantity;                    
                    break;
                }
            }
        });
    }   

}]);
