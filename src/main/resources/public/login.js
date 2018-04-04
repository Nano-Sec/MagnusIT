(function (){
    var app=angular.module('myapp');
    app.controller('ctrl',ctrl);
    app.factory('AuthInterceptor', [function() {
        return {
            'request': function(config) {
                config.headers = config.headers || {};
                var encodedString = btoa(userName+":"+password);
                config.headers.Authorization = 'Basic '+encodedString;
               return config;
            }
        };
    }]);
    app.config(['$httpProvider', function($httpProvider) {
        $httpProvider.interceptors.push('AuthInterceptor');
    }]);
    function ctrl (){
    	var main= this;
    	main.test= function(){
    		alert('dgfdg');
    	}
    }
})();