angular.module('myapp').factory('EmployeeService', ['$http', '$q', function($http, $q){

    var factory = {
        fetchAllEmployees: fetchAllEmployees,
        fetchEmployee: fetchEmployee,
        fetchEmployeeEssentials: fetchEmployeeEssentials,
        fetchLoggedEmployee: fetchLoggedEmployee,
        createEmployee: createEmployee,
        updateEmployee: updateEmployee,
        deleteEmployee: deleteEmployee
    };

    return factory;

    function createEmployee(emp) {
        var deferred = $q.defer();
        $http.post('admin/insert/', emp)
            .then(
                function (response) {
                    deferred.resolve("Employee posted");
                },
                function(errResponse){
                    console.error('Error while creating Employee');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function fetchAllEmployees() {
        var deferred = $q.defer();
        $http.get('admin/all/')
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Employees');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function fetchEmployee(empId) {
        var deferred = $q.defer();
        $http.get('admin/view/'+empId)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Employee with Id: '+empId);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function fetchEmployeeEssentials(list) {
        var deferred = $q.defer();
        $http({url:'employee/viewEssentials',
            method: "GET",
            params:{List:list}})
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching employee essentials');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function fetchLoggedEmployee() {
        var deferred = $q.defer();
        $http.get('employee/view/')
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching logged in employee');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function updateEmployee(emp) {
        var deferred= $q.defer();
        $http.put('admin/update/', emp)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while updating Employee');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteEmployee(number) {
        var deferred = $q.defer();
        $http.delete('admin/delete/'+number)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while deleting Employee');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);