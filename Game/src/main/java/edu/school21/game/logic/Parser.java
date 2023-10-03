package edu.school21.game.logic;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.Parameter;

@Parameters(separators = "=")
public class Parser {
    @Parameter(
            names = "--enemiesCount",
            description = "Enemies count to integer",
            required = true
    )
    public Integer enemiesCount;

    @Parameter(
            names = "--wallsCount",
            description = "Walls count to integer",
            required = true
    )
    public Integer wallsCount;

    @Parameter(
            names = "--size",
            description = "Field size to integer",
            required = true
    )
    public Integer sizeField;

    @Parameter(
            names = "--profile",
            description = "Profile of String",
            required = true
    )
    public String profile;
}
