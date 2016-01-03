var KatoonyakaNavigationBar = function ($rootScope) {
    return {
        restrict: "A",

        scope: {},

        link: function (scope, $element) {

            scope.panelled = false;

            function _saveDimensions() {
                scope.offsetTop = $rootScope.viewportHeight - $element.position().top - $element.outerHeight();
            }

            _saveDimensions();

            $rootScope.$on("katoonyaka::resize", _saveDimensions);

            $rootScope.$on("katoonyaka::scroll", function (e, pos) {
                if (scope.panelled && pos < scope.offsetTop) {
                    $element.removeClass("panelled");
                    scope.panelled = false;
                }
                else if (!scope.panelled && pos > scope.offsetTop) {
                    $element.addClass("panelled");
                    scope.panelled = true;
                }

            });

        }
    }
};