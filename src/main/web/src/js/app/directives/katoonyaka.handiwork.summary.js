var KatoonyakaHandiworkSummary = function ($rootScope, gaService) {
    return {
        restrict: "A",
        scope: {},
        link: function (scope, $element) {

            function _loadThumbnail() {
                var textToClamp = $element.find("p")[0];
                $clamp(textToClamp, {clamp: 3, useNativeClamp: false});

                var $thumb = $element.find("img");
                var photoUrl = $thumb.data("thumbSrc");
                $thumb.attr("src", photoUrl);
                $element.imagesLoaded(function () {
                    $element.removeClass("pending");
                });

                gaService.trackPhotoLazyLoad(photoUrl);
            }

            function _checkVisibility() {
                if (!scope.visible && isElementVisibleInViewport($element)) {
                    scope.visible = true;
                    _loadThumbnail();
                }
            }

            scope.visible = false;

            var unregisterScrollListener = $rootScope.$on("katoonyaka::scroll", _checkVisibility);

            _checkVisibility();

            $element.on("$destroy", function () {
                unregisterScrollListener();
                scope.$destroy();
            });

        }
    }
};