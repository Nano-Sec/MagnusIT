angular.module('myapp').controller('IssueController', ['$window','$state','IssueService', function($window,$state,IssueService) {
    var self = this;
    self.issueNumber=Math.floor(Math.random() * 10000);
    self.postissue={'issueNumber':self.issueNumber,'project':{'id': ''},'reporter':{'employeeNumber': 1},'category':self.category,'title':'','description':''};
    self.currentIssue= {"id": null, "issueNumber": '', "project": '', "reporter": '', "assignedTo": '', "isPublic": '',"category": '',
        "status": '', "priority": '', "title": '', "description": '', "dueDate": '',
        "createdBy": '', "creationDate": '', "lastModifiedBy": '', "lastModifiedDate": ''
    };
    self.putIssue={"id": null, "issueNumber": '', "project": {'id': ''}, "reporter": {'employeeNumber': 1}, "assignedTo": {'employeeNumber': 1}, "isPublic": '',"category": '',
        "status": '', "priority": '', "title": '', "description": '', "dueDate": '',
        "createdBy": {'employeeNumber': 1}, "creationDate": '', "lastModifiedBy": {'employeeNumber': 1}, "lastModifiedDate": ''
    };
    self.issues=[];
    self.postcomment={'issue':{'id':self.currentIssue.id},'user':{'employeeNumber': 1},'body':''};
    self.comments=[];
    self.commentflag= false;
 
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
    };

    self.fetchAllIssues();

    self.fetchIssue = function fetchIssue(issueId){
        IssueService.fetchIssue(issueId)
            .then(
            function(data){
                self.currentIssue= data;
                self.fetchAllComments(self.currentIssue.issueNumber);
            },
            function(errResponse){
                console.error('Error while fetching issue: '+issueId);
            }
            );
    };

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
    };

    if(localStorage.getItem("number")!=null){
        self.fetchIssue(localStorage.getItem("number"));
    }

    self.createIssue = function createIssue(){
        IssueService.createIssue(self.postissue)
            .then(
                self.fetchAllIssues,
            function(errResponse){
                console.error('Error while creating Issue');
            }
        );
        $state.go("home.dashboard");
    };

    self.createComment = function createComment(){
        self.postcomment.issue.id= self.currentIssue.id;
        IssueService.createComment(self.postcomment)
            .then(
                self.fetchAllComments(self.currentIssue.issueNumber),
            function(errResponse){
                console.error('Error while posting comment');
            }
        );
        self.cancelComment();
        $window.location.reload();
    };

    self.updateIssue= function(description){
        self.transformToPut();
        IssueService.updateIssue(self.putIssue, description)
            .then(
                self.fetchAllIssues,
                function(errResponse){
                    console.error('Error while updating Issue');
                }
            );
        alert('Issue successfully updated');
        $state.go('home.viewIssue');
    };

    self.transformToPut= function(){
        self.putIssue.id= self.currentIssue.id;
        self.putIssue.issueNumber= self.currentIssue.issueNumber;
        self.putIssue.project.id= self.currentIssue.project;
        self.putIssue.reporter.employeeNumber= parseInt( self.currentIssue.reporter);
        self.putIssue.assignedTo.employeeNumber= parseInt(self.currentIssue.assignedTo);
        self.putIssue.isPublic= self.currentIssue.isPublic;
        self.putIssue.category= self.currentIssue.category;
        self.putIssue.status= self.currentIssue.status;
        self.putIssue.priority= self.currentIssue.priority;
        self.putIssue.title= self.currentIssue.title;
        self.putIssue.description= self.currentIssue.description;
        self.putIssue.dueDate= self.currentIssue.dueDate;
        self.putIssue.createdBy.employeeNumber= parseInt(self.currentIssue.createdBy);
        self.putIssue.creationDate= self.currentIssue.creationDate;
        self.putIssue.lastModifiedBy.employeeNumber= parseInt(self.currentIssue.lastModifiedBy);
        self.putIssue.lastModifiedDate= self.currentIssue.lastModifiedDate;
    };

    self.deleteIssue= function(){
        IssueService.deleteIssue(self.currentIssue.issueNumber)
            .then(
                self.fetchAllIssues,
                function(errResponse){
                    console.error('Error while deleting Issue');
                }
            );
        localStorage.setItem("number", null);
    };

    self.confirmIssue= function(){
        var check= confirm('Are you sure you want to confirm issue '+ self.currentIssue.issueNumber+'?');
        if(check){
            self.currentIssue.status= 'CONFIRMED';
            self.updateIssue('Confirmed');
        }
    };

    self.rejectIssue= function(){
        var check= confirm('Are you sure you want to reject issue '+ self.currentIssue.issueNumber+'?');
        if(check){
            self.currentIssue.status= 'REJECTED';
            self.updateIssue('Rejected');
        }
    };

    self.markAsFixed= function(){
        var check= confirm('Are you sure you want to mark issue '+ self.currentIssue.issueNumber+' as fixed?');
        if(check){
            self.currentIssue.status= 'RESOLVED';
            self.updateIssue('Marked as fixed');
        }
    };

    self.reopenIssue= function(){
        var check= confirm('Are you sure you want to reopen issue '+ self.currentIssue.issueNumber+'?');
        if(check){
            self.currentIssue.status= 'NEW';
            self.updateIssue('Reopened');
        }
    };

    self.goToIssue= function(id){
        localStorage.setItem("number",id);
        $state.go('home.viewIssue');
    };

    self.goToUpdateIssue= function(id){
        localStorage.setItem("number",id);
        $state.go('home.updateIssue');
    };

    self.setCommentFlag= function setCommentFlag(){
        self.commentflag=true;
    };

    self.cancelComment= function cancelComment(){
        self.commentflag=false;
    };

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