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
                    $rootScope.$broadcast("katoonyaka::pageTransitionRequested", response.data);

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
        .run(["$rootScope", KatoonyakaBootstrap])
        .controller("KatoonyakaController", ["$scope", "$rootScope", "$http", KatoonyakaController])
        .directive("katoonyakaHandiworksList", ["$rootScope", "$compile", KatoonyakaHandiworksList])
        .directive("katoonyakaScroll", ["$rootScope", KatoonyakaScroll])
        .directive("katoonyakaHandiworkSummary", ["$rootScope", KatoonyakaHandiworkSummary])
        .directive("katoonyakaCoverSlides", ["$rootScope", "$interval", KatoonyakaCoverSlides])
        .directive("katoonyakaInternalLink", ["$rootScope", "$location", KatoonyakaInternalLink])
        .directive("katoonyakaPageTransition", ["$rootScope", "$compile", KatoonyakaPageTransition]);
})();
