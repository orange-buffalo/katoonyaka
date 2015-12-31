var KatoonyakaScroll = function ($rootScope) {
    return {
        restrict: "A",

        scope: {},

        link: function (scope, element) {
            var $window = $(window);
            scope.scrollBarVisible = false;
            scope.scrollBarStartPosition = $window.height();

            var $frame = $(element);
            $frame.addClass("frame");
            $frame.children().wrapAll("<div class='slidee'/>");

            var $scrollBar = $("<div class='scrollbar'><div class='handle'></div></div>");
            $scrollBar.transition({opacity: 0}, 0);
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



            $window.on("DOMContentLoaded load resize", function () {
                scope.scrollBarStartPosition = $window.height();
                sly.reload();
            });

            sly.on("move", function () {
                $window.trigger("scroll");

                if (!scope.scrollBarVisible && sly.pos.cur > scope.scrollBarStartPosition) {
                    $scrollBar.transition({opacity: 1}, 400);
                    scope.scrollBarVisible = true;
                }

                if (scope.scrollBarVisible && sly.pos.cur < scope.scrollBarStartPosition) {
                    $scrollBar.transition({opacity: 0}, 400);
                    scope.scrollBarVisible = false;
                }


            });

            $rootScope.scrollTo = function (pos) {
                sly.slideTo(pos);
            }
        }
    }
};