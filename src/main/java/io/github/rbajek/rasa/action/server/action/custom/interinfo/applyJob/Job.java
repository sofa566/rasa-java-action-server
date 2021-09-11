package io.github.rbajek.rasa.action.server.action.custom.interinfo.applyJob;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class Job {
	private String job;
	private String nameZh;
	private String salary;
	private List<String> contents;
	
	public Job(String job, String nameZh, String salary, List<String> contents) {
		super();
		this.job = job;
		this.nameZh = nameZh;
		this.salary = salary;
		this.contents = contents;
	}

	public Job() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
