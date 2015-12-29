<#include "global-settings.ftl"/>
<!doctype html>
<html>
    <head>
        <title>Katoonyaka. Portfolio</title>
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="/static/css/katoonyaka.${katoonyakaVersion}.css">
    </head>
    <body ng-app="katoonyaka" ng-controller="KatoonyakaController">

    <div data-katoonyaka-scroll>
        <#--<#include "cover-slides.ftl"/>-->

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
                        <span class="name">${handiwork.name}</span>
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
    <script src="/static/js/katoonyaka.handiworks.list.js"></script>
    <script src="/static/js/katoonyaka.scroll.js"></script>
    <script src="/static/js/katoonyaka.handiwork.summary.js"></script>
    <script src="/static/js/utils.js"></script>
    <script src="/static/js/imagesloaded.js"></script>
    <script src="/static/js/katoonyaka.js"></script>


    </body>
</html>




