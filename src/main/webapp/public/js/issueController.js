angular.module('myapp').controller('IssueController', ['$q','$window','$state','IssueService','ProjectService','EmployeeService', function($q,$window,$state,IssueService,ProjectService,EmployeeService) {
    var self = this;
    self.issueNumber=Math.floor(Math.random() * 10000);
    self.postissue={'issueNumber':self.issueNumber,'project':{'id': null},'reporter':{'employeeNumber': 1},'category':self.category,'title':'','description':''};
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
    self.commentUsers=[];
    self.historyUsers=[];
    self.usersToFetch=[];
    self.commentflag= false;
    self.page={"currentPage":1,"count":0,"pageSize":20,"totalPages":0};
    self.pageArray=[];

    self.setPage= function(page){
        var startPage,endPage;
        if(self.page.totalPages<=5){
            startPage=1;
            endPage= self.page.totalPages;
        }
        else{
            if(self.page.currentPage<=3) {
                startPage = 1;
                endPage= 5;
            }
            else if(self.page.currentPage>=self.page.totalPages-2){
                startPage=self.page.totalPages-4;
                endPage=self.page.totalPages;
            }
            else{
                startPage=self.page.currentPage-2;
                endPage=self.page.currentPage+2;
            }
        }
        for(var i=startPage;i<endPage;i++){
            self.pageArray[i]=i;
        }
        self.page.currentPage=page;
        self.fetchAllIssues(self.page.currentPage, self.page.pageSize);
    };
 
    self.fetchIssueCount= function(){
      IssueService.fetchIssueCount()
          .then(
              function(data) {
                  self.page.count= data;
                  self.page.totalPages=Math.ceil(self.page.count/self.page.pageSize);
                  self.setPage(1);
              },
              function(errResponse){
                  console.error('Error while fetching Issue count');
              }
          );
    };

    self.fetchAllIssues = function fetchAllIssues(page,size){
        IssueService.fetchAllIssues(page-1,size)
            .then(
            function(data) {
                self.issues= data;
            },
            function(errResponse){
                console.error('Error while fetching Issues');
            }
        );
    };

    self.searchIssues = function searchIssues(page,size,search){
        IssueService.searchIssues(page,size,search)
            .then(
                function(data) {
                    self.issues= data;
                },
                function(errResponse){
                    console.error('Error while searching Issues');
                }
            );
    };

    self.fetchIssueCount();
    self.fetchAllIssues(self.page.currentPage,self.page.pageSize);

    self.fetchUser= function(empId){
        var deferred= $q.defer();
        EmployeeService.fetchEmployee(empId)
            .then(
                function(data){
                    deferred.resolve(data);
                },
                function(errResponse){
                    console.error('Error while fetching employee: '+empId);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    };

    self.fetchProject= function(projectId){
        var deferred= $q.defer();
        ProjectService.fetchProject(projectId)
            .then(
                function(response){
                    deferred.resolve(response);
                },
                function(errResponse){
                    console.error('Error while fetching project: '+projectId);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    };

    self.fetchIssue = function fetchIssue(issueId){
        IssueService.fetchIssue(issueId)
            .then(
            function(data){
                self.currentIssue= data;
                self.fetchAllComments(self.currentIssue.issueNumber);
                self.fetchHistory(self.currentIssue.issueNumber);
                self.fetchProject(self.currentIssue.project)
                    .then(
                        function(response){
                            self.project= response;
                        },
                        function(errResponse){
                            console.error(errResponse);
                        }
                    );
                if(self.currentIssue.assignedTo!=-1){
                    self.fetchUser(self.currentIssue.assignedTo)
                        .then(
                            function(response){
                                self.assignedTo= response;
                            },
                            function(errResponse){
                                console.error(errResponse);
                            }
                        );
                }
                self.fetchUser(self.currentIssue.reporter)
                    .then(
                        function(response){
                            self.reporter= response;
                        },
                        function(errResponse){
                            console.error(errResponse);
                        }
                    );
            },
            function(errResponse){
                console.error('Error while fetching issue: '+issueId);
                localStorage.setItem("number",null);
            }
            );
    };

    self.fetchAllComments = function fetchAllComments(number){
        IssueService.fetchAllComments(number)
            .then(
            function(data) {
                self.comments= data;
                for(var i=0;i<self.comments.length;i++){
                    self.usersToFetch.push(self.comments[i].user);
                }
                self.fetchAllCommentUsers(JSON.stringify(self.usersToFetch));
            },
            function(errResponse){
                console.error('Error while fetching comments');
            }
        );
    };

    self.fetchAllCommentUsers= function(list){
      EmployeeService.fetchEmployeeEssentials(list)
          .then(
              function(data) {
                  self.commentUsers=data;
              },
              function(errResponse){
                  console.error('Error while fetching comment users');
              }
          );
    };

    self.fetchAllHistoryUsers= function(list){
        EmployeeService.fetchEmployeeEssentials(list)
            .then(
                function(data) {
                    self.historyUsers=data;
                },
                function(errResponse){
                    console.error('Error while fetching history users');
                }
            );
    };

    self.fetchHistory = function fetchHistory(number) {
        IssueService.fetchHistory(number)
            .then(
                function(data) {
                    self.history= data;
                    for(var i=0;i<self.history.length;i++){
                        self.usersToFetch.push(self.history[i].user);
                    }
                    self.fetchAllHistoryUsers(JSON.stringify(self.usersToFetch));
                },
                function(errResponse){
                    console.error('Error while fetching issue history');
                }
            );
    };

    if(localStorage.getItem("number")!=null){
        self.fetchIssue(localStorage.getItem("number"));
    }

    self.createIssue = function createIssue(){
        IssueService.createIssue(self.postissue)
            .then(
                self.fetchAllIssues(self.page.currentPage,self.page.pageSize),
            function(errResponse){
                console.error('Error while creating Issue');
            }
        );
        alert('issue created successfully');
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
                self.fetchAllIssues(self.page.currentPage,self.page.pageSize),
                function(errResponse){
                    console.error('Error while updating Issue');
                }
            );
        alert('Issue successfully updated');
        $state.go('home.viewIssue');
        $window.location.reload();
    };

    self.transformToPut= function(){
        self.putIssue.id= self.currentIssue.id;
        self.putIssue.issueNumber= self.currentIssue.issueNumber;
        self.putIssue.project.id= self.currentIssue.project;
        self.putIssue.reporter.employeeNumber= parseInt( self.currentIssue.reporter);
        if(self.currentIssue.assignedTo==-1){
            self.putIssue.assignedTo=null;
        }
        else
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
        var check= confirm('Are you sure you want to delete this issue?');
        if(check==true) {
            IssueService.deleteIssue(self.currentIssue.issueNumber)
                .then(
                    self.fetchAllIssues(self.page.currentPage,self.page.pageSize),
                    function (errResponse) {
                        console.error('Error while deleting Issue');
                    }
                );

            localStorage.setItem("number", null);
            $state.go('home.viewIssueList');
        }
    };

    self.confirmIssue= function(){
        var check= confirm('Are you sure you want to confirm issue '+ self.currentIssue.issueNumber+'?');
        if(check==true){
            self.currentIssue.status= 'CONFIRMED';
            self.updateIssue('Confirmed');
        }
    };

    self.rejectIssue= function(){
        var check= confirm('Are you sure you want to reject issue '+ self.currentIssue.issueNumber+'?');
        if(check==true){
            self.currentIssue.status= 'REJECTED';
            self.updateIssue('Rejected');
        }
    };

    self.markAsFixed= function(){
        var check= confirm('Are you sure you want to mark issue '+ self.currentIssue.issueNumber+' as fixed?');
        if(check==true){
            self.currentIssue.status= 'RESOLVED';
            self.updateIssue('Marked as fixed');
        }
    };

    self.reopenIssue= function(){
        var check= confirm('Are you sure you want to reopen issue '+ self.currentIssue.issueNumber+'?');
        if(check==true){
            self.currentIssue.status= 'NEW';
            self.updateIssue('Reopened');
        }
    };

    self.goToIssue= function(id){
        localStorage.setItem("number",id);
        $state.go('home.viewIssue');
    };

    self.goToProject= function(id){
        localStorage.setItem("projectNumber",id);
        $state.go('home.viewProject');
    };

    self.goToUser= function(id){
        localStorage.setItem("empNumber",id);
        $state.go('home.viewEmployee');
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
}]);