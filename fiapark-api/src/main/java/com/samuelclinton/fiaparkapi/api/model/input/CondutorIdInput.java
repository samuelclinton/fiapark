package com.samuelclinton.fiaparkapi.api.model.input;

import com.samuelclinton.fiaparkapi.domain.data.InputModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CondutorIdInput implements InputModel {

    @NotNull
    @Schema(example = "1")
    private Long id;

}
