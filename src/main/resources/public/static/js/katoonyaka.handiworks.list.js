var KatoonyakaHandiworksList = function ($compile) {
    return {
        restrict: "A",

        link: function (scope, element) {
            var $list = $(element);

            function _compileChildren() {
                $list.children().each(function (index, value) {
                    $compile(value)(scope);
                });
                $(window).trigger("scroll");
            }

            var photos = [];
            $list.children().each(function (index, value) {
                var $child = $(value);

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

            $(window).resize(function () {
                $list.empty().justifiedImages('displayImages');
                _compileChildren();
            });

            _compileChildren();
        }
    }
};