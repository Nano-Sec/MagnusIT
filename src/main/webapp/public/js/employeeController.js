angular.module('myapp').controller('EmployeeController', ['$window','$state','EmployeeService', function($window,$state,EmployeeService) {
    var self = this;
    self.postEmployee={};
    self.putEmployee={};
    self.employees=[];

    self.fetchAllEmployees = function fetchAllEmployees(){
        EmployeeService.fetchAllEmployees()
            .then(
                function(data) {
                    self.employees= data;
                },
                function(errResponse){
                    console.error('Error while fetching Employees');
                }
            );
    };

    self.fetchAllEmployees();

    self.fetchEmployee = function fetchEmployee(empId){
        EmployeeService.fetchEmployee(empId)
            .then(
                function(data){
                    self.currentEmployee= data;
                },
                function(errResponse){
                    console.error('Error while fetching employee: '+empId);
                }
            );
    };

    self.fetchLoggedEmployee = function fetchLoggedEmployee(empId){
        EmployeeService.fetchLoggedEmployee(empId)
            .then(
                function(data){
                    self.currentEmployee= data;
                },
                function(errResponse){
                    console.error('Error while fetching logged in employee');
                }
            );
    };

    if(localStorage.getItem("empNumber")!=null){
        self.fetchEmployee(localStorage.getItem("empNumber"));
    }

    self.createEmployee = function createEmployee(){
        EmployeeService.createEmployee(self.postEmployee)
            .then(
                self.fetchAllEmployees,
                function(errResponse){
                    console.error('Error while creating Employee');
                }
            );
        $state.go("home.dashboard");
    };

    self.updateEmployee= function(description){
        EmployeeService.updateEmployee(self.putEmployee, description)
            .then(
                self.fetchAllEmployees,
                function(errResponse){
                    console.error('Error while updating Employee');
                }
            );
        alert('Employee successfully updated');
        $state.go('home.viewEmployee');
        $window.location.reload();
    };

    self.deleteEmployee= function(){
        var check= confirm('Are you sure you want to delete this employee?');
        if(check==true) {
            EmployeeService.deleteEmployee(self.currentEmployee.employeeNumber)
                .then(
                    self.fetchAllEmployees,
                    function (errResponse) {
                        console.error('Error while deleting Employee');
                    }
                );

            localStorage.setItem("empNumber", null);
            $state.go('home.viewEmployeeList');
        }
    };

    self.goToEmployee= function(id){
        localStorage.setItem("empNumber",id);
        $state.go('home.viewEmployee');
    };

    self.goToUpdateEmployee= function(id){
        localStorage.setItem("empNumber",id);
        $state.go('home.updateEmployee');
    };
}]);