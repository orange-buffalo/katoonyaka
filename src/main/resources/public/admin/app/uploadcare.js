var UploadcareController = function($scope, ServicesFacade) {

    function _readData(data) {
        $scope.usageData = data.photosUsage;
        $scope.unusedExternalPhotosCount = data.unusedExternalPhotos.length;
        $scope.totalExternalPhotosCount = data.totalExternalPhotosCount;

        if ($scope.unusedExternalPhotosCount == 0) {
            ServicesFacade.notificationService.success("Looks like we are good, no trash in our Uploadcare project!");
        }
        else {
            ServicesFacade.notificationService.warn("Ho-ho, we have some trash uploads. Let's clean-up them?");
        }
    }

    ServicesFacade.uploadcareService.load().then(_readData);

    $scope.refresh = function() {
        ServicesFacade.uploadcareService.refresh().then(_readData);
    };

    $scope.fix = function() {
        ServicesFacade.uploadcareService.fix().then(_readData);
    };


};