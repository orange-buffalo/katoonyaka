var KatoonyakaPageTransition = function ($rootScope, $compile) {
    return {
        restrict: "A",
        link: function (scope, element) {
            var container = element.find(".main-content");

            $rootScope.$on('pageTransitionRequested', function (event, newPageData) {

                window.document.title = newPageData.title;

                var $currentContent = container.children().eq(0);

                var $newContent = $("<div class='wrapper pending'>")
                    .append($(newPageData.htmlContent));

                $currentContent.transition({ opacity: 0 }, 700);

                $newContent.transition({ translate: "-100%" }, 0);

                container.append($newContent);

                var $window = $(window);

                $rootScope.scrollTo($window.height());

                $newContent.transition({ translate: "0" }, 700, function() {
                    $currentContent.remove();
                    $newContent.removeClass("pending");
                    $compile($newContent[0])(scope);
                    $window.trigger("DOMContentLoaded");
                });

            });
        }
    }
};