angular.module('myapp').controller('EmployeeController', ['$window','$state','EmployeeService', function($window,$state,EmployeeService) {
    var self = this;
    self.employeeNo= Math.floor(Math.random() * 10000);
    self.postEmployee={
        "empNo": self.employeeNo,
        "employeeName": "",
        "email": "",
        "username": "",
        "password":'',
        "dateOfJoining": '',
        "userRoles": []
    };
    self.putEmployee={};
    self.employees=[];
    self.roles=[];

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
                    self.currentEmployee.dateOfJoining= new Date(self.currentEmployee.dateOfJoining);
                    for(var i=0;i<self.currentEmployee.userRoles.length;i++){
                        if(self.currentEmployee.userRoles[i].roleType=="ADMIN")
                            self.admin="ADMIN";
                        else if(self.currentEmployee.userRoles[i].roleType=="EMPLOYEE")
                            self.employee="EMPLOYEE";
                    }
                },
                function(errResponse){
                    console.error('Error while fetching employee: '+empId);
                }
            );
    };

    self.fetchEmployeeEssentials = function fetchEmployeeEssentials(list){
        EmployeeService.fetchEmployeeEssentials(list)
            .then(
                function(data){

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

    if(localStorage.getItem("empNumber")!=null && $state.current.name=='home.viewEmployee'){
        self.fetchEmployee(localStorage.getItem("empNumber"));
    }

    self.createEmployee = function createEmployee(){
        if(self.admin==null && self.employee==null){
            alert('Please select a roletype');
        }
        else{
            if(self.admin!=null)
                self.postEmployee.userRoles.push({"roleType":"ADMIN"});
            if(self.employee!=null)
                self.postEmployee.userRoles.push({"roleType":"EMPLOYEE"});
            EmployeeService.createEmployee(self.postEmployee)
                .then(
                    self.fetchAllEmployees,
                    function(errResponse){
                        console.error('Error while creating Employee');
                    }
                );
            alert('employee created successfully');
            $state.go("home.dashboard");
        }
    };

    self.updateEmployee= function(){
        if(self.admin==null && self.employee==null){
            alert('Please select a roletype');
        }
        else {
            if(self.admin!=null)
                self.roles.push({"roleType":"ADMIN"});
            if(self.employee!=null)
                self.roles.push({"roleType":"EMPLOYEE"});
            self.currentEmployee.userRoles=self.roles;
            delete self.currentEmployee.createdBy;
            delete self.currentEmployee.creationDate;
            delete self.currentEmployee.lastModifiedBy;
            delete self.currentEmployee.lastModifiedDate;
            EmployeeService.updateEmployee(self.currentEmployee)
                .then(
                    self.fetchAllEmployees,
                    function (errResponse) {
                        console.error('Error while updating Employee');
                    }
                );
            self.fetchEmployee(localStorage.getItem("empNumber"));
            alert('Employee successfully updated');
            self.admin='';
            self.employee='';
            $state.go('home.viewEmployee');
            $window.location.reload();
        }
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
            $state.go('home.userList');
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