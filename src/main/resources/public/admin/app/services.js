var NavigationService = function($rootScope, $location) {
    var service = {};

    service.init = function() {
        $rootScope.$on("$routeChangeSuccess", function() {
            var path = $location.path();
            if (path.indexOf("handiworks") >= 0) {
                $rootScope.activeConsole = "Handiworks Management";
            }
            else if (path.indexOf("covers") >= 0) {
                $rootScope.activeConsole = "Covers Management";
            }
            else {
                $rootScope.activeConsole = "";
            }
        });
    };

    service.navigateToCovers = function() {
        $location.path("/covers");
    };

    service.navigateToHandiworks = function() {
        $location.path("/handiworks");
    };

    service.navigateToEditHandiwork = function(handiworkId) {
        $location.path("/handiworks/" + handiworkId + "/edit");
    };

    service.navigateToCreateHandiwork = function() {
        $location.path("/handiworks/create");
    };

    service.navigateToHandiwork = function(handiworkId) {
        $location.path("/handiworks/" + handiworkId + "/view");
    };

    return service;
};

var HttpErrorHandler = function($q, blockUI, $rootScope) {
    function showErrorMessage(message) {
        blockUI.start();
        blockUI.message(message);
        setTimeout(function() {
            $rootScope.$apply(function()  {
                blockUI.stop();
            });
        }, 3000);
    }

    return {
        'requestError': function(rejection) {
            showErrorMessage("Failed to send request to the server :(");
            return $q.reject(rejection);
        },

        'responseError': function(rejection) {
            showErrorMessage("Error happened on the server side :(");
            return $q.reject(rejection);
        }
    };
};

var NotificationService = function($mdToast) {
    function _showNotification(message, iconAddition) {
        $mdToast.show({
            template: '<md-toast class="notification"><span><md-icon ' + iconAddition + '></md-icon>' + message + '</span></md-toast>',
            position: 'top right'
        });
    }

    return {
        info: function(message) {
            _showNotification(message, 'md-svg-icon="info" style="color: #4097ba;"');
        },

        error: function(message) {
            _showNotification(message, 'md-svg-icon="error" style="color: #e01307;"');
        },

        success: function(message) {
            _showNotification(message, 'md-svg-icon="success" style="color: #22a146;"');
        },

        warn: function(message) {
            _showNotification(message, 'md-svg-icon="warning" style="color: #f96913;"');
        }
    }
};

var ServicesFacade = function (Cover, $mdBottomSheet, $location, Handiwork, UploadService,
                               NavigationService, $mdToast, $mdDialog, $window,
                               blockUI, $routeParams, NotificationService, ConfigService) {

  function _createSuccessNotification(message) {
    return function () {
      NotificationService.success(message);
    }
  }

  return {
    blockUi: blockUI,
    $mdBottomSheet: $mdBottomSheet,
    coverService: Cover,
    $location: $location,
    navigationService: NavigationService,
    $mdToast: $mdToast,
    $mdDialog: $mdDialog,
    $routeParams: $routeParams,
    notificationService: NotificationService,
    handiworkService: Handiwork,
    createSuccessNotification: _createSuccessNotification,
    $window: $window,
    configService: ConfigService,
    uploadService: UploadService
  }
};

var UploadService = function (Upload, NotificationService, blockUI) {

  function _uploadPhoto(files, currentFile, url, callback) {
      if (currentFile == files.length) {
        NotificationService.success("All photos have been uploaded!");
        if (callback) {
          callback();
        }
      }
      else {
        var file = files[currentFile];
        file.upload = Upload.upload({
          url: url,
          data: {file: file}
        });

        file.upload.then(function () {
          _uploadPhoto(files, currentFile + 1, url, callback);
        }, null, function (evt) {
          var progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
          if (progress < 100) {
            if (files.length == 1) {
              blockUI.message(file.name + " is uploading.. " + progress + "% done..");
            }
            else {
              blockUI.message("Uploading " + (currentFile + 1) + " of " + files.length +
                " (" + file.name + " " + progress + "% done..)");
            }
          }
          else {
            if (files.length == 1) {
              blockUI.message(file.name + " uploaded, processing on server..");
            }
            else {
              blockUI.message("Uploading " + (currentFile + 1) + " of " + files.length +
                " (" + file.name + " is transferred, processing on server..)");
            }
          }
        });
      }
  }

  return {
    uploadPhotos: function (files, url, callback) {
      if (files && files.length) {
        _uploadPhoto(files, 0, url, callback);
      }
    }
  }
};

var HandiworkService = function(Restangular, blockUI, $q) {
    var handiworksRestangular = Restangular.all("handiworks");

    handiworksRestangular.moveHandiwork = function(handiwork, toIndex) {
        return $q(function(resolve) {
            blockUI.start();
            handiworksRestangular.getList()
                .then(function(handiworks) {
                    dndMove(toIndex, handiwork, handiworks);

                    handiworksRestangular.customPUT(handiworks)
                        .then(function() {
                            blockUI.stop();
                            if (resolve) {
                                resolve();
                            }
                        });
                });
        });
    };

    return handiworksRestangular;
};

function indexOf(item, items) {
   return _.findIndex(items, function(currentItem) {
       return (item.id == currentItem.id);
   });
}

function dndMove(dndIndex, item, items) {
    var currentIndex = indexOf(item, items);

    items.splice(dndIndex, 0, items.splice(currentIndex, 1)[0]);
}

var CoverService = function(Restangular, blockUI, $q) {
    var coversRestangular = Restangular.all("covers");

    coversRestangular.moveCover = function(cover, toIndex) {
        return $q(function(resolve) {
            blockUI.start();
            coversRestangular.getList()
                .then(function(covers) {
                    dndMove(toIndex, cover, covers);

                    coversRestangular.customPUT(covers)
                        .then(function() {
                            blockUI.stop();
                            if (resolve) {
                                resolve();
                            }
                        });
                });
        });
    };

    return coversRestangular;
};

var ConfigService = function($http, $q, blockUI) {
    var service = {};

    service.getConfigValue = function(configProperty) {
        return $q(function(resolve, reject) {
            blockUI.start();

            $http({
                method: 'GET',
                url: '/admin/api/config/' + configProperty
            }).then(function successCallback(response) {
                blockUI.stop();
                if (resolve) {
                    resolve(response.data);
                }
            }, function errorCallback() {
                blockUI.stop();
                if (reject) {
                    reject();
                }
            });
        });
    };

    return service;
};