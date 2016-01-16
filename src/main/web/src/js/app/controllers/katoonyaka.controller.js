var KatoonyakaController = function ($scope, $rootScope, $http, gaService) {

    $rootScope.$on("$locationChangeStart", function (angularEvent, newUrl, oldUrl) {
        if (oldUrl && oldUrl !== newUrl) {
            $http({
                method: "GET",
                url: newUrl,
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function successCallback(response) {
                $rootScope.$broadcast("katoonyaka::pageTransitionRequested", response.data);

            }, function errorCallback() {
                console.log("error");  //todo
            });
        }
    });

    $rootScope.$on("katoonyaka::pageTransitionFinished", gaService.trackPageView);

};