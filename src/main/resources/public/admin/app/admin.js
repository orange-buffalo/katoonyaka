function _initKatoonyakaAdmin(NavigationService) {
    NavigationService.init();
}

function _configKatoonyakaAdmin($routeProvider, $mdIconProvider, $httpProvider,
                                $mdThemingProvider, RestangularProvider) {
    $routeProvider.
        when('/handiworks', {
            templateUrl: '/admin/partials/handiworks.html',
            controller: 'HandiworksController'
        }).
        when('/handiworks/:handiworkId/view', {
            templateUrl: '/admin/partials/handiwork.html',
            controller: 'HandiworkController'
        }).
        when('/handiworks/:handiworkId/edit', {
            templateUrl: '/admin/partials/edit-handiwork.html',
            controller: 'EditHandiworkController'
        }).
        when('/handiworks/create', {
            templateUrl: '/admin/partials/edit-handiwork.html',
            controller: 'EditHandiworkController'
        }).
        when('/covers', {
            templateUrl: '/admin/partials/covers.html',
            controller: 'CoversController'
        }).
        when('/covers/:coverId/edit', {
            templateUrl: '/admin/partials/edit-cover.html',
            controller: 'EditCoverController'
        }).
        when('/covers/create', {
            templateUrl: '/admin/partials/edit-cover.html',
            controller: 'EditCoverController'
        }).
        otherwise({
            redirectTo: '/handiworks'
        });

    $mdIconProvider
        .icon('menu-button', '/admin/img/more-vert.svg', 24)
        .icon('edit-button', '/admin/img/pencil.svg', 24)
        .icon('delete-button', '/admin/img/trashcan.svg', 24)
        .icon('publish-button', '/admin/img/bullhorn.svg', 24)
        .icon('handiwork-button', '/admin/img/heart.svg', 24)
        .icon('cover-button', '/admin/img/images.svg', 24)
        .icon('mainmenu-button', '/admin/img/mainmenu-button.svg', 24)
        .icon('info', '/admin/img/info.svg', 24)
        .icon('error', '/admin/img/error.svg', 24)
        .icon('success', '/admin/img/success.svg', 24)
        .icon('warning', '/admin/img/warn.svg', 24)
        .icon('draftify-button', '/admin/img/quill.svg', 24)
        .icon('open', '/admin/img/compass.svg', 24);

    $httpProvider.interceptors.push('HttpErrorHandler');

    $mdThemingProvider.theme('default')
        .primaryPalette('teal')
        .accentPalette('orange');

    RestangularProvider.setBaseUrl("/admin/api/");
    RestangularProvider.setDefaultHttpFields({cache: false});
}

var controllers = {
    HandiworksController: HandiworksController,
    HandiworkController: HandiworkController,
    EditHandiworkController: EditHandiworkController,
    CoversController: CoversController,
    NavigationController: NavigationController
};

var factories = {
    NavigationService: NavigationService,
    HttpErrorHandler: HttpErrorHandler,
    Cover: CoverService,
    Handiwork: HandiworkService,
    ServicesFacade: ServicesFacade,
    NotificationService: NotificationService,
    ConfigService: ConfigService,
    UploadService: UploadService
};

angular.module('katoonyakaAdminApp',
        ['ngMaterial', 'ngRoute', 'ngResource', 'blockUI', 'ngMessages', 'dndLists',
            'ngSanitize', 'restangular', 'ngFileUpload']
    )
    .filter('nl2br', function() {
        return function (text) {
            return text ? text.replace(/\n/g, '<br>') : '';
        }
    })
    .config(_configKatoonyakaAdmin)
    .factory(factories)
    .controller(controllers)
    .run(_initKatoonyakaAdmin);
