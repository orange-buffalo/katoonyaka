var CoversController = function($scope, ServicesFacade) {

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

    $scope.publish = function(cover) {
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

  $scope.uploadCover = function (files) {
    ServicesFacade.uploadService.uploadPhotos(files, "/admin/api/covers", _reloadCovers);
  }

};