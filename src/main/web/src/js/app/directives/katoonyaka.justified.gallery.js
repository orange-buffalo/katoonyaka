var KatoonyakaJustifiedGallery = function ($rootScope, $compile, photoService) {
    return {
        restrict: "A",

        scope: {
            itemContainerClass: "@",
            itemThumbClass: "@",
            rowHeightFactor: "@"
        },

        link: function (scope, $list) {

            function _compileChildren() {
                $list.children().each(function (index, value) {
                    $compile(value)(scope);
                });
            }

            var photos = [];
            $list.children().each(function (index, value) {
                var $child = $(value);
                $child.remove();
                $child.removeAttr("data-ng-non-bindable");
                $child.removeClass("loading");

                photos.push({
                    baseUrl: $child.data("baseUrl"),
                    width: $child.data("width"),
                    height: $child.data("height"),
                    content: $child
                });
            });

            function _createTiles() {
                var rowHeightFactor = scope.rowHeightFactor ? scope.rowHeightFactor : 1;
                var maxHeight = Math.min($rootScope.viewportHeight, 900);
                var rowHeight = rowHeightFactor * ($rootScope.landscapeMode ? maxHeight * 0.5 : maxHeight * 0.3);

                $list.empty().justifiedImages({
                    images: photos,
                    rowHeight: rowHeight,
                    maxRowHeight: $rootScope.viewportHeight * 3,
                    margin: $rootScope.smallScreen ? 3 : 5,
                    thumbnailPath: function (photo, width) {
                      return photoService.getPhotoUrl(photo.baseUrl, width);
                    },
                    getSize: function (photo) {
                        return {width: photo.width, height: photo.height};
                    },
                    template: function (photo) {
                        photo.content.addClass("pending");
                        photo.content.height(photo.displayHeight);
                        photo.content.css({
                            'margin-right': photo.marginRight
                        });
                        var thumb = photo.content.find("img");
                        thumb.attr("data-thumb-src", photo.src);
                        thumb.width(photo.displayWidth);
                        return photo.content[0].outerHTML;
                    },
                    imageContainer: scope.itemContainerClass,
                    imageSelector: scope.itemThumbClass
                });
            }

            _createTiles();

            var unregisterResizeListener = $rootScope.$on("katoonyaka::resize", function () {
                _createTiles();
                _compileChildren();
            });

            _compileChildren();

            $list.on("$destroy", function () {
                unregisterResizeListener();
                scope.$destroy();
            });
        }
    }
};