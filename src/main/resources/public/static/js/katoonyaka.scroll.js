var KatoonyakaScroll = function () {
    return {
        restrict: "A",

        link: function (scope, element) {
            var $frame = $(element);
            $frame.addClass("frame");
            $frame.children().wrapAll("<div class='slidee'/>");

            var $scrollBar = $("<div class='scrollbar'><div class='handle'></div></div>");
            $frame.append($scrollBar);

            $frame.sly({
                speed: 300,
                //easing: 'easeOutExpo', //todo ?
                scrollBar: $scrollBar,
                scrollBy: 100,
                dragHandle: 1,
                dynamicHandle: 1,
                clickBar: 1
            });

            $(window).on("DOMContentLoaded load resize", function () {
                $frame.sly("reload");
            });

            $frame.sly("on", "move", function() {
                $(window).trigger("scroll");
            });
        }
    }
};