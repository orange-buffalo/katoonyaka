var KatoonyakaHandiworksList = function ($compile) {
    return {

        // This means the directive can be used as an attribute only. Example <div data-my-slide="variable"> </div>
        restrict: "A",

        // This is the functions that gets executed after Angular has compiled the html
        link: function (scope, element, attrs) {
            var $list = $(element);

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
                    photo.content.height(photo.displayHeight);
                    photo.content.css({
                        'margin-right': photo.marginRight
                    });
                    var thumb = summaryContainer.find('img');
                    thumb.attr('src', photo.src);
                    thumb.width(photo.displayWidth);
                    return photo.content[0].outerHTML;
                },
                imageContainer: 'handiwork-summary',
                imageSelector: 'handiwork-thumb'

            });

            $(window).resize(function () {
                $list.empty().justifiedImages('displayImages');
            });

            $list.children().each(function (index, value) {
                $compile(value)(scope);
            });




        }
    }
};