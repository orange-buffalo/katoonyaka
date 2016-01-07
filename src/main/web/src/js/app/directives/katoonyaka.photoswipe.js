var KatoonyakaPhotoswipe = function ($rootScope) {
    return {
        restrict: "A",

        scope: {
            gallerySelector: "@",
            galleryThumbSelector: "@"
        },

        link: function (scope, $element) {

            function _setupPhotoswipe() {
                var $gallery = $(scope.gallerySelector),
                    $galleryThumbs = $gallery.find(scope.galleryThumbSelector);

                function _getThumbBoundsFn(index) {
                    var $el = $galleryThumbs.eq(index);
                    var offset = $el.offset();
                    return {
                        x: offset.left,
                        y: offset.top,
                        w: $el.outerWidth()
                    };
                }

                var psItems = [];
                $galleryThumbs.each(function (index, value) {
                    var $thumb = $(value);

                    var originalPhotoHeight = $thumb.data("height");
                    var photoHeight = Math.min($rootScope.viewportHeight, originalPhotoHeight, 2048);

                    var originalPhotoWidth = $thumb.data("width");
                    var photoWidth = Math.round(photoHeight / originalPhotoHeight * originalPhotoWidth);

                    if (photoWidth > 2048) {
                        photoWidth = 2048;
                        photoHeight = Math.round(photoWidth / originalPhotoWidth * originalPhotoHeight);
                    }

                    psItems.push({
                        src: $thumb.data("baseUrl") + ".w" + photoWidth + ".jpeg",
                        w: photoWidth,
                        h: photoHeight
                    });

                });

                var barSize = $rootScope.smallScreen ? 7 : 15;

                $galleryThumbs.on("click", function () {
                    var $thumb = $(this);

                    var index = $galleryThumbs.index($thumb);


                    var options = {
                        index: index,
                        history: false,
                        getThumbBoundsFn: _getThumbBoundsFn,
                        bgOpacity: 0.85,
                        mainClass: 'pswp--minimal--dark',
                        barsSize: {top: barSize, bottom: barSize},
                        captionEl: false,
                        shareEl: false,
                        zoomEl: false,
                        tapToClose: true,
                        tapToToggleControls: false,
                        showHideOpacity: true,
                        closeOnScroll: false,
                        closeOnVerticalDrag: false,
                        indexIndicatorSep: " of "
                    };

                    var gallery = new PhotoSwipe($element[0], PhotoSwipeUI_Default, psItems, options);
                    gallery.init();

                });
            }

            _setupPhotoswipe();

            $rootScope.$on("katoonyaka::pageTransitionFinished", _setupPhotoswipe);
            $rootScope.$on("katoonyaka::resize", _setupPhotoswipe);

        }
    }
};