<#include "global-settings.ftl"/>
<!doctype html>
<html>
    <head>
        <title>Katoonyaka. ${responseData.title}</title>
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="/static/css/katoonyaka.${katoonyakaVersion}.css">
        <base href="/">
    </head>
    <body ng-app="katoonyaka" ng-controller="KatoonyakaController">

    <div class="cover-slides" data-katoonyaka-cover-slides>
    <#if !covers?has_content>
        <div class="cover-slide hidden" data-photo="e5d3142d-c45b-46ed-835f-9881a9b20e93"></div>
    <#else>
        <#list covers as cover>
            <div class="cover-slide hidden" data-photo="${cover.photo.externalId}"></div>
        </#list>
    </#if>
    </div>

    <div data-katoonyaka-scroll>
        <section class="main-content">
            ${responseData.htmlContent}
        </section>
    </div>

    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0-rc.0/angular.min.js"></script>
    <script src="/static/js/jquery.justified.images.js"></script>
    <script src="/static/js/sly.js"></script>
    <script src="/static/js/utils.js"></script>
    <script src="/static/js/imagesloaded.js"></script>
    <script src="/static/js/clamp.js"></script>
    <script src="/static/js/katoonyaka.handiworks.list.js"></script>
    <script src="/static/js/katoonyaka.scroll.js"></script>
    <script src="/static/js/katoonyaka.handiwork.summary.js"></script>
    <script src="/static/js/katoonyaka.cover.slides.js"></script>
    <script src="/static/js/katoonyaka.internal.link.js"></script>
    <script src="/static/js/katoonyaka.js"></script>

    </body>
</html>




