(function () {
    var KatoonyakaController = function ($scope, $rootScope, $http) {

        $rootScope.$on("$locationChangeStart", function (angularEvent, newUrl, oldUrl) {
            if (oldUrl && oldUrl !== newUrl) {
                console.log("change to: " + newUrl + " from " + oldUrl);

                $http({
                    method: "GET",
                    url: newUrl,
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then(function successCallback(response) {
                                         console.log("done");
                    console.log(response.data);

                }, function errorCallback(response) {
                    console.log("error");
                });


            }
        });

    };

    function _initKatoonyakaAdmin() {
        console.log("inited fro func");

    }

    function _configKatoonyaka($locationProvider) {
        $locationProvider.html5Mode(true);
    }

    angular.module("katoonyaka",
        []
        )
        //.filter('nl2br', function() {
        //    return function (text) {
        //        return text ? text.replace(/\n/g, '<br>') : '';
        //    }
        //})
        //.directive('uploadcareWidget', UploadcareWidget)
        .config(["$locationProvider", _configKatoonyaka])
        //.factory(factories)
        .controller("KatoonyakaController", ["$scope", "$rootScope", "$http", KatoonyakaController])
        .run(_initKatoonyakaAdmin)


        .directive("katoonyakaHandiworksList", ["$compile", KatoonyakaHandiworksList])
        .directive("katoonyakaScroll", ["$compile", KatoonyakaScroll])
        .directive("katoonyakaHandiworkSummary", [KatoonyakaHandiworkSummary])
        .directive("katoonyakaCoverSlides", ["$interval", KatoonyakaCoverSlides])
        .directive("katoonyakaInternalLink", ["$location", KatoonyakaInternalLink])


    ;
})();
