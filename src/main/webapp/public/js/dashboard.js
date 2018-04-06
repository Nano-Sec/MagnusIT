(function(){
    var app=angular.module('myapp');
    app.controller('dashController',dashController);

    function dashController($state){
        var self= this;
        self.newIssue= function(){
            $state.go('home.createIssue');
        }
    }
})();