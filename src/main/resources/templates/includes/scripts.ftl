<script type="text/javascript">
  function loadScript(e,t){var a=document.createElement("script");a.type="text/javascript",a.async=!0,a.readyState?a.onreadystatechange=function(){("loaded"==a.readyState||"complete"==a.readyState)&&(a.onreadystatechange=null,t&&"function"==typeof t&&t())}:a.onload=function(){t&&"function"==typeof t&&t()},a.src=e,(document.getElementsByTagName("head")[0]||document.getElementsByTagName("body")[0]).appendChild(a)}

  var PHOTO_SIZES = [<#list photoSizesConfig.sizes as photoSize>{name:'${photoSize.name}',width:${photoSize.widthInPx?c}},</#list>];

  loadScript("http://code.jquery.com/jquery-1.11.3.min.js", function () {
      loadScript("https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0-rc.0/angular.min.js", function () {
          loadScript("/static/js/katoonyaka.${katoonyakaVersion}.min.js");
      });
  });
</script>