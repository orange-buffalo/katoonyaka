<#include "global-settings.ftl"/>
<!doctype html>
<html>
    <head>
        <title>Katoonyaka. We crashed</title>
        <#include "head-common.ftl"/>
        <link rel="stylesheet" href="/static/css/katoonyaka-crash.${katoonyakaVersion}.css">
    </head>
    <body>
        <div class="crash-page">
            <div class="crash-container">
                <span class="error-code">${errorCode}</span>
                <br/>
                <span class="error-message">${errorMessage}</span>
                <br/>
                <a href="/" class="home-link">Back to Home Page</a>
            </div>
        </div>
    </body>
</html>