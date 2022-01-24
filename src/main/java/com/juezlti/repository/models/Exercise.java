package com.juezlti.repository.models;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "test")
public class Exercise {

	@Id
	private String id;

	private String title;
	private String module;
	private String owner_id;
	private String project_id;
	private List<String> keywords;
	private String event;
	private String platform;
	private String status;
	private Number timeout;
	private List<String> programmingLanguages;
	private Date updated_at;
	private Date created_at;
	private String statement;
	private String hint;
	
	private String akId;
	
	private String type;
	private String difficulty;
	private String averageGradeUnderstability;
	private String averageGradeDifficulty;
	private String averageGradeTime;
	private String averageGrade;
	private String numberVotes;
	private String exercise_solution;
	
	private String exercise_must;
	private String exercise_musnt;

	// SQL
	private Integer exercise_dbms;
	private String exercise_sql_type;
	private String exercise_database;
	private String exercise_probe;
	private String exercise_onfly;
	
	//Code
	private Integer exercise_language;
	private String exercise_input_test;
	private String exercise_input_grade;
	private String exercise_output_test;
	private String exercise_output_grade;
	private String recalculateOutputs;
	
	@Transient
	@JsonProperty("isCodeExercise")
	private boolean codeExercise;
	@Transient
	@JsonProperty("isSqlExercise")
	private boolean sqlExercise;


}
