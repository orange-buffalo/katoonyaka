(function () {
    var KatoonyakaController = function ($scope, $rootScope, $http) {

        $rootScope.$on("$locationChangeStart", function (angularEvent, newUrl, oldUrl) {
            if (oldUrl && oldUrl !== newUrl) {
                $http({
                    method: "GET",
                    url: newUrl,
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then(function successCallback(response) {
                    $rootScope.$broadcast("pageTransitionRequested", response.data);

                }, function errorCallback() {
                    console.log("error");  //todo
                });
            }
        });

    };

    function _configKatoonyaka($locationProvider) {
        $locationProvider.html5Mode(true);
    }

    angular.module("katoonyaka", [])
        .config(["$locationProvider", _configKatoonyaka])
        .controller("KatoonyakaController", ["$scope", "$rootScope", "$http", KatoonyakaController])
        .directive("katoonyakaHandiworksList", ["$compile", KatoonyakaHandiworksList])
        .directive("katoonyakaScroll", ["$rootScope", KatoonyakaScroll])
        .directive("katoonyakaHandiworkSummary", [KatoonyakaHandiworkSummary])
        .directive("katoonyakaCoverSlides", ["$interval", KatoonyakaCoverSlides])
        .directive("katoonyakaInternalLink", ["$location", KatoonyakaInternalLink])
        .directive("katoonyakaPageTransition", ["$rootScope", "$compile", KatoonyakaPageTransition]);
})();
