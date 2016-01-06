var KatoonyakaNavigationBar = function ($rootScope, $compile) {
    return {
        restrict: "A",

        scope: {},

        link: function (scope, $element) {

            scope.panelled = false;
            var $additionalLinks = $element.find(".additional-links").first();

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

            $rootScope.$on("katoonyaka::pageTransitionRequested", function (event, newPageData) {
                $additionalLinks.transition({opacity: 0}, 200, function () {
                    $additionalLinks.html(newPageData.additionalLinksContent);
                    $compile($additionalLinks)(scope);
                    $additionalLinks.css({opacity: ""});
                });
            });

        }
    }
};