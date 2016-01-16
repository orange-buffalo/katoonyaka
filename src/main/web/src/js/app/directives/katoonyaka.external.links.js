var KatoonyakaExternalLinks = function ($rootScope, gaService) {
    return {
        restrict: "A",

        link: function () {

            function _onExternalLinkClick() {
                gaService.trackExternalLinkClick(this.href);
            }

            function _setupListeners() {
                var $externalLinks = $("a[href^='http']");
                $externalLinks.off("click", _onExternalLinkClick);
                $externalLinks.on("click", _onExternalLinkClick);
            }

            _setupListeners();

            $rootScope.$on("katoonyaka::pageTransitionFinished", _setupListeners);
        }
    }
};