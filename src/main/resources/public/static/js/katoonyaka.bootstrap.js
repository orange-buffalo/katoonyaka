var KatoonyakaBootstrap = function ($rootScope) {

    function _getViewportMetrics() {
        $rootScope.viewportHeight = $rootScope.$window.height();
        $rootScope.viewportWidth = $rootScope.$window.width();
    }

    $rootScope.$window = $(window);
    _getViewportMetrics();

    $rootScope.$window.on("DOMContentLoaded load resize", function () {
        _getViewportMetrics();
        $rootScope.$broadcast("katoonyaka::layoutChange");
    });

    window.getElementViewportVisibility = function (el) {
        if (typeof jQuery === "function" && el instanceof jQuery) {
            el = el[0];
        }

        var rect = el.getBoundingClientRect();

        var height = rect.height;
        var width = rect.width;
        var totalSpace = height * width;

        var visibleBottom = (rect.bottom <= $rootScope.viewportHeight)
            ? rect.bottom
            : $rootScope.viewportHeight;

        var visibleRight = (rect.right <= $rootScope.viewportWidth)
            ? rect.right :
            $rootScope.viewportWidth;

        var visibleTop = (rect.top >= 0) ? rect.top : 0;

        var visibleLeft = (rect.left >= 0) ? rect.left : 0;

        var visibleSpace = (visibleRight - visibleLeft) * (visibleBottom - visibleTop);

        return visibleSpace / totalSpace;
    };

    window.isElementVisibleInViewport = function (el) {
        return getElementViewportVisibility(el) > 0;
    };

};