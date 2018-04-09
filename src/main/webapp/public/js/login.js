(function (){
    var app=angular.module('myapp');
    
    app.controller('login',

    function($rootScope, $scope, $http, $state) {

    var authenticate = function(credentials, callback) {

        var headers = credentials ? {authorization : "Basic "
            + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('/user', {headers : headers})
        .then(function(response) {
        if (response.data) {
            $rootScope.user= response.data;
            $rootScope.authenticated = true;
        } else {
            $rootScope.authenticated = false;
        }
        callback && callback();
        })
        .catch(function() {
        $rootScope.authenticated = false;
        callback && callback();
        });

    }

    $scope.credentials = {};
    $scope.auth = function() {
        authenticate($scope.credentials, function() {
            if ($rootScope.authenticated) {
            $state.go("home");
            $scope.error = false;
            } else {
            $state.go("login");
            $scope.error = true;
            }
        });
    };
    });
})();