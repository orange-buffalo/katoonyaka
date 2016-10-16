var NavigationController = function($scope, NavigationService, $mdBottomSheet) {

    $scope.goToHandiworks = function() {
        NavigationService.navigateToHandiworks();
        $mdBottomSheet.hide();
    };

    $scope.goToCovers = function() {
        NavigationService.navigateToCovers();
        $mdBottomSheet.hide();
    };

    $scope.showMenu = function() {
        $mdBottomSheet.show({
            templateUrl: '/admin/partials/menu-navigation.html',
            scope: $scope.$new()
        });
    };

};