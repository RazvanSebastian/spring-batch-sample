package com.example.samplespringbatch.dto;

public class ProjectDTO {

	private String projectName;
	private String projectType;
	private String projectStatus;
	private String projectTechnologies;

	public ProjectDTO() {
		super();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getProjectTechnologies() {
		return projectTechnologies;
	}

	public void setProjectTechnologies(String projectTechnologies) {
		this.projectTechnologies = projectTechnologies;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	@Override
	public String toString() {
		return "ProjectDTO [projectname=" + projectName + ", projectStatus=" + projectStatus + ", projectTechnologies="
				+ projectTechnologies + "]";
	}

}
