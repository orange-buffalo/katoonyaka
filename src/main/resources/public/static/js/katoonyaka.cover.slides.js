var KatoonyakaCoverSlides = function ($rootScope, $interval) {
    return {
        restrict: "A",

        scope: {},

        link: function (scope, $slider) {
            var slideWidth = $rootScope.viewportWidth,
                timeoutId,
                $slides = $slider.children(),
                currentSlide = -1;

            slideWidth = (slideWidth > 2048) ? 2048 : slideWidth;

            function _changeSlide() {
                if (currentSlide !== $slides.length) {
                    if (currentSlide >= 0) {
                        $slides.eq(currentSlide).addClass("hidden");
                    }
                    currentSlide++;
                    if (currentSlide >= $slides.length) {
                        currentSlide = 0;
                    }
                    $slides.eq(currentSlide).removeClass("hidden");
                }

            }

            $slides.each(function (index, value) {
                var $slide = $(value);
                $slide.css("background",
                    "url(http://www.ucarecdn.com/" + $slide.data("photo") + "/-/resize/" + slideWidth + "x/) no-repeat center center");

            });

            $slider.imagesLoaded({background: ".cover-slide"}, function () {
                _changeSlide();

                timeoutId = $interval(function () {
                    _changeSlide();
                }, 8000);

            });

            $slider.on("$destroy", function () {
                $interval.cancel(timeoutId);
            });

        }
    }
};