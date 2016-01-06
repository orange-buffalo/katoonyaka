<a href="/" data-katoonyaka-internal-link>back to list</a>
<#if model.previousHandiwork?has_content>
<a href="/portfolio/${model.previousHandiwork.url}" data-katoonyaka-internal-link>prev</a>
</#if>
<#if model.nextHandiwork?has_content>
<a href="/portfolio/${model.nextHandiwork.url}" data-katoonyaka-internal-link>next</a>
</#if>