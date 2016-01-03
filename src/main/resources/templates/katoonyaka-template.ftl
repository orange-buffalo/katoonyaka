<#include "global-settings.ftl"/>
<!doctype html>
<html lang="en">
    <head>
        <title>${responseData.title}</title>

        <meta charset="utf-8" />

        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1">

        <link rel="icon" type="image/png" href="/static/img/favicon.png">

        <link rel="stylesheet" href="/static/css/katoonyaka.${katoonyakaVersion}.css">

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
        <#include "scripts.ftl"/>
    </body>
</html>