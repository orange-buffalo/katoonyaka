var KatoonyakaHandiworkSummary = function () {
    return {
        restrict: "A",
        scope: {},
        link: function (scope, element) {
            var $element = $(element);

            function _loadThumbnail() {
                var textToClamp = $element.find("p")[0];
                $clamp(textToClamp, {clamp: 3, useNativeClamp: false});

                var $thumb = $element.find("img");
                $thumb.attr("src", $thumb.data("thumbSrc"));
                $element.imagesLoaded(function () {
                    $element.removeClass("pending");
                });
            }

            scope.visible = false;

            $(window).on("DOMContentLoaded load resize scroll", function () {
                if (!scope.visible && isElementVisibleInViewport(element)) {
                    scope.visible = true;
                    _loadThumbnail();
                }
            });
        }
    }
};