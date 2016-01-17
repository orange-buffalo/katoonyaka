<section>
    <h1>Portfolio</h1>
    <div id="handiwork-list"
         class="handiworks-list"
         data-katoonyaka-justified-gallery
         data-item-container-class="handiwork-summary"
         data-item-thumb-class="handiwork-thumb">
    <#list model.handiworks as handiwork>
        <a href="/portfolio/${handiwork.url}"
           data-ng-non-bindable
           data-katoonyaka-handiwork-summary
           data-katoonyaka-internal-link
           data-base-url="/photos/${handiwork.url}.${handiwork.cover.id}"
           data-width="${handiwork.cover.width?c}"
           data-height="${handiwork.cover.height?c}"
           class="handiwork-summary">
            <article class="handiwork-summary-container">
                <img class="handiwork-thumb"
                     src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"
                     alt="${handiwork.name}"/>
                <div class="handiwork-description">
                    <div class="handiwork-description-wrapper">
                        <h2>${handiwork.name}</h2>
                        <hr/>
                        <p>${handiwork.summary!""}</p>
                    </div>
                </div>
            </article>
        </a>
    </#list>
    </div>
</section>