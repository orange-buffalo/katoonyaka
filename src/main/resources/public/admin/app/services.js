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
            else if (path.indexOf("uploadcare") >= 0) {
                $rootScope.activeConsole = "Uploadcare Storage Management";
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

    service.navigateToUploadcare = function() {
        $location.path("/uploadcare");
    };

    service.navigateToEditCover = function(coverId) {
        $location.path("/covers/" + coverId + "/edit");
    };

    service.navigateToEditHandiwork = function(handiworkId) {
        $location.path("/handiworks/" + handiworkId + "/edit");
    };

    service.navigateToCreateCover = function() {
        $location.path("/covers/create");
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

var ServicesFacade = function(Cover, $mdBottomSheet, $location, Handiwork,
                              NavigationService, $mdToast, $mdDialog, $window,
                              blockUI, $routeParams, NotificationService, UploadcareService,
                              ConfigService) {

    function _createSuccessNotification(message) {
        return function() {
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
        uploadcareService: UploadcareService,
        $window: $window,
        configService: ConfigService
    }
};

var UploadcareWidget = function (blockUI, NotificationService) {
    return {
        restrict: 'A',
        scope: {
            onFileUploaded: '&',
            onAllUploadsCompleted: '&',
            multipleFiles: '=',
            publicKey: '='
        },
        link: function (scope, element) {
            function _openUploadCareDialog() {
                var dialog = uploadcare.openDialog(null, {
                    imagesOnly: true,
                    multiple: scope.multipleFiles,
                    tabs: 'file',
                    publicKey: scope.publicKey
                });

                dialog.done(function(result) {
                    blockUI.start();
                    blockUI.message("Uploading...");

                    var processedFilesCount = 0;
                    var expectedFilesCount;

                    function _checkIfAllUploaded() {
                        processedFilesCount++;
                        if (processedFilesCount == expectedFilesCount) {
                            scope.onAllUploadsCompleted();
                            blockUI.stop();
                        }
                    }

                    function _listenToFile(file) {
                        file.done(function(fileInfo) {
                            scope.$apply(function () {
                                scope.onFileUploaded({fileInfo: fileInfo});
                                _checkIfAllUploaded();
                            });
                        });

                        file.fail(function(error) {
                            scope.$apply(function () {
                                NotificationService.error(error);
                                _checkIfAllUploaded();
                            });
                        })
                    }

                    if (scope.multipleFiles) {
                        var selectedFiles = result.files();
                        expectedFilesCount = selectedFiles.length;
                        angular.forEach(selectedFiles, function(file) {
                            _listenToFile(file);
                        });
                    }
                    else {
                        expectedFilesCount = 1;
                        _listenToFile(result);
                    }
                });
            }

            element.on("click", _openUploadCareDialog);
        }
    };
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

function createUploadcarePhoto(fileInfo) {
    return {
        externalId: fileInfo.uuid,
        width: fileInfo.originalImageInfo.width,
        height: fileInfo.originalImageInfo.height
    };
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

var UploadcareService = function(Restangular) {
    return Restangular.all("uploadcare");
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