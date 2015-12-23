var CoversController = function($scope, ServicesFacade) {

    ServicesFacade.configService.getConfigValue("uploadcare.publicKey")
        .then(function (publicKey) {
            $scope.uploadcarePublicKey = publicKey;
        });

    function _reloadCovers() {
        ServicesFacade.coverService.getList()
            .then(function(covers) {
                $scope.covers = covers;
            })
    }

    _reloadCovers();

    $scope.showMenu = function(cover) {
        $scope.selectedCover = cover;

        ServicesFacade.$mdBottomSheet.show({
            templateUrl: '/admin/partials/covers-item-operations.html',
            scope: $scope.$new()
        });
    };

    $scope.startCoverEditing = function(cover) {
        ServicesFacade.navigationService.navigateToEditCover(cover.id);
        ServicesFacade.$mdBottomSheet.cancel();
    };

    $scope.startCoverCreation = function() {
        ServicesFacade.navigationService.navigateToCreateCover();
        ServicesFacade.$mdBottomSheet.cancel();
    };

    $scope.publish = function(cover) {
        if (!cover.photo || !cover.photo.externalId) {
            ServicesFacade.$mdBottomSheet.cancel();
            ServicesFacade.notificationService.warn('Add cover photo first!');
            return;
        }

        cover.draft = false;
        cover.put()
            .then(function() {
                ServicesFacade.notificationService.success("Cover is now visible for others!");
                ServicesFacade.$mdBottomSheet.cancel();
                _reloadCovers();
            });
    };

    $scope.unpublish = function(cover) {
        cover.draft = true;
        cover.put()
            .then(function() {
                ServicesFacade.notificationService.success('Draftified and hidden from others!');
                ServicesFacade.$mdBottomSheet.cancel();
                _reloadCovers();
            });
    };

    $scope.askForDeletionConfirmation = function(cover) {
        var confirmDialog = ServicesFacade.$mdDialog.confirm()
            .title('Would you like to delete this cover?')
            .content('This cannot be undone. So make sure you are doing the right thing :)')
            .ok('Yes, do what I said!')
            .cancel('Nope');
        ServicesFacade.$mdDialog.show(confirmDialog).then(function() {
            cover.remove()
                .then(function() {
                    ServicesFacade.notificationService.success('Deleted!');
                    _reloadCovers();
                    ServicesFacade.$mdBottomSheet.cancel();
                });
        });
    };

    $scope.moveCover = function(index, item) {
        ServicesFacade.coverService.moveCover(item, index)
            .then(function() {
                ServicesFacade.notificationService.success("Saved");
                _reloadCovers();
            });
    };

};

var EditCoverController = function($scope, ServicesFacade) {

    function _saveCallback() {
        ServicesFacade.notificationService.success('Saved!');
        ServicesFacade.navigationService.navigateToCovers();
    }

    var coverId = ServicesFacade.$routeParams.coverId;
    $scope.editMode = !_.isUndefined(coverId);

    if ($scope.editMode) {
        ServicesFacade.coverService.get(coverId)
            .then(function(cover) {
                $scope.selectedCover = cover;
            });
    }
    else {
        $scope.selectedCover = {
            horizontalAlignment: "CENTER",
            verticalAlignment: "CENTER",
            draft: true
        };
    }

    $scope.cancelEditing = function() {
        ServicesFacade.navigationService.navigateToCovers();
    };

    $scope.submitCover = function() {
        if ($scope.editMode) {
            $scope.selectedCover.put()
                .then(_saveCallback);
        }
        else {
            ServicesFacade.coverService.post($scope.selectedCover)
                .then(_saveCallback);
        }
    };

    $scope.onPhotoUploaded = function(fileInfo) {
        $scope.selectedCover.photo = createUploadcarePhoto(fileInfo);
        ServicesFacade.notificationService.info(fileInfo.name + " was uploaded, don't forget to save the changes!");
    }

};