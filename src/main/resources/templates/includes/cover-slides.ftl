<div class="cover-slides" data-katoonyaka-cover-slides>
<#if !covers?has_content>
    <div class="cover-slide hidden" data-photo="e5d3142d-c45b-46ed-835f-9881a9b20e93"></div>
<#else>
    <#list covers as cover>
        <div class="cover-slide hidden" data-photo="${cover.photo.externalId}"></div>
    </#list>
</#if>
</div>