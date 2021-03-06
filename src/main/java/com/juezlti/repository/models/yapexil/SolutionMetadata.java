package com.juezlti.repository.models.yapexil;

import com.juezlti.repository.models.Exercise;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.juezlti.repository.service.ExerciseService.SOLUTIONS_FOLDER;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SolutionMetadata {
    String id;
    String pathname;
    String lang;
    String exerciseId;

    public SolutionMetadata(Exercise exercise){
        this.exerciseId = exercise.getAkId();
        this.id = UUID.randomUUID().toString();
        this.lang  = exercise.getExercise_language();
        this.pathname = "solution." + exercise.getExercise_language().toLowerCase();
    }

    public String getSolutionStringPath(){
        return this.getId() + "/" + this.getPathname();
    }

    public Path getSolutionPath(){
        return Paths.get(this.getSolutionStringPath());
    }

    public String getFileStringPath(){
        return this.getExerciseId() + "/" + SOLUTIONS_FOLDER + "/" + this.getId() + "/" + this.getPathname();
    }

    public Path getFilePath(){
        return Paths.get(this.getFileStringPath());
    }

}
