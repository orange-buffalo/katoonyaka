<section id="header-cover" data-scroll-anchor="home">
                <div class="cover-slider">
                <#if !covers?has_content>
                    <div class="cover-slide"
                         style="background-image: url('http://www.ucarecdn.com/e5d3142d-c45b-46ed-835f-9881a9b20e93/')">
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