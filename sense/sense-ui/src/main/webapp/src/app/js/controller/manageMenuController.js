/********  OUTSIDE SCOPE  ***********/
function addItemToSubCategory(event, button) {
  var subCategory = {
    id: $(button).attr('id'),
    name: $(button).attr('subcategory-name')
  };
  var scope = angular.element('.add-category-button').scope();
  scope.$apply(function() {
      scope.addItemToSubCategory(event, subCategory);
  });
}

function deleteItem(event, deleteButton) {
  var id = $(deleteButton).attr('item-id');
  var name = $(deleteButton).attr('item-name');
  var item = {
    id: id,
    name: name
  };
  var scope = angular.element('.add-category-button').scope();
  scope.$apply(function() {
      scope.deleteItem(item, event);
  });
}

function updateItem(event, updateButton) {
  var updateItem = {};
  var id = $(updateButton).attr('item-id');
  var angularServices = angular.element('body').injector().get('Services');
  var items = angularServices.getCachedItems($(updateButton).attr('type').toLowerCase());
  for (var item of items) {
    if (item.id == id) {
      updateItem = item;
    }
  }
  if (updateItem != null) {
    var scope = angular.element('.add-category-button').scope();
    scope.$apply(function() {
        scope.updateItem(event, updateItem);
    });
  }
}

function deleteSubCategory(event, deleteButton) {
  var id = $(deleteButton).attr('subcategory-id');
  var name = $(deleteButton).attr('subcategory-name');
  var subCategory = {
    id: id,
    name: name
  };
  var scope = angular.element('.add-category-button').scope();
  scope.$apply(function() {
      scope.deleteSubCategory(subCategory, event);
  });
}

function updateSubCategory(event, editButton) {
  var id = $(editButton).attr('subcategory-id');
  var name = $(editButton).attr('subcategory-name');
  var subCategory = {
    id: id,
    name: name
  };
  var scope = angular.element('.add-category-button').scope();
  scope.$apply(function() {
      scope.updateSubCategory(subCategory, event);
  });
}

/********  CONTROLLER SCOPE  ***********/
app.controller('ManageMenuController', ['$scope', '$location', '$rootScope', 'Services', '$mdToast', '$timeout', '$q', '$window', '$routeParams', 'growl', '$mdMedia', '$mdDialog', function($scope, $location, $rootScope, Services, $mdToast, $timeout, $q, $window, $routeParams, growl, $mdMedia, $mdDialog) {
  $rootScope.pageTitle = 'Menu';
  var token = $rootScope.Authtoken;
  var headers = {
      'Accept': 'application/json',
      'Authorization': token
  };

  $scope.noMenu = false;
  $scope.menuType = 'fnb';
  $scope.showAddSubCategoryAndItemButtons = true;
  
  $scope.toggleMenu = function() {
      if ($scope.menuType == 'fnb') {            
          $scope.fetchMenu('bar');
          $scope.menuType = 'bar';
      } else {
          $scope.fetchMenu('fnb');
          $scope.menuType = 'fnb';
      }
  }

  $scope.getItemsFromMap = function(category, index) {
      displayItems(category, index);        
  }

  $scope.fetchMenu = function(type) {
      menuType = {"type" : type};
      $window.scrollTo(0, 0);
      $rootScope.loading = true;
      getAndCacheMenu(menuType, 0);
  }
  
  function displayItems(category, index) {
      if (category != undefined) {
        $scope.currentCategory = {
          id: category.id,
          name: category.name
        };
      }
      var itemCards = '';
      var subCategories = {};
      if (Object.keys(categoryItemsMap).length == 0 || category == undefined) {
          $scope.noMenu = true;
          itemCards = 'No Menu Found. Please click on the Add Category button to create menu';
          $scope.showAddSubCategoryAndItemButtons = false;
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
      $('#subCategoryDivManageMenu').html(subCategoriesDivs);
      $('#subCategoryDivManageMenu').show();
      $('#itemsDivManageMenu').html(itemCards);
      $scope.selectedIndex = index;
  }

  function getSubCategoriesDivs(subCategories) {
      var subCategoriesDivs = '';
      var itemCards = '';
      $.each(subCategories, function(key, value) {
          subCategoriesDivs += '<div flex layout="column" layout-padding style="background: azure;">'+
                                  '<h4 flex="80" layout="row" class="panel-title">'+
                                    '<p style="margin-right:20px;">'+value.subCategory.name+'</p>'+
                                    '<p style="margin-right:20px;" subcategory-name="'+value.subCategory.name+'" subcategory-id="'+value.subCategory.id+'" onclick="updateSubCategory(event, this)">'+
                                      '<i class="fa fa-pencil fa-hover-hidden custom-icon-button"></i>'+
                                      '<i class="fa fa-pencil fa-lg fa-hover-show custom-icon-button"></i>'+
                                    '</p>'+
                                    '<p subcategory-name="'+value.subCategory.name+'" subcategory-id="'+value.subCategory.id+'" onclick="deleteSubCategory(event, this)">'+
                                      '<i class="fa fa-times fa-hover-hidden custom-icon-button"></i>'+
                                      '<i class="fa fa-times fa-lg fa-hover-show custom-icon-button"></i>'+
                                    '</p>'+                                                       
                                  '</h4>'+
                                  '<div layout="row" layout-wrap style="padding: 0px;">';
          itemCards = '';
          $.each(value.items, function(index, item) {
              itemCards += getCard(item, value.subCategory.categoryId);              
          });
          itemCards += getAddItemButton(value.subCategory);
          subCategoriesDivs += itemCards + '</div></div>';
      });

      return subCategoriesDivs;
  }

  function getCard(item, categoryId) {
    return '<md-card md-whiteframe="8" id="'+item.id+'" categoryId="'+categoryId+'" flex="10" layout="column" class="item-card cursorPointer">'+
                '<md-card-title flex="85" class="card-title" item-id="'+item.id+'" type="'+item.type+'" onclick="updateItem(event, this)">'+
                    item.name+                                
                '</md-card-title>'+
                '<md-card-actions flex="15" layout="row">'+
                    '<div flex>'+
                        '<i id="item-price-'+item.id+'" class="fa fa-inr" aria-hidden="true">'+item.price+'</i>'+
                    '</div>'+
                    '<div flex>'+
                        '<p align="right" item-name="'+item.name+'" item-id="'+item.id+'" onclick="deleteItem(event, this)">'+
                          '<i class="fa fa-trash-o fa-hover-hidden custom-icon-button"></i>'+
                          '<i class="fa fa-trash-o fa-lg fa-hover-show custom-icon-button"></i>'+
                        '</p>'+
                    '</div>'+
                '</md-card-actions>'+
            '</md-card>';
  }

  function getAddItemButton(subCategory) {
    return '<div flex="20" layout="column" layout-align="center start" layout-padding>'+
              '<button id="'+subCategory.id+'" subcategory-name="'+subCategory.name+'" class="item-add-button" aria-label="Add" onclick="addItemToSubCategory(event, this)">'+
                  '<i class="fa fa-plus fa-lg" aria-hidden="true"></i>'+
              '</button>'+
            '</div>';
  }  

  function getAndCacheMenu(menuType, index) {
      var type = menuType.type; 
      $scope.showAddSubCategoryAndItemButtons = true;
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
                                  displayItems($scope.categories[index], index);
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
          displayItems($scope.categories[index], index);
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

  var deleteText = '';
  $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

  /***************************************   ADD/UPDATE methods START   ************************************************/ 
  var addEditCategoryModalObj = {};
  $scope.addCategory = function(ev) {
    addEditCategoryModalObj = {};
    addEditCategoryModalObj.type = $scope.menuType;
    addEditCategoryModalObj.heading = 'Add new Category';
    var userAction = $scope.showCategoryModal(ev, addEditCategoryModalObj);
    userAction.then(function(answer){
      if (answer) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.addEditCategory($rootScope.hosturl, addEditCategoryModalObj, headers).success(function(data) {
            if (data.result) {
              updateView('addCategory');           
              growl.addSuccessMessage(data.category.name + " added successfully");
              $rootScope.loading = false;
            } else {
              growl.addErrorMessage(data.statusCodes.statusCode[0].description);
              $rootScope.loading = false;
            }
        });  
      }        
    });
  };

  $scope.updateCategory = function(ev, category) {
    addEditCategoryModalObj = {};
    addEditCategoryModalObj.type = $scope.menuType;
    addEditCategoryModalObj.id = category.id;
    addEditCategoryModalObj.name = category.name;
    addEditCategoryModalObj.heading = 'Update Category';
    var userAction = $scope.showCategoryModal(ev, addEditCategoryModalObj);
    userAction.then(function(answer){
      if (answer) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.addEditCategory($rootScope.hosturl, addEditCategoryModalObj, headers).success(function(data) {
            if (data.result) {
              updateView('updateCategory');
              growl.addSuccessMessage(data.category.name + " updated successfully");
              $rootScope.loading = false;
            } else {
              growl.addErrorMessage(data.statusCodes.statusCode[0].description);
              $rootScope.loading = false;
            }
        });  
      }        
    });
  };

  var addEditSubCategoryModalObj = {};
  $scope.addSubCategory = function(ev) {
    addEditSubCategoryModalObj = {};
    addEditSubCategoryModalObj.type = $scope.menuType;
    addEditSubCategoryModalObj.categoryId = $scope.currentCategory.id;    
    addEditSubCategoryModalObj.heading = 'Add new Sub Category to ' + $scope.currentCategory.name;
    var userAction = $scope.showSubCategoryModal(ev, addEditSubCategoryModalObj);
    userAction.then(function(answer){
      if (answer) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.addEditSubCategory($rootScope.hosturl, addEditSubCategoryModalObj, headers).success(function(data) {
            if (data.result) {
              updateView();
              growl.addSuccessMessage(data.subCategory.name + " added successfully");
              $rootScope.loading = false;
            } else {
              growl.addErrorMessage(data.statusCodes.statusCode[0].description);
              $rootScope.loading = false;
            }
        });  
      }        
    });
  };

  $scope.updateSubCategory = function(subCategory, ev) {
    addEditSubCategoryModalObj = {};
    addEditSubCategoryModalObj.categoryId = $scope.currentCategory.id;
    addEditSubCategoryModalObj.categoryName = $scope.currentCategory.name;
    addEditSubCategoryModalObj.id = subCategory.id;
    addEditSubCategoryModalObj.name = subCategory.name;
    addEditSubCategoryModalObj.type = $scope.menuType;
    addEditSubCategoryModalObj.heading = 'Update Sub Category';
    var userAction = $scope.showSubCategoryModal(ev, addEditSubCategoryModalObj);
    userAction.then(function(answer){
      if (answer) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.addEditSubCategory($rootScope.hosturl, addEditSubCategoryModalObj, headers).success(function(data) {
            if (data.result) {
              updateView();
              growl.addSuccessMessage(data.subCategory.name + " updated successfully");
              $rootScope.loading = false;
            } else {
              growl.addErrorMessage(data.statusCodes.statusCode[0].description);
              $rootScope.loading = false;
            }
        });  
      }        
    });
  };

  var addEditItemModalObj = {};
  $scope.addItemToCategory = function(ev) {
    addEditItemModalObj = {};
    addEditItemModalObj.categoryId = $scope.currentCategory.id;
    addEditItemModalObj.type = $scope.menuType;
    addEditItemModalObj.heading = 'Add item to ' + $scope.currentCategory.name;
    setDefaultValues($scope.menuType, addEditItemModalObj)
    addEditItem(ev, addEditItemModalObj, 'add');
  }

  $scope.addItemToSubCategory = function(ev, subCategory) {
    addEditItemModalObj = {};
    addEditItemModalObj.categoryId = $scope.currentCategory.id;
    addEditItemModalObj.subCategoryId = subCategory.id;
    addEditItemModalObj.type = $scope.menuType;
    addEditItemModalObj.heading = 'Add item to ' + subCategory.name;
    setDefaultValues($scope.menuType, addEditItemModalObj)
    addEditItem(ev, addEditItemModalObj, 'add');
  }

  $scope.updateItem = function(ev, item) {
    addEditItemModalObj = item;
    addEditItemModalObj.heading = 'Update item';
    addEditItem(ev, addEditItemModalObj, 'update');
  }


  function addEditItem(ev, addEditItemModalObj, changeFlag) {
    var userAction = $scope.showItemModal(ev, addEditItemModalObj);
    userAction.then(function(answer){
      if (answer) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.addEditItem($rootScope.hosturl, addEditItemModalObj, headers).success(function(data) {
            if (data.result) {
              updateView();
              growl.addSuccessMessage(data.item.name + " " + (changeFlag=='add'?"added":"updated") + " successfully");
              $rootScope.loading = false;
            } else {
              growl.addErrorMessage(data.statusCodes.statusCode[0].description);
              $rootScope.loading = false;
            }
        });  
      }        
    });
  }

  function updateView(changeFlag) {
    menuType = {"type" : $scope.menuType };
    if (changeFlag == 'addCategory') {
      selectIndex = Services.getCachedCategories(menuType.type).length;
    } else if (changeFlag == 'deleteCategory') {
      if ($scope.selectedIndex == 0) {
        selectIndex = $scope.selectedIndex;
      } else {
        selectIndex = $scope.selectedIndex-1;
      }      
    } else {
      selectIndex = $scope.selectedIndex;
    }
    Services.setCachedCategories(menuType.type, null);
    getAndCacheMenu(menuType, selectIndex);
  }

  function setDefaultValues(type, item) {
    if (type.toLowerCase() == 'fnb') {
      return setFnbDefaultValues(item);
    } else {
      return setBarDefaultValues(item);
    }
  }

  function setFnbDefaultValues(item) {
    item.spicyLevel = 2;
    item.isVeg = true;
    item.fnbItemType = 'VEG';
    item.servesFor = 2;
    item.preparationTime = 5;
    item.currency = 'INR';
    item.available = false;
    return item;
  }

  function setBarDefaultValues(item) {
    item.currency = 'INR';
    item.available = false;
    return item;
  }

  /***************************************   ADD/UPDATE methods END   ************************************************/ 

  /***************************************   DELETE methods START   ************************************************/ 
  var deleteModalObj = {};
  $scope.deleteCategory = function(ev, category) {
    deleteModalObj = {};
    deleteModalObj.deleteText = "Are you sure you want to delete " + category.name + "?  All items under " + category.name + " will be deleted.";
    deleteModalObj.id = category.id;
    var userAction = $scope.$parent.showDeleteModal(ev, deleteModalObj, $scope.customFullscreen);
    userAction.then(function(answer){
      if (answer) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.deleteCategory($rootScope.hosturl, deleteModalObj, headers).success(function(data) {
            if (data.result) {
                updateView('deleteCategory');
                growl.addSuccessMessage("Category deleted successfully");
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });  
      }        
    });
  };

  $scope.deleteSubCategory = function(subCategory, ev) {
    deleteModalObj = {};
    deleteModalObj.deleteText = "Are you sure you want to delete " + subCategory.name + "?  All items under " + subCategory.name + " will be deleted.";
    deleteModalObj.id = subCategory.id;
    var userAction = $scope.$parent.showDeleteModal(ev, deleteModalObj, $scope.customFullscreen);
    userAction.then(function(answer){
      if (answer) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.deleteSubCategory($rootScope.hosturl, deleteModalObj, headers).success(function(data) {
            if (data.result) {
                updateView();
                growl.addSuccessMessage("Sub Category deleted successfully");
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });  
      }        
    });
  };

  $scope.deleteItem = function(item, ev) {
    deleteModalObj = {};
    deleteModalObj.deleteText = "Are you sure you want to delete " + item.name + "?";
    deleteModalObj.id = item.id;
    var userAction = $scope.$parent.showDeleteModal(ev, deleteModalObj, $scope.customFullscreen);
    userAction.then(function(answer){
      if (answer) {
        $window.scrollTo(0, 0);
        $rootScope.loading = true;
        Services.deleteItem($rootScope.hosturl, deleteModalObj, headers).success(function(data) {
            if (data.result) {
                updateView();
                growl.addSuccessMessage("Item deleted successfully");
                $rootScope.loading = false;
            } else {
                growl.addErrorMessage(data.statusCodes.statusCode[0].description);
                $rootScope.loading = false;
            }
        });  
      }        
    });
  };

  /***************************************   DELETE methods START   ************************************************/ 

  /***************************************   MODALS START   ************************************************/ 

  $scope.showCategoryModal = function(ev, addEditCategoryModalObject) {
    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
    var dataObject = addEditCategoryModalObject;
    return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) { 
              $scope.addEditCategoryModalObj = dataToPass;
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
            templateUrl: 'app/views/manage/menu/addCategory.html',
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

  $scope.showSubCategoryModal = function(ev, addEditSubCategoryModalObject) {
    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
    var dataObject = addEditSubCategoryModalObject;
    return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) { 
              $scope.addEditSubCategoryModalObj = dataToPass;
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
            templateUrl: 'app/views/manage/menu/addSubCategory.html',
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

  $scope.showItemModal = function(ev, addEditItemModalObject) {
    var template = '';
    if (addEditItemModalObject.type.toLowerCase() == 'fnb') {
      template = 'app/views/manage/menu/addFnbItem.html';
    } else {
      template = 'app/views/manage/menu/addBarItem.html';
    }    
    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
    var dataObject = addEditItemModalObject;
    return $mdDialog.show({
            locals: { dataToPass: dataObject },
            controller: ['$scope', 'dataToPass', function($scope, dataToPass) { 
              $scope.addEditItemModalObj = dataToPass;
              $scope.statuses = ['Planned', 'Confirmed', 'Cancelled'];
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
            templateUrl: template,
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