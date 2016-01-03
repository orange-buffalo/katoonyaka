<#macro img imageUuid alt defaultWidth=800>
<img src="http://www.ucarecdn.com/${imageUuid}/-/resize/${defaultWidth}x/"
     srcset="http://www.ucarecdn.com/${imageUuid}/-/resize/100x/ 100w,
     http://www.ucarecdn.com/${imageUuid}/-/resize/100x/ 100w,
     http://www.ucarecdn.com/${imageUuid}/-/resize/200x/ 200w,
     http://www.ucarecdn.com/${imageUuid}/-/resize/400x/ 400w,
     http://www.ucarecdn.com/${imageUuid}/-/resize/600x/ 600w,
     http://www.ucarecdn.com/${imageUuid}/-/resize/1000x/ 1000w,
     http://www.ucarecdn.com/${imageUuid}/-/resize/1200x/ 1200w,
     http://www.ucarecdn.com/${imageUuid}/-/resize/1500x/ 1500w"
     alt="${alt}"/>
</#macro>