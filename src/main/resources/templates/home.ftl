<#include "global-settings.ftl"/>
<#include "utils.ftl"/>
<!doctype html>
<html>
    <head>
        <title>Katoonyaka. Portfolio</title>
        <#include "head-common.ftl"/>
        <link rel="stylesheet" href="/static/css/katoonyaka-home.${katoonyakaVersion}.css">
    </head>
    <body>
    <#include "nav-bar.ftl"/>
        <div>

            <section id="header-cover" data-scroll-anchor="home">
                <div class="cover-slider">
                <#if !covers?has_content>
                    <div class="cover-slide" style="background-image: url('http://www.ucarecdn.com/e5d3142d-c45b-46ed-835f-9881a9b20e93/')">
                        <section class="header-text center">
                            <div class="row">
                                <div class="small-12 medium-12 large-6 columns">
                                    <h1 class="header-name">katoonyaka</h1>
                                </div>
                            </div>
                            <div class="row">
                                <div class="small-12 medium-12 large-6 columns">
                                    <div class="header-description">creations</div>
                                </div>
                            </div>
                        </section>
                    </div>
                <#else>
                    <#list covers as cover>
                        <div class="cover-slide enabled-slide" data-photo="${cover.photo.externalId}">
                            <#if cover.verticalAlignment?has_content && cover.verticalAlignment == "TOP">
                                <#assign styleClass = "top">
                            <#elseif cover.verticalAlignment?has_content && cover.verticalAlignment == "BOTTOM">
                                <#assign styleClass = "bottom">
                            <#else>
                                <#assign styleClass = "center">
                            </#if>

                            <section class="header-text ${styleClass}">
                                <#if cover.horizontalAlignment?has_content && cover.horizontalAlignment == "LEFT">
                                    <#assign styleClass = "left">
                                <#elseif cover.horizontalAlignment?has_content && cover.horizontalAlignment == "RIGHT">
                                    <#assign styleClass = "right">
                                <#else>
                                    <#assign styleClass = "center">
                                </#if>

                                <div class="row">
                                    <div class="header-text-row ${styleClass}">
                                        <h1 class="header-name">katoonyaka</h1>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="header-text-row ${styleClass}">
                                        <div class="header-description">${cover.text!"creations"}</div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </#list>
                </#if>
                </div>
            </section>

            <section id="portfolio-section" data-scroll-anchor="portfolio">
                <div class="row">
                <#list handiworks as handiwork>
                    <section class="small-12 medium-12 large-6 columns<#if handiwork_has_next><#else> end</#if>" data-scroll-anchor="${handiwork.url}">
                        <div class="handiwork-card">
                            <div class="card-image" data-cover="/photos/${handiwork.url}/${handiwork.cover.id}">
                                <a href="/portfolio/${handiwork.url}">
                                    <div class="clock"></div>
                                    <noscript>
                                        <img src="/photos/${handiwork.url}.${handiwork.cover.id}.jpeg"/>
                                    </noscript>
                                </a>
                            </div>
                            <div class="card-title">
                                <h3>${handiwork.name}</h3>
                            </div>
                            <div class="card-action">
                                <a href="/portfolio/${handiwork.url}">View More</a>
                            </div>
                        </div>
                    </section>
                </#list>
                </div>
            </section>

            <section id="about-section" data-scroll-anchor="about">
                <h4>The Story</h4>
                <h2>It's time to add the direction</h2>

                <p>Knitting has been a major hobby of mine since childhood, but it has still remained just a hobby - in
                    spite of such a constancy, I have never seriously considered the possibility of turning my interest
                    into a business. But it is turned out that not finding a satisfaction in other fields of work, I
                    went back to my childhood fancies - that have always been a truly inspiration to me. Whether there
                    were dolls clothes or experiments with my own looks and moods, every time the creative process
                    swallowed me completely, making forget about time during turning my imagination into reality. Now it
                    is time not only to put all this into shape, but also add the direction.</p>

                <p>I value coziness, comfort, natural yarn and mostly gravitate towards warm autumn colors, however,
                    this does not keep me from experiments with cold tones.</p>

                <p>I hardly ever use the ready made knitting schemes and patterns. Basically, I just put my ideas into
                    action: thoroughly calculate the stitches and rows, modify some details and see what happens then. I
                    do my best to keep the quality of my work high and I am proud of it. But even more I am proud of
                    sharing with you the results of my efforts :)</p>
            </section>

            <section id="shops-section" data-scroll-anchor="shops">
                <h2>Buy from katoonyaka</h2>

                <div class="shop-container">
                    <div class="shop-logo">
                        <a href="http://en.dawanda.com/shop/katoonyaka" target="_blank">
                        <@img imageUuid="0fff289c-ec33-4a56-acae-c5479c78d279" alt="katoonyaka on Dawanda"/>
                        </a>
                    </div>
                    <div class="shop-description">
                        DaWanda is the online marketplace with heart and soul.
                        <br/>
                        People who share a passion for unique and creative products, made with love, come here to meet.
                        <br/>
                        <a href="http://en.dawanda.com/shop/katoonyaka" target="_blank">Visit our shop on DaWanda.</a>
                    </div>
                </div>

                <div class="shop-container">
                    <div class="shop-logo">
                        <a href="https://www.etsy.com/shop/katoonyaka" target="_blank">
                            <@img imageUuid="e8833bda-d7c3-413c-86db-310bf9220003" alt="katoonyaka on Etsy"/>
                        </a>
                    </div>
                    <div class="shop-description">
                        Etsy is a marketplace where people around the world connect, both online and offline, to make, sell and buy unique goods.
                        <br/>
                        <a href="https://www.etsy.com/shop/katoonyaka" target="_blank">Visit our shop on Etsy.</a>
                    </div>
                </div>

            </section>

            <#include "copyright.ftl"/>
        </div>

      <script type="text/javascript">
        var mainJs = "/static/js/katoonyaka.home-${katoonyakaVersion}.min.js";
      </script>
      <#include "scripts-common.ftl"/>
    </body>
</html>




