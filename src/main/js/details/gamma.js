var Gamma = (function () {

    var defaults = {
            columns: 4,
            viewport : [ {
                width : 900,
                columns : 3
            }, {
                width : 500,
                columns : 2
            }, {
                width : 0,
                columns : 1
            } ]
        },
        init = function (settings) {

            Gamma.settings = $.extend(true, {}, defaults, settings);

            // cache some elements..
            _config();
            // build the layout
            _layout();
            // init masonry
            _initMasonry(function () {

                // remove loading status
                Gamma.container.removeClass('gamma-loading');
                // show items
                Gamma.items.show();
                Gamma.gallery.masonry();

                // init window events
                _initEvents();

            });

        },
        _config = function () {

            Gamma.container = $('.gamma-container');
            Gamma.gallery = Gamma.container.children('ul.gamma-gallery');
            Gamma.items = Gamma.gallery.children();
            Gamma.itemsCount = Gamma.items.length;
            Gamma.columns = Gamma.settings.columns;
        },

    // transform initial html structure into a list of images (well mostly)
        _layout = function ($items) {

            _setMasonry();

            var $items = $items || Gamma.items.hide();

            // replace each div element with an image element with the right source
            $items.each(function (index) {

                var $item = $(this),
                    $picEl = $item.children(),
                    sources = _getImgSources($picEl),
                    source = _chooseImgSource(sources, $item.outerWidth(true), false);

                // data is saved in the <li> element
                $item.data({
                    source: sources,
                    maxwidth: $picEl.data('maxWidth'),
                    maxheight: $picEl.data('maxHeight')
                });

                $('<div/>').addClass('gamma-description').html("").insertAfter($picEl);

                $('<img/>').attr({
                    alt: $picEl.data('alt'),
                    title: $picEl.data('title'),
                    src: source.src
                }).insertAfter($picEl);

                $picEl.remove();

                $item.click(function() {
                    _openPhotoSwipe(index);
                });
            });

        },
    // gets all possible image sources of an element
        _getImgSources = function ($el) {

            var theSources = [];
            $el.children('div').each(function (i) {

                var $source = $(this);
                theSources.push({
                    width: $source.data('minWidth') || 0,
                    psHeight: $source.data('height'),
                    psWidth: $source.data('width'),
                    src: $source.data('src'),
                    pos: i
                });

            });

            return theSources;

        },
    // change the number of masonry columns based on the current container's width and the settings.viewport configuration
        _setMasonry = function () {

            var containerW = Gamma.container.width();

            if (Gamma.settings.viewport) {

                for (var i = 0, len = Gamma.settings.viewport.length; i < len; ++i) {

                    var viewport = Gamma.settings.viewport[i];

                    if (containerW > viewport.width) {

                        Gamma.columns = viewport.columns;
                        break;

                    }

                }

            }

            // set the widths (%) for each of the <li>
            Gamma.items.css('width', Math.floor(containerW / Gamma.columns) * 100 / containerW + '%');

        },
    // initialize masonry
        _initMasonry = function (callback) {

            Gamma.gallery.imagesLoaded(function () {

                Gamma.gallery.masonry({
                    itemSelector: 'li',
                    columnWidth: 'li'
                });

                if (callback) {
                    callback.call();
                }

            });

        },
        _getThumbBoundsFn = function(index) {
            var $el = $($('.gamma-gallery li img')[index]);
            var offset = $el.offset();
            return {x:offset.left, y:offset.top, w:$el.outerWidth()};
        },
        _openPhotoSwipe = function (itemIndex) {

            var pswpElement = document.querySelectorAll('.pswp')[0];

            var psItems = [];
            var width = $(window).width();
            Gamma.items.each(function(index, gammaItem) {
                var data = $(gammaItem).data();
                var source = _chooseImgSource(data.source, width, true);

                psItems.push({
                    src: source.src,
                    w: source.psWidth,
                    h: source.psHeight
                });
            });

            // define options (if needed)
            var options = {
                index: itemIndex,
                history: false,
                focus: false,
                getThumbBoundsFn: _getThumbBoundsFn,
                bgOpacity: 0.8,
                addCaptionHTMLFn: function() {
                    return false;
                }

            };

            var gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, psItems, options);
            gallery.init();

        },
    // reloads masonry grid
        _reloadMasonry = function () {
            Gamma.gallery.imagesLoaded(function () {
                Gamma.gallery.masonry();
            });
        },
    // choose a source based on the item's size and on the configuration set by the user in the initial HTML
        _chooseImgSource = function (sources, w, photoswipeRequest) {

            if (w <= 0) {
                w = 1;
            }

            for (var i = 0, len = sources.length; i < len; ++i) {

                var source = sources[i];

                var sourceWidth = photoswipeRequest ? source.psWidth : source.width;

                if (w > sourceWidth) {

                    return source;

                }

            }

        },
    // resize the window event
        _resize = function () {

            _setMasonry();

            _resizeGrid();

            _reloadMasonry();

        },
    // resizes the masonry grid
    // change the source of the images (grid) accordingly
        _resizeGrid = function () {

            Gamma.items.each(function () {

                var $item = $(this),
                    source = _chooseImgSource($item.data('source'), Gamma.items.outerWidth(true), false);

                $item.find('img').attr('src', source.src);

            });

        },

    // initializes events according to type
        _initEvents = function() {
            $(window).resize(_resize);
        };

    return {
        init: init
    }

})();