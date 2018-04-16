angular.module('myapp').factory('IssueService', ['$http', '$q', function($http, $q){
    
    // var currentIssue;
    var factory = {
        fetchAllIssues: fetchAllIssues,
        fetchAllComments: fetchAllComments,
        fetchIssue: fetchIssue,
        createIssue: createIssue,
        createComment: createComment,
        updateIssue: updateIssue,
        deleteIssue: deleteIssue
        // fetchUserProjects: fetchUserProjects,
        // fetchIssueCategories: fetchIssueCategories
    };
 
    return factory;
 
    function createIssue(issue) {
        var deferred = $q.defer();
        $http.post('issue/', issue)
            .then(
            function (response) {
                deferred.resolve("issue posted");
            },
            function(errResponse){
                console.error('Error while creating Issue');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function createComment(comment) {
        var deferred = $q.defer();
        $http.post('comment/', comment)
            .then(
            function (response) {
                deferred.resolve("comment posted");
            },
            function(errResponse){
                console.error('Error while posting comment');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function fetchAllIssues() {
        var deferred = $q.defer();
        $http.get('issues/')
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Issues');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function fetchAllComments(number) {
        var deferred = $q.defer();
        $http.get('comments/'+ number)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching comments');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
    function fetchIssue(issueId) {
        var deferred = $q.defer();
        $http.get('issue/'+issueId)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Issue with Id: '+issueId);
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function updateIssue(issue, description) {
        var deferred= $q.defer();
        $http.put('update/', issue, {params: {description: description}})
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while updating Issue');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteIssue(number) {
        var deferred = $q.defer();
        $http.delete('delete/'+number)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while deleting Issue');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
 
    // function fetchUserProjects() {
    //     var deferred = $q.defer();
    //     $http.get('localhost8080/')
    //         .then(
    //         function (response) {
    //             deferred.resolve(response.data);
    //         },
    //         function(errResponse){
    //             console.error('Error while fetching projects for user Id: '+id);
    //             deferred.reject(errResponse);
    //         }
    //     );
    //     return deferred.promise;
    // }

    // function fetchIssueCategories() {
    //     var deferred = $q.defer();
    //     $http.get('')
    //         .then(
    //         function (response) {
    //             deferred.resolve(response.data);
    //         },
    //         function(errResponse){
    //             console.error('Error while fetching Issue categories');
    //             deferred.reject(errResponse);
    //         }
    //     );
    //     return deferred.promise;
    // }
 
}]);