angular.module('myapp').controller('ProjectController', ['$window','$state','ProjectService', function($window,$state,ProjectService) {
    var self = this;
    self.projectNo= Math.floor(Math.random() * 10000);
    self.postProject={
        "id": '',
        "projectNo": self.projectNo,
        "name": '',
        "isPublic": '',
        "description": ''
    };
    self.postProject.projectNo= Math.floor(Math.random() * 10000);
    self.putProject={};
    self.projects=[];

    self.fetchAllProjects = function fetchAllProjects(){
        ProjectService.fetchAllProjects()
            .then(
                function(data) {
                    self.projects= data;
                },
                function(errResponse){
                    console.error('Error while fetching Projects');
                }
            );
    };

    self.fetchAllProjects();

    self.fetchProject = function fetchProject(projectId){
        ProjectService.fetchProject(projectId)
            .then(
                function(data){
                    self.currentProject= data;
                },
                function(errResponse){
                    console.error('Error while fetching project: '+projectId);
                }
            );
    };

    if(localStorage.getItem("projectNumber")!=null && localStorage.getItem("projectNumber")!= undefined &&
        $state.current.name=='home.viewProject'){
        self.fetchProject(localStorage.getItem("projectNumber"));
    }

    self.createProject = function createProject(){
        ProjectService.createProject(self.postProject)
            .then(
                self.fetchAllProjects,
                function(errResponse){
                    console.error('Error while creating Project');
                }
            );
        alert('project created successfully');
        $state.go("home.dashboard");
    };

    self.updateProject= function(){
        ProjectService.updateProject(self.putProject)
            .then(
                self.fetchAllProjects,
                function(errResponse){
                    console.error('Error while updating Project');
                }
            );
        alert('Project successfully updated');
        $state.go('home.viewProject');
        $window.location.reload();
    };

    self.deleteProject= function(){
        var check= confirm('Are you sure you want to delete this project?');
        if(check==true) {
            ProjectService.deleteProject(self.currentProject.id)
                .then(
                    self.fetchAllProjects,
                    function (errResponse) {
                        console.error('Error while deleting Project');
                    }
                );
            localStorage.setItem("projectNumber", null);
            $state.go('home.projectList');
        }
    };

    self.goToProject= function(id){
        localStorage.setItem("projectNumber",id);
        $state.go('home.viewProject');
    };

    self.goToUpdateProject= function(id){
        localStorage.setItem("projectNumber",id);
        $state.go('home.updateProject');
    };
}]);