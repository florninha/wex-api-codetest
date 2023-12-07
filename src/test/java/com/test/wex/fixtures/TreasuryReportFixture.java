package com.test.wex.fixtures;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class TreasuryReportFixture {

    public String CONST_PATH="./src/test/java/com/test/wex/resources/payloads";
    public String treasuryReportWithDataAsString() throws IOException {
        Path filePath = Path.of(CONST_PATH + "/treasury/TreasuryReportWithData.json");
        return Files.readString(filePath);
    }

    public String treasuryReportWithoutDataAsString() throws IOException {
        Path filePath = Path.of(CONST_PATH + "/treasury/TreasuryReportWithoutData.json");
        return Files.readString(filePath);
    }
}
