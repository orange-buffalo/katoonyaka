var KatoonyakaScroll = function ($rootScope, gaService) {
    return {
        restrict: "A",

        scope: {},

        link: function (scope, $frame) {
            scope.scrollBarVisible = false;
            scope.firstMove = true;

            $frame.addClass("frame");
            $frame.children().wrapAll("<div class='slidee'/>");

            var $scrollBar = $("<div class='scrollbar'><div class='handle'></div></div>");
            $scrollBar.transition({opacity: 0}, 0);
            $frame.append($scrollBar);

            var sly = new Sly($frame,
                {
                    touchDragging: true,
                    speed: 400,
                    scrollBar: $scrollBar,
                    scrollBy: $rootScope.smallScreen? 80 : 100,
                    dragHandle: true,
                    dynamicHandle: true,
                    clickBar: true,
                    easing: "linear",
                    releaseSwing: true
                })
                .init();

            $rootScope.$on("katoonyaka::layoutChange", sly.reload);
            $rootScope.$on("katoonyaka::pageTransitionFinished", sly.reload);

            var $scrollButton = $(".scroll-button");
            $scrollButton.on("click", function() {
                $rootScope.scrollTo($rootScope.viewportHeight);
                gaService.trackScrollButtonClick();
            });

            sly.on("move", function () {
                if (scope.firstMove) {
                    $rootScope.scrollTo($rootScope.viewportHeight);
                    scope.firstMove = false;
                    $scrollButton.hide(300);
                    gaService.trackFirstScroll();
                }

                $rootScope.$broadcast("katoonyaka::scroll", sly.pos.cur);

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
            };

        }
    }
};