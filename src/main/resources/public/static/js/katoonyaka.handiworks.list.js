var KatoonyakaHandiworksList = function ($compile) {
    return {
        restrict: "A",

        link: function (scope, $list) {

            function _compileChildren() {
                $list.children().each(function (index, value) {
                    $compile(value)(scope);
                });
            }

            var lastHeight = scope.viewportHeight;
            var lastWidth = scope.viewportWidth;

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

            $list.empty().justifiedImages({
                images: photos,
                rowHeight: 300,
                maxRowHeight: 1000,
                margin: 5,
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

            var unregisterLayoutListener = scope.$on("katoonyaka::layoutChange", function () {
                if (lastHeight !== scope.viewportHeight || lastWidth !== scope.viewportWidth) {
                    console.log("size changed");

                    $list.empty().justifiedImages('displayImages');
                    _compileChildren();
                    lastHeight = scope.viewportHeight;
                    lastWidth = scope.viewportWidth;
                }
            });

            _compileChildren();

            $list.on("$destroy", function () {
                unregisterLayoutListener();
                scope.$destroy();
            });
        }
    }
};