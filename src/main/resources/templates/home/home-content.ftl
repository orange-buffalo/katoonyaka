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
       data-base-url="/photos/${handiwork.url}/${handiwork.cover.id}/"
       data-width="${handiwork.cover.width?c}"
       data-height="${handiwork.cover.height?c}"
       class="handiwork-summary">
        <div class="handiwork-summary-container">
            <img class="handiwork-thumb"/>
            <div class="handiwork-description">
                <div class="handiwork-description-wrapper">
                    <h3>${handiwork.name}</h3>
                    <hr/>
                    <p>${handiwork.summary!"..."}</p>
                </div>
            </div>
        </div>
    </a>
</#list>
</div>