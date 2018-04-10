angular.module('myapp').controller('IssueController', ['$stateParams','$state','IssueService', function($stateParams,$state,IssueService) {
    var self = this;
    self.issueNumber=Math.floor(Math.random() * 10000);
    self.postissue={'issueNumber':self.issueNumber,'project':{'id': ''},'reporter':{'employeeNumber': 1},'category':self.category,'title':'','description':''};
    self.postcomment={'issue':self.currentIssue,'user':{'employeeNumber': 1},'body':''};
    self.currentIssue;
    self.issues=[];
    self.comments=[];
    self.commentflag= false;

    self.setCommentFlag= function setCommentFlag(){
        self.commentflag=true;
    }

    self.cancelComment= function cancelComment(){
        self.commentflag=false;
    }

    self.goToIssue= function(id){
        IssueService.setCurrentIssue(id);
        $state.go('home.viewIssue');
    }
 
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

    self.fetchAllComments = function fetchAllComments(number){
        IssueService.fetchAllComments(number)
            .then(
            function(data) {
                self.comments= data;
            },
            function(errResponse){
                console.error('Error while fetching comments');
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

    self.createComment = function createComment(){
        IssueService.createComment(self.postcomment)
            .then(
                self.fetchAllComments(self.currentIssue.issueNumber),
            function(errResponse){
                console.error('Error while posting comment');
            }
        );
    }
 
    self.fetchIssue = function fetchIssue(issueId){
        IssueService.fetchIssue(issueId)
            .then(
                function(data){
                    self.currentIssue= data;
                },
                function(errResponse){
                    console.error('Error while fetching issue: '+issueId);
                }
            );
    }

    if(IssueService.getCurrentIssue()!=null){
        self.fetchIssue(IssueService.getCurrentIssue());
    }
    if(self.currentIssue!=null){
        self.fetchAllComments(self.currentIssue.issueNumber);
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