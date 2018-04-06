angular.module('myapp').controller('IssueController', ['$scope', 'IssueService', function($scope, IssueService) {
    var self = this;
    self.issueNumber=Math.floor(Math.random() * 1000);
    self.issue={'issueNumber':self.issueNumber,'project':{'id': ''},'reporter':{'employeeNumber': 1},'category':self.category,'title':'','description':''};
    self.issues=[];

    self.createIssue = function createIssue(){
        IssueService.createIssue(self.issue)
            .then(
                console.log('Issue created.'),
            function(errResponse){
                console.error('Error while creating Issue');
            }
        );
    }
 
    self.fetchAllIssues = function fetchAllIssues(){
        IssueService.fetchAllIssues()
            .then(
            function(data) {
                self.issues = data;
            },
            function(errResponse){
                console.error('Error while fetching Issues');
            }
        );
    }
 
    self.fetchIssue = function fetchIssue(issueId){
        IssueService.fetchIssue(issueId)
            .then(
                function(data){
                    self.issue = data;
                },
                function(errResponse){
                    console.error('Error while fetching issue: '+issueId);
                }
            );
    }

    // function fetchUserProjects(id){
    //     IssueService.fetchUserProjects(id)
    //     .then(
    //         function(data){
    //             self.project = data;
    //         },
    //         function(errResponse){
    //             console.error('Error while fetching projects for userId: '+id);
    //         }
    //     );
    // }

    // function fetchIssueCategories(){
    //     IssueService.fetchIssueCategories()
    //     .then(
    //         function(data){
    //             self.category = data;
    //         },
    //         function(errResponse){
    //             console.error('Error in fetching categories');
    //         }
    //     );
    // }
 
}]);