<section itemscope itemtype="http://schema.org/Product" class="handiwork-details">
    <div class="text-content">
        <h1 itemprop="name">${model.handiwork.name}</h1>

        <div itemprop="description" class="handiwork-description-area">
        <#list model.handiwork.sections as section>
            <div class="handiwork-description-part<#if section_has_next><#else> end</#if>">
                <h2>${section.header!"..."}</h2>
            ${section.content!"..."}
            </div>
        </#list>
        </div>

        <div class="handiwork-links">
        <#if model.handiwork.etsyUrl?has_content>
            <a class="icon-etsy" href="${model.handiwork.etsyUrl}" target="_blank">Buy on Etsy</a><br/>
        </#if>
        <#if model.handiwork.dawandaUrl?has_content>
            <a class="icon-dawanda" href="${model.handiwork.dawandaUrl}" target="_blank">Buy on DaWanda</a>
        </#if>
        </div>
    </div>

    <div class="photo-content" itemscope itemtype="http://schema.org/ImageGallery">
        <div class="handiwork-photo-gallery"
             data-katoonyaka-justified-gallery
             data-row-height-factor="0.6"
             data-item-container-class="handiwork-photo"
             data-item-thumb-class="handiwork-photo-thumb">
        <#list model.handiwork.photos as photo>
            <div
               data-ng-non-bindable
               data-katoonyaka-handiwork-photo
               data-base-url="/photos/${model.handiwork.url}/${photo.id}/"
               data-width="${photo.width?c}"
               data-height="${photo.height?c}"
               class="handiwork-photo">
                <img class="handiwork-photo-thumb"/>
            </div>
        </#list>
        </div>

        <#--todo: check if we need noscript-->
        <#--<ul class="gamma-gallery">-->
        <#--<#list handiwork.photos as photo>-->
            <#--<li itemprop="associatedMedia" itemscope itemtype="MediaObject">-->
                <#--<div data-max-width="1800" data-max-height="2400">-->
                    <#--<#list handiwork.photoSources as photoSource>-->
                        <#--<div data-src="/photos/${handiwork.url}/${photo.id}/${photoSource.width?c}"-->
                             <#--data-min-width="${photoSource.minWidth?c}"-->
                             <#--data-width="${photoSource.width?c}"-->
                             <#--data-height="${(photo.height * photoSource.width / photo.width)?round?c}"></div>-->
                    <#--</#list>-->
                    <#--<noscript>-->
                        <#--<img src="/photos/${handiwork.url}.${photo.id}.jpeg"-->
                             <#--alt="${handiwork.name}"-->
                             <#--itemprop="http://schema.org/image"/>-->
                    <#--</noscript>-->
                <#--</div>-->
            <#--</li>-->
        <#--</#list>-->
        <#--</ul>-->


    </div>

</section>