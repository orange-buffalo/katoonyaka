<article itemscope
         itemtype="http://schema.org/Product"
         class="handiwork-details">
    <div class="text-content">
        <h1 itemprop="name">${model.handiwork.name}</h1>

        <div itemprop="description" class="handiwork-description-area">
        <#list model.handiwork.sections as section>
            <section class="handiwork-description-part">
                <h2>${section.header!"..."}</h2>
                ${section.content!"..."}
            </section>
        </#list>
        </div>

        <div class="handiwork-links">
        <#if model.handiwork.etsyUrl?has_content>
            <a class="icon-etsy" href="${model.handiwork.etsyUrl}" target="_blank">Buy on Etsy</a>
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
               data-base-url="/photos/${model.handiwork.url}.${photo.id}${photoSizesConfig.nameSeparator}"
               data-width="${photo.width?c}"
               data-height="${photo.height?c}"
               class="handiwork-photo">
                <img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"
                     alt="${model.handiwork.name}"
                     class="handiwork-photo-thumb"/>
            </div>
        </#list>
        </div>
    </div>
</article>