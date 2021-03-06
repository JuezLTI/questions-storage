package com.juezlti.repository.models.yapexil;

import com.juezlti.repository.models.Exercise;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import static com.juezlti.repository.service.ExerciseService.TESTS_FOLDER;

@Data
@NoArgsConstructor
public class TestMetadata {
    String id;
    Number weight;
    Boolean visible;
    String input;
    String output;
    List<String> arguments;
    String inputValue;
    String outputValue;
    Object feedback;
    private String exerciseId;

    public TestMetadata(Exercise exercise){
        this.id = UUID.randomUUID().toString();
        this.exerciseId = exercise.getAkId();
        this.weight = 0;
        this.visible = true;
        this.input = "input.txt";
        this.output = "output.txt";
        this.arguments = exercise.getKeywords();
        this.feedback = new ArrayList<String>();
    }

    public String getInputFileStringPath() {
        return this.getExerciseId() + "/" + TESTS_FOLDER + "/" + this.getId() + "/" + this.getInput();
    }

    public String getOutputFileStringPath() {
        return this.getExerciseId() + "/" + TESTS_FOLDER + "/" + this.getId() + "/" + this.getOutput();
    }

}
