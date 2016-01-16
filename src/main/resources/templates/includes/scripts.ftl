<script type="text/javascript">
  function loadScript(url, callback) {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.async = true;
    if (script.readyState) {
      script.onreadystatechange = function () {
        if (script.readyState == "loaded" || script.readyState == "complete") {
          script.onreadystatechange = null;
          if (callback && typeof callback === "function") {
            callback();
          }
        }
      };
    } else {
      script.onload = function () {
        if (callback && typeof callback === "function") {
          callback();
        }
      };
    }
    script.src = url;
    (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(script);
  }

  loadScript("http://code.jquery.com/jquery-1.11.3.min.js", function () {
      loadScript("https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0-rc.0/angular.min.js", function () {
          loadScript("/static/js/katoonyaka.${katoonyakaVersion}.min.js");
      });
  });
</script>