angular.module('myapp').factory('ProjectService', ['$http', '$q', function($http, $q){

    var factory = {
        fetchAllProjects: fetchAllProjects,
        fetchProject: fetchProject,
        createProject: createProject,
        updateProject: updateProject,
        deleteProject: deleteProject
    };

    return factory;

    function createProject(project) {
        var deferred = $q.defer();
        $http.post('project/add/', project)
            .then(
                function (response) {
                    deferred.resolve("Project posted");
                },
                function(errResponse){
                    console.error('Error while creating Project');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function fetchAllProjects() {
        var deferred = $q.defer();
        $http.get('project/view/all/')
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Projects');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function fetchProject(projectId) {
        var deferred = $q.defer();
        $http.get('project/view/'+projectId)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Project with Id: '+projectId);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function updateProject(project) {
        var deferred= $q.defer();
        $http.put('project/modify/', project)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while updating Project');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteProject(number) {
        var deferred = $q.defer();
        $http.delete('project/remove/'+number)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while deleting Project');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);