var KatoonyakaScroll = function ($rootScope) {
    return {
        restrict: "A",

        link: function (scope, element) {
            var $frame = $(element);
            $frame.addClass("frame");
            $frame.children().wrapAll("<div class='slidee'/>");

            var $scrollBar = $("<div class='scrollbar'><div class='handle'></div></div>");
            $frame.append($scrollBar);

            var sly = new Sly($frame,
                {
                    speed: 300,
                    //easing: 'easeOutExpo', //todo ?
                    scrollBar: $scrollBar,
                    scrollBy: 100,
                    dragHandle: 1,
                    dynamicHandle: 1,
                    clickBar: 1
                })
                .init();

            var $window = $(window);

            $window.on("DOMContentLoaded load resize", function () {
                sly.reload();
            });

            sly.on("move", function() {
                $window.trigger("scroll");
            });

            $rootScope.scrollTo = function(pos) {
                sly.slideTo(pos);
            }
        }
    }
};