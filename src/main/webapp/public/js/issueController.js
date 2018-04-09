angular.module('myapp').controller('IssueController', ['IssueService', function(IssueService) {
    var self = this;
    self.issueNumber=Math.floor(Math.random() * 10000);
    self.postissue={'issueNumber':self.issueNumber,'project':{'id': ''},'reporter':{'employeeNumber': 1},'category':self.category,'title':'','description':''};
    self.issues=[];
 
    self.fetchAllIssues = function fetchAllIssues(){
        IssueService.fetchAllIssues()
            .then(
            function(data) {
                self.issues= data;
            },
            function(errResponse){
                console.error('Error while fetching Issues');
            }
        );
    }

    self.fetchAllIssues();

    self.createIssue = function createIssue(){
        IssueService.createIssue(self.postissue)
            .then(
                self.fetchAllIssues,
            function(errResponse){
                console.error('Error while creating Issue');
            }
        );
    }
 
    self.fetchIssue = function fetchIssue(issueId){
        IssueService.fetchIssue(issueId)
            .then(
                function(data){
                    console.log(JSON.stringify(data));
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