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
    <body data-ng-app="katoonyaka" data-ng-controller="KatoonyakaController">
        <#include "includes/cover-slides.ftl"/>
        <#include "includes/nav-bar.ftl"/>

        <div data-katoonyaka-scroll>
            <div class="header-container">
                <div class="header-title"></div>
                <div class="scroll-button"></div>
            </div>

            <main class="main-content" data-katoonyaka-page-transition>
                <div class="wrapper">
                    ${responseData.mainContent}
                </div>
            </main>
        </div>

        <#include "includes/pswp.ftl"/>
        <#include "includes/scripts.ftl"/>
    </body>
</html>