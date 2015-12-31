var KatoonyakaInternalLink = function ($location) {
    return {
        restrict: "A",
        link: function (scope, element) {
            var $element = $(element),
                path = $element.attr("href");

            $element.on("click", function (e) {
                scope.$apply(function () {
                    $location.path(path);
                });
                e.preventDefault();
            });
        }
    }
};