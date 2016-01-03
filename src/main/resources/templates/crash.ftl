<#include "global-settings.ftl"/>
<!doctype html>
<html>
    <head>
        <title>Katoonyaka. We crashed</title>
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1">
        <link rel="icon" type="image/png" href="/static/img/favicon.png">
        <link rel="stylesheet" href="/static/css/katoonyaka.${katoonyakaVersion}.css">
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