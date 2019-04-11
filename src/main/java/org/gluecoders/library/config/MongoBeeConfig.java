/*
package org.gluecoders.library.config;

import com.github.mongobee.Mongobee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("fongo-mongobee")
public class MongoBeeConfig {

    @Bean
    public Mongobee mongobee(){
        Mongobee runner = new Mongobee("mongodb://localhost:27017/mockDB");
        runner.setDbName("yourDbName");         // host must be set if not set in URI
        runner.setChangeLogsScanPackage(
                "org.gluecoders.library.changelogs"); // the package to be scanned for changesets

        return runner;
    }

}


*/
