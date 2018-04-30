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
            templateUrl: 'public/login.html'
        })
        .state('login',{
            url: '/login',
            templateUrl: 'public/login.html'
        })
        .state('home',{
            abstract: true,
            url: '/home',
            templateUrl: 'views/home.html'
        })
        .state('home.dashboard',{
            url: '/dashboard',
            templateUrl: 'views/dashboard.html'
        })
        .state('home.createIssue',{
            url: '/createIssue',
            templateUrl: 'views/createIssue.html'
        })
        .state('home.viewIssueList',{
            url: '/IssueList',
            templateUrl: 'views/issueList.html'
        })
        .state('home.viewIssue',{
            url: '/Issue',
            templateUrl: 'views/viewIssue.html'
        })
        .state('home.updateIssue',{
            url: '/Issue/update',
            templateUrl: 'views/updateIssue.html'
        })
        .state('home.userList',{
            url: '/Users/view',
            templateUrl: 'views/userList.html'
        })
        .state('home.viewEmployee',{
            url: '/Users/view/employee',
            templateUrl: 'views/viewUser.html'
        })
        .state('home.projectList',{
            url: '/projects/view',
            templateUrl: 'views/projectList.html'
        })
        .state('home.viewProject',{
            url: '/project/view',
            templateUrl: 'views/viewProject.html'
        })
        .state('404',{
            url: '/error/404',
            templateUrl: 'error.html'
        })
    }

})();