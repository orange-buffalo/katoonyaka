var KatoonyakaController = function($scope) {
    console.log("inited");

};

function _initKatoonyakaAdmin() {
   console.log("inited fro func");

}

var controllers = {
    KatoonyakaController: KatoonyakaController
};

angular.module('katoonyaka',
        []
    )
    //.filter('nl2br', function() {
    //    return function (text) {
    //        return text ? text.replace(/\n/g, '<br>') : '';
    //    }
    //})
    //.directive('uploadcareWidget', UploadcareWidget)
    //.config(_configKatoonyakaAdmin)
    //.factory(factories)
    .controller(controllers)
    .run(_initKatoonyakaAdmin)


.directive("katoonyakaHandiworksList", ["$compile", KatoonyakaHandiworksList])
.directive("katoonyakaScroll", ["$compile", KatoonyakaScroll])
.directive("katoonyakaHandiworkSummary", [KatoonyakaHandiworkSummary])


;
