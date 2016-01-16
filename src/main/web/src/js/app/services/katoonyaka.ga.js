var GaService = function () {

    function _trackPageView() {
        if (ga) {
            ga("send", "pageview");
        }
    }

    function _trackPhotoLazyLoad(photoUrl) {
        if (ga) {
            ga("send", "event", "photos", "lazy loaded", photoUrl);
        }
    }

    function _trackPhotoFullView(photoUrl) {
        if (ga) {
            ga("send", "event", "photos", "full view opened", photoUrl);
        }
    }

    function _trackAdditionalLinkClick(url) {
        if (ga) {
            ga("send", "event", "links", "navigated via additional link", url);
        }
    }

    function _trackExternalLinkClick(url) {
        if (ga) {
            ga("send", "event", "links", "external navigation", url);
        }
    }

    function _trackFirstScroll() {
        if (ga) {
            ga("send", "event", "scroll", "first scroll");
        }
    }

    function _trackScrollButtonClick() {
        if (ga) {
            ga("send", "event", "scroll", "scroll button click");
        }
    }

    return {
        trackPageView: _trackPageView,
        trackPhotoLazyLoad: _trackPhotoLazyLoad,
        trackPhotoFullView: _trackPhotoFullView,
        trackAdditionalLinkClick: _trackAdditionalLinkClick,
        trackExternalLinkClick: _trackExternalLinkClick,
        trackFirstScroll: _trackFirstScroll,
        trackScrollButtonClick: _trackScrollButtonClick
    }
};