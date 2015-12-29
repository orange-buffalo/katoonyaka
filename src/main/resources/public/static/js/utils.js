function getElementViewportVisibility(el) {
    if (typeof jQuery === "function" && el instanceof jQuery) {
        el = el[0];
    }

    var rect = el.getBoundingClientRect();

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

function isElementVisibleInViewport(el) {
    return getElementViewportVisibility(el) > 0;
}