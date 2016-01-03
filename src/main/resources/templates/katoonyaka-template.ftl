<#include "global-settings.ftl"/>
<!doctype html>
<html lang="en">
    <head>
        <title>${responseData.title}</title>

        <meta charset="utf-8" />

        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1">

        <link rel="icon" type="image/png" href="/static/img/favicon.png">

    <#--<link rel="stylesheet" href="/static/css/katoonyaka.${katoonyakaVersion}.css"> todo -->
        <link rel="stylesheet" href="/static/css/katoonyaka.css">

        <base href="/">
    </head>
    <body ng-app="katoonyaka" ng-controller="KatoonyakaController">

        <#include "cover-slides.ftl"/>
        <#include "nav-bar.ftl"/>

        <div katoonyaka-scroll>
            <div class="header-container">
                <div class="header-title"></div>
                <div class="scroll-button"></div>
            </div>

            <section class="main-content" katoonyaka-page-transition>
                <div class="wrapper">
                    ${responseData.htmlContent}
                </div>
            </section>
        </div>

        <#include "pswp.ftl"/>

        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0-rc.0/angular.min.js"></script>
        <script src="/static/js/jquery.justified.images.js"></script>
        <script src="/static/js/sly.js"></script>
        <script src="/static/js/imagesloaded.js"></script>
        <script src="/static/js/clamp.js"></script>
        <script src="/static/js/jquery.transit.js"></script>
        <script src="/static/js/photoswipe.js"></script>
        <script src="/static/js/photoswipe-ui-default.js"></script>
        <script src="/static/js/katoonyaka.justified.gallery.js"></script>
        <script src="/static/js/katoonyaka.scroll.js"></script>
        <script src="/static/js/katoonyaka.handiwork.summary.js"></script>
        <script src="/static/js/katoonyaka.handiwork.photo.js"></script>
        <script src="/static/js/katoonyaka.cover.slides.js"></script>
        <script src="/static/js/katoonyaka.internal.link.js"></script>
        <script src="/static/js/katoonyaka.page.transition.js"></script>
        <script src="/static/js/katoonyaka.bootstrap.js"></script>
        <script src="/static/js/katoonyaka.photoswipe.js"></script>
        <script src="/static/js/katoonyaka.navigation.bar.js"></script>
        <script src="/static/js/katoonyaka.js"></script>

    </body>
</html>




