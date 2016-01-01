var KatoonyakaHandiworkPhoto = function () {
    return {
        restrict: "A",

        link: function (scope, $element) {

            var $thumb = $element.find("img");
            $thumb.attr("src", $thumb.data("thumbSrc"));
            $element.imagesLoaded(function () {
                $element.removeClass("pending");
            });

        }
    }
};