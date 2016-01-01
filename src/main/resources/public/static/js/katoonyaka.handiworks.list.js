var KatoonyakaHandiworksList = function ($rootScope, $compile) {
    return {
        restrict: "A",

        scope: {},

        link: function (scope, $list) {

            function _compileChildren() {
                $list.children().each(function (index, value) {
                    $compile(value)(scope);
                });
            }

            var photos = [];
            $list.children().each(function (index, value) {
                var $child = $(value);
                $child.removeAttr("ng-non-bindable");

                photos.push({
                    baseUrl: $child.data("baseUrl"),
                    width: $child.data("width"),
                    height: $child.data("height"),
                    content: $child
                });

            });

            function _createTiles() {
                $list.empty().justifiedImages({
                    images: photos,
                    rowHeight: $rootScope.landscapeMode ? $rootScope.viewportHeight * 0.5 : $rootScope.viewportHeight * 0.3,
                    maxRowHeight: $rootScope.viewportHeight * 2,
                    margin: $rootScope.smallScreen ? 3 : 5,
                    thumbnailPath: function (photo, width, height) {
                        return photo.baseUrl + width + "x" + height + ".jpeg";
                    },
                    getSize: function (photo) {
                        return {width: photo.width, height: photo.height};
                    },
                    template: function (photo) {
                        var summaryContainer = photo.content.find('.handiwork-summary-container');
                        photo.content.addClass("pending");
                        photo.content.height(photo.displayHeight);
                        photo.content.css({
                            'margin-right': photo.marginRight
                        });
                        var thumb = summaryContainer.find('img');
                        thumb.attr("data-thumb-src", photo.src);
                        thumb.width(photo.displayWidth);
                        return photo.content[0].outerHTML;
                    },
                    imageContainer: 'handiwork-summary',
                    imageSelector: 'handiwork-thumb'

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