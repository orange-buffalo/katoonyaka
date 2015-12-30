<#include "global-settings.ftl"/>
<!doctype html>
<html>
    <head>
        <title>Katoonyaka. Portfolio</title>
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="/static/css/katoonyaka.${katoonyakaVersion}.css">
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


            <div id="handiwork-list"
                 class="handiworks-list"
                 data-katoonyaka-handiworks-list>
            <#list handiworks as handiwork>
                <a href="/portfolio/${handiwork.url}"
                   data-base-url="/photos/${handiwork.url}/${handiwork.cover.id}/"
                   data-width="${handiwork.cover.width?c}"
                   data-height="${handiwork.cover.height?c}"
                   data-katoonyaka-handiwork-summary
                   class="handiwork-summary">
                    <div class="handiwork-summary-container">
                        <img class="handiwork-thumb"/>
                        <div class="handiwork-description">
                            <div class="handiwork-description-wrapper">
                               <h3>${handiwork.name}</h3>
                                <hr/>
                                <p>${handiwork.summary}</p>
                            </div>
                        </div>
                    </div>
                </a>
            </#list>
            </div>




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
    <script src="/static/js/katoonyaka.js"></script>


    </body>
</html>




