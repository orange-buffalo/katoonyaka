var KatoonyakaBootstrap = function ($rootScope) {

    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-47067320-1', 'auto');
    ga('send', 'pageview');

    function _getViewportMetrics() {
        $rootScope.viewportHeight = $rootScope.$window.height();
        $rootScope.viewportWidth = $rootScope.$window.width();
        $rootScope.landscapeMode = $rootScope.viewportHeight < $rootScope.viewportWidth;
        $rootScope.smallScreen = $rootScope.viewportWidth < 600;
    }

    $rootScope.$window = $(window);
    _getViewportMetrics();

    $rootScope.$window.on("DOMContentLoaded load", function () {
        $rootScope.$broadcast("katoonyaka::layoutChange");
    });

    $rootScope.$window.on("resize", function () {
        _getViewportMetrics();
        $rootScope.$broadcast("katoonyaka::resize");
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