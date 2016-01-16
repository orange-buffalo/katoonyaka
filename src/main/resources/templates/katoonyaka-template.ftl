<#include "global-settings.ftl"/>
<!doctype html>
<html lang="en" prefix="og: http://ogp.me/ns#">
    <head>
        <title>${responseData.title}</title>

        <meta charset="utf-8" />

        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1">

        <meta name="description" content="${responseData.metaDescription!""}">

        <meta property="og:title" content="${responseData.title}"/>
        <meta property="og:description" content="${responseData.metaDescription!""}"/>
        <meta property="og:url" content="http://katoonyaka.co/${responseData.relativeUrl}"/>
        <meta property="og:image" content="http://katoonyaka.co/${responseData.relativeImageUrl}"/>
        <meta property="og:type" content="website"/>

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