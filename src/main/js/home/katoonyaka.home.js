var handiworkCards = [];
var $scrollAnchors = [];
var scrollInProgress = false;
var $navLinks;

function updateNavigation() {
    if (scrollInProgress) {
        return;
    }

    var found = false;
    $.each($scrollAnchors, function(index, element) {
        if (found) {
            return;
        }
        if (isElementInViewport(element)) {
            var scrollAnchor = element.data("scroll-anchor");
            location.hash = scrollAnchor;
            found = true;
            updateNavMenu(scrollAnchor);
        }
    });

    if (found) return;

    var hash = location.hash;
    var bestVisibility;
    $.each($scrollAnchors, function(index, $element) {
        var visibilityPart = getElementViewportVisibility($element);
        if (index == 0 || visibilityPart > bestVisibility) {
            bestVisibility = visibilityPart;
            hash = $element.data("scroll-anchor");
        }
    });
    location.hash = hash;
    updateNavMenu(hash);
}

function getNavBarHeight() {
    return $("nav").outerHeight();
}

function onScroll() {
    updateNavigation();
    loadCovers();
}

function loadCovers() {
    if (scrollInProgress) {
        return;
    }

    $.each(handiworkCards, function(index, handiworkCard) {
        if (handiworkCard.loaded) {
            return;
        }
        var $element = handiworkCard.$element;
        var visibilityPart = getElementViewportVisibility($element);
        if (visibilityPart > 0) {
            handiworkCard.loaded = true;

            var width = $element.outerWidth();
            handiworkCard.loadedWidth = width;
            var src = $element.data("cover") + "/" + width + "x" + Math.round(width / 2);
            var $img = $('<img src="' + src + '" />');
            $img.hide();
            $img.imagesLoaded(function() {
               $img.fadeIn(400, function() {
                   $element.find(".clock").remove();
               });
            });
            $img.appendTo($element.find("a"));
        }
    });
}

function onScrollStart() {
    scrollInProgress = true;
}

function onScrollEnd() {
    scrollInProgress = false;
    loadCovers();
}

function updateNavMenu(scrollAnchor) {
    $navLinks.parent().removeClass("active");

    var $activeMenuItem = $("[data-scroll-destination='" + scrollAnchor + "']");
    if ($activeMenuItem.length == 0) {
        $activeMenuItem = $("[data-scroll-destination='portfolio']");
    }
    $activeMenuItem.parent().addClass("active");
}

function scrollTo(scrollAnchor, callback) {
    updateNavMenu(scrollAnchor);

    var $elementToScrollTo = $("[data-scroll-anchor='" + scrollAnchor + "']");
    if ($elementToScrollTo.length == 0) {
        if (typeof callback !== 'undefined') {
            callback();
        }
        return;
    }

    location.hash = scrollAnchor;
    $elementToScrollTo.animatescroll({
        padding: getNavBarHeight(),
        easing  : "easeOutCubic",
        onScrollStart: onScrollStart,
        onScrollEnd: function () {
            onScrollEnd();
            if (typeof callback !== 'undefined') {
                callback();
            }
        }
    });
}

function isElementInViewport($el) {
    var rect = $el[0].getBoundingClientRect();

    return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= $(window).height() &&
        rect.right <= $(window).width()
    );
}

function getElementViewportVisibility($el) {
    var rect = $el[0].getBoundingClientRect();

    var height = rect.height;
    var width = rect.width;
    var totalSpace = height * width;

    var viewportHeight = $(window).height();
    var visibleBottom = (rect.bottom <= viewportHeight) ? rect.bottom : viewportHeight;

    var viewportWidth = $(window).width();
    var visibleRight = (rect.right <= viewportWidth) ? rect.right : viewportWidth;

    var visibleTop = (rect.top >= 0) ? rect.top : 0;

    var visibleLeft = (rect.left >= 0) ? rect.left : 0;

    var visibleSpace = (visibleRight - visibleLeft) * (visibleBottom - visibleTop);

    return visibleSpace / totalSpace;
}

$(function() {

    $.each($("[data-scroll-anchor]"), function(index, element) {
        $scrollAnchors.push($(element));
    });

    $navLinks = $(".nav-link");

    $navLinks.click(function(event) {
        scrollTo($(this).data("scroll-destination"));
        event.preventDefault();
    });

    $(window).scroll(onScroll);

    $(window).resize(function() {
        $.each(handiworkCards, function(index, handiworkCard) {
            if (!handiworkCard.loaded) {
                return;
            }
            var width = handiworkCard.$element.outerWidth();
            if (width <= handiworkCard.loadedWidth) {
                return;
            }
            handiworkCard.loaded = false;
            var $element = handiworkCard.$element;
            $element.find("img").remove();
            $element.find("a").prepend("<div class='clock'></div>");
        });

        onScroll();
    });

    var $coverSlider = $(".cover-slider");
    var $covers = [];
    var $children = $coverSlider.children(".enabled-slide");
    var currentCover = 0;
    var transitionTime = 700;
    var transitionEasing = 'linear';
    if ($children.length > 0) {
        $children.each(function () {
            var $cover = $(this);
            $cover.css("background",
                "url(http://www.ucarecdn.com/" + $cover.data("photo") + "/-/resize/" + $(window).width() + "x/) no-repeat center center");
            $cover.css("background-size", "cover");

            $covers.push($cover);
            //$cover.transition({x: '100%'}, 0);
            $cover.transition({opacity: '0'}, 0);
        });

        //$covers[0].transition({x: '0%'}, 0);
        $covers[0].transition({opacity: '1'}, 0);
        if ($covers.length > 1) {
            setInterval(updateCover, 3500);
        }
    }

    function updateCover() {
        var $previousCover = $covers[currentCover];

        currentCover++;
        if (currentCover >= $covers.length) {
            currentCover = 0;
        }

        //$covers[currentCover].transition({x: '0%'}, transitionTime, transitionEasing);
        //$previousCover.transition({x: '-100%'}, transitionTime, transitionEasing);

        $covers[currentCover].transition({opacity: '1'}, transitionTime, transitionEasing);
        $previousCover.transition({opacity: '0'}, transitionTime, transitionEasing);

        //setTimeout(function() {
        //    //$previousCover.transition({x: '100%'}, 0);
        //    $previousCover.transition({opacity: '100%'}, 0);
        //}, transitionTime + 1);
    }

});

$(window).load(function() {
    $.each($(".card-image"), function(index, element) {
        var $element = $(element);
        var handiworkCard = {
            $element: $element,
            loaded: false
        };
        handiworkCards.push(handiworkCard);
    });

    var initialLocation = location.hash;
    if (initialLocation) {
        initialLocation = initialLocation.replace("#", "");
    } else {
        initialLocation = "home";
    }

    scrollTo(initialLocation, loadCovers);

});