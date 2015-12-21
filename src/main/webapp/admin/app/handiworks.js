var HandiworksController = function($scope, ServicesFacade) {

    function _reloadHandiworks() {
        $scope.handiworks = ServicesFacade.handiworkService.getList().$object;
    }

    _reloadHandiworks();

    $scope.showHandiwork = function(handiwork) {
        ServicesFacade.navigationService.navigateToHandiwork(handiwork.id);
        ServicesFacade.$mdBottomSheet.cancel();
    };

    $scope.startHandiworkEditing = function(handiwork) {
        ServicesFacade.navigationService.navigateToEditHandiwork(handiwork.id);
        ServicesFacade.$mdBottomSheet.cancel();
    };

    $scope.startHandiworkCreation = function() {
        ServicesFacade.navigationService.navigateToCreateHandiwork();
    };

    $scope.moveHandiwork = function(index, item) {
        ServicesFacade.handiworkService.moveHandiwork(item, index)
            .then(function() {
                ServicesFacade.notificationService.success("Saved");
                _reloadHandiworks();
            });
    };

};

var HandiworkController = function($scope, ServicesFacade) {

    ServicesFacade.configService.getConfigValue("uploadcare.publicKey")
        .then(function (publicKey) {
            $scope.uploadcarePublicKey = publicKey;
        });

    var handiworkId = ServicesFacade.$routeParams.handiworkId;
    ServicesFacade.handiworkService.get(handiworkId)
        .then(function(handiwork) {
            $scope.handiwork = handiwork;
        });

    $scope.draftify = function() {
        $scope.handiwork.draft = true;
        $scope.handiwork.put().then(ServicesFacade.createSuccessNotification('Draftified!'));
    };

    $scope.publish = function() {
        if ($scope.handiwork.photos.length == 0) {
            ServicesFacade.notificationService.error("Can't publish without photos");
            return;
        }

        if (!_hasCover()) {
            ServicesFacade.notificationService.error("Can't publish without cover defined");
            return;
        }
        $scope.handiwork.draft = false;
        $scope.handiwork.put().then(ServicesFacade.createSuccessNotification('Published!'));
    };

    $scope.setAsCover = function(photo) {
        $scope.handiwork.cover = photo;
        $scope.handiwork.put().then(ServicesFacade.createSuccessNotification('New cover saved!'));
    };

    $scope.movePhoto = function(index, photo) {
        dndMove(index, photo, $scope.handiwork.photos);
        $scope.handiwork.put().then(ServicesFacade.createSuccessNotification('Changes are saved!'));
    };

    $scope.onPhotoUploaded = function(fileInfo) {
        $scope.handiwork.photos.push(createUploadcarePhoto(fileInfo));
    };

    $scope.onAllPhotosUploaded = function() {
        $scope.handiwork.put().then(function() {
            ServicesFacade.handiworkService.get($scope.handiwork.id)
                .then(function(handiwork) {
                    $scope.handiwork = handiwork;
                    ServicesFacade.notificationService.success('New photos uploaded and saved!')
                });
        });
    };

    function _hasCover() {
        return !_.isUndefined($scope.handiwork.cover) &&
            !_.isNull($scope.handiwork.cover);
    }

    $scope.isHandiworkCover = function(photo) {
        return !_.isUndefined(photo.id) &&
            _hasCover() &&
            (photo.id === $scope.handiwork.cover.id);
    };

    $scope.deletePhoto = function(photo) {
        var handiwork = $scope.handiwork;
        var isCover = $scope.isHandiworkCover(photo);
        var shouldBeDraftify = (handiwork.photos.length == 1);
        var content = isCover
            ? ('This is a cover for ' + handiwork.name + '.')
            : 'This is a regular photo, will just remove it from the photo list.';
        if (shouldBeDraftify) {
            content += ' And also this is the last photo, handiwork will be moved to Draft.'
        }
        else if (isCover) {
            content += ' Another photo will be randomly chosen for the cover.';
        }

        var confirmDialog = ServicesFacade.$mdDialog.confirm()
            .title('Would you like to delete this photo?')
            .content(content)
            .ok('Yes, do what I said!')
            .cancel('Nope');
        ServicesFacade.$mdDialog.show(confirmDialog).then(function() {
            var photoIndex = indexOf(photo, handiwork.photos);
            handiwork.photos.splice(photoIndex, 1);
            if (shouldBeDraftify) {
                handiwork.draft = true;
                handiwork.cover = undefined;
            }
            else if (isCover) {
                handiwork.cover = handiwork.photos[0];
            }

            handiwork.put().then(ServicesFacade.createSuccessNotification('Changes are saved'));
        });
    };

    $scope.navigateToHandiworks = function() {
        ServicesFacade.navigationService.navigateToHandiworks();
    };

    $scope.startHandiworkEditing = function() {
        ServicesFacade.navigationService.navigateToEditHandiwork($scope.handiwork.id);
    };

    $scope.preview = function() {
        ServicesFacade.$window.open("/portfolio/" + $scope.handiwork.url);
    };

    $scope.confirmDeletion = function() {
        var confirmDialog = ServicesFacade.$mdDialog.confirm()
            .title('Would you like to delete this handiwork?')
            .content('This cannot be undone. A lot of work could be lost.. Check again and confirm..')
            .ok('Yes, do what I said!')
            .cancel('Nope-nope-nope');
        ServicesFacade.$mdDialog.show(confirmDialog).then(function() {
            $scope.handiwork.remove()
                .then(function() {
                    ServicesFacade.notificationService.success('Deleted!');
                    ServicesFacade.navigationService.navigateToHandiworks();
                });
        });
    };

};

var EditHandiworkController = function($scope, ServicesFacade) {

    function _saveCallback() {
        ServicesFacade.notificationService.success('Saved!');
        if ($scope.editMode) {
            ServicesFacade.navigationService.navigateToHandiwork($scope.handiwork.id);
        }
        else {
            ServicesFacade.handiworkService.getList()
                .then(function(handiworks) {
                    ServicesFacade.navigationService.navigateToHandiwork(handiworks[0].id);
                });
        }
    }

    var handiworkId = ServicesFacade.$routeParams.handiworkId;

    $scope.editMode = (typeof handiworkId !== 'undefined');

    if ($scope.editMode) {
        ServicesFacade.handiworkService.get(handiworkId).then(function(handiwork) {
            $scope.handiwork = handiwork;
        });
    }
    else {
        $scope.handiwork = {
            draft: true
        };
    }

    $scope.cancelEditing = function() {
        if ($scope.editMode) {
            ServicesFacade.navigationService.navigateToHandiwork($scope.handiwork.id);
        }
        else {
            ServicesFacade.navigationService.navigateToHandiworks();
        }
    };

    $scope.submitHandiwork = function() {
        if ($scope.editMode) {
            $scope.handiwork.put().then(_saveCallback);
        }
        else {
            ServicesFacade.handiworkService.post($scope.handiwork)
                .then(_saveCallback);
        }
    };

    $scope.onNameChange = function() {
        $scope.handiwork.url = $scope.handiwork.name.toLowerCase().replace(/ /g, "-");
    };
};