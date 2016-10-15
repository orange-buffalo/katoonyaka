(function () {
    function _configKatoonyaka($locationProvider) {
        $locationProvider.html5Mode(true);
    }

    angular.module("katoonyaka", [])
        .config(["$locationProvider", _configKatoonyaka])
        .run(["$rootScope", KatoonyakaBootstrap])
        .controller("KatoonyakaController", ["$scope", "$rootScope", "$http", "gaService", KatoonyakaController])
        .factory("gaService", [GaService])
        .factory("photoService", [PhotoService])
        .directive("katoonyakaJustifiedGallery", ["$rootScope", "$compile", "photoService", KatoonyakaJustifiedGallery])
        .directive("katoonyakaScroll", ["$rootScope", "gaService", KatoonyakaScroll])
        .directive("katoonyakaHandiworkSummary", ["$rootScope", "gaService", KatoonyakaHandiworkSummary])
        .directive("katoonyakaNavigationBar", ["$rootScope", "$compile", "gaService", KatoonyakaNavigationBar])
        .directive("katoonyakaHandiworkPhoto", [KatoonyakaHandiworkPhoto])
        .directive("katoonyakaCoverSlides", ["$rootScope", "$interval", "photoService", KatoonyakaCoverSlides])
        .directive("katoonyakaInternalLink", ["$rootScope", "$location", KatoonyakaInternalLink])
        .directive("katoonyakaPageTransition", ["$rootScope", "$compile", KatoonyakaPageTransition])
        .directive("katoonyakaExternalLinks", ["$rootScope", "gaService", KatoonyakaExternalLinks])
        .directive("katoonyakaPhotoswipe", ["$rootScope", "gaService", "photoService", KatoonyakaPhotoswipe]);
})();
