package jg.socialapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class MessageDto {

    @NotNull
    @Size(min=1, max=140)
    private String value;

    @ApiModelProperty(hidden = true)
    private String dateTime;

    @ApiModelProperty(hidden = true)
    private String username;
}
