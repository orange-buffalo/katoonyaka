var KatoonyakaScroll = function ($rootScope, gaService) {
    return {
        restrict: "A",
        scope: {},
        link: function (scope) {
            scope.firstMove = true;
            scope.$body = $("body");

            var $scrollButton = $(".scroll-button");
            $scrollButton.on("click", function () {
                $rootScope.scrollTo($rootScope.viewportHeight);
                gaService.trackScrollButtonClick();
            });

            window.onscroll = function () {
                if (scope.firstMove) {
                    $rootScope.scrollTo($rootScope.viewportHeight);
                    scope.firstMove = false;
                    gaService.trackFirstScroll();
                }
                else {
                    $rootScope.$broadcast("katoonyaka::scroll", document.body.scrollTop);
                }
            };

            $rootScope.scrollTo = function (pos) {
                scope.$body.stop().animate({scrollTop: pos}, "500", "linear");
            };
        }
    }
};