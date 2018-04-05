(function (){
    //ui.router and not ui-router
    var app=angular.module('myapp',['ui.router']);
    app.config(Routesconfig);

    //inject manually for minified versions to work
    Routesconfig.$inject=['$stateProvider','$urlRouterProvider'];

    function Routesconfig($stateProvider,$urlRouterProvider){
        //set default url to uiroute to avoid 404 errors
        $urlRouterProvider
        .when('','login')
        .when('/','login')
        .otherwise(function($injector, $location){
            var state= $injector.get('$state');
            state.go('404', { url: $location.path() }, { location: true });
        });

        //define states
        $stateProvider
        .state('/',{
            templateUrl: 'login.html'
        })
        .state('login',{
            url: '/login',
            templateUrl: 'login.html'
        })
        .state('home',{
            url: '/home',
            templateUrl: '/views/home.html'
        })
        .state('404',{
            url: '/error/404',
            templateUrl: 'error.html'
        })
    }

})();