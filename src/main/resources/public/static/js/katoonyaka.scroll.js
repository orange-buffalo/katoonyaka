var KatoonyakaScroll = function ($rootScope) {
    return {
        restrict: "A",

        scope: {},

        link: function (scope, $frame) {
            scope.scrollBarVisible = false;

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

            $rootScope.$on("katoonyaka::layoutChange", sly.reload);
            $rootScope.$on("katoonyaka::pageTransitionFinished", sly.reload);

            sly.on("move", function () {
                $rootScope.$broadcast("katoonyaka::scroll", sly.pos);

                if (!scope.scrollBarVisible && sly.pos.cur > $rootScope.viewportHeight) {
                    $scrollBar.transition({opacity: 1}, 400);
                    scope.scrollBarVisible = true;
                }

                if (scope.scrollBarVisible && sly.pos.cur < $rootScope.viewportHeight) {
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