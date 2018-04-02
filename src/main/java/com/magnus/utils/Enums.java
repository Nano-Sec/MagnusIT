package com.magnus.utils;

public class Enums {
	public enum RoleType {
		ADMIN, EMPLOYEE
	}
	public enum IssueStatus {
		NEW,CONFIRMED,REJECTED,ASSIGNED,RESOLVED,CLOSED
	}
	public enum IssueCategory{
		Hardware,Server,Client
	}
	public enum IssuePriority {
		URGENT,HIGH,MEDIUM,LOW
	}
	public enum ProjectStatus {
		NEW,IN_PROGRESS,COMPLETED
	}
}
