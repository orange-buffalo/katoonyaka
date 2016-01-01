var KatoonyakaInternalLink = function ($rootScope, $location) {
    return {
        restrict: "A",

        link: function (scope, $element) {
            var path = $element.attr("href");

            $element.on("click", function (e) {
                $rootScope.$apply(function () {
                    $location.path(path);
                });
                e.preventDefault();
            });
        }
    }
};