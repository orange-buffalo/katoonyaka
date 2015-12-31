<div>
    <section itemscope itemtype="http://schema.org/Product">
        <div class="handiwork-description-container">
            <h1 itemprop="name">${handiwork.name}</h1>

            <div itemprop="description" class="handiwork-description-area">
            <#list handiwork.sections as section>
                <div class="handiwork-description-tile<#if section_has_next><#else> end</#if>">
                    <h2>${section.header!"..."}</h2>
                ${section.content!"..."}
                </div>
            </#list>
            </div>
        </div>

        <div class="handiwork-links">
        <#if handiwork.etsyUrl?has_content>
            <a class="icon-etsy" href="${handiwork.etsyUrl}" target="_blank">Buy on Etsy</a>
        </#if>
        <#if handiwork.dawandaUrl?has_content>
            <a class="icon-dawanda" href="${handiwork.dawandaUrl}" target="_blank">Buy on DaWanda</a>
        </#if>
        </div>

        <div class="gamma-container gamma-loading" itemscope itemtype="http://schema.org/ImageGallery">
            <ul class="gamma-gallery">
            <#list handiwork.photos as photo>
                <li itemprop="associatedMedia" itemscope itemtype="MediaObject">
                    <div data-max-width="1800" data-max-height="2400">
                        <#list handiwork.photoSources as photoSource>
                            <div data-src="/photos/${handiwork.url}/${photo.id}/${photoSource.width?c}"
                                 data-min-width="${photoSource.minWidth?c}"
                                 data-width="${photoSource.width?c}"
                                 data-height="${(photo.height * photoSource.width / photo.width)?round?c}"></div>
                        </#list>
                        <noscript>
                            <img src="/photos/${handiwork.url}.${photo.id}.jpeg"
                                 alt="${handiwork.name}"
                                 itemprop="http://schema.org/image"/>
                        </noscript>
                    </div>
                </li>
            </#list>
            </ul>
        </div>

    <#include "pswp.ftl"/>
    </section>

</div>
