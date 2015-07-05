package org.jspringbot.jmeter.cmdtools;

import org.junit.Test;
import org.junit.Ignore;

import java.io.File;

@Ignore
public class CmdToolsMojoTest {

    @Test
    public void testExecute() throws Exception {
        CmdToolsMojo mojo = new CmdToolsMojo();

        mojo.setJmeterHome("/Users/sbuitizon/Downloads/jmeter");
        mojo.setTargetDirectory(new File("/tmp"));
        mojo.setGenerateCsv("summary.csv");
        mojo.setPluginType("org.jspringbot.jmeter.cmdtools.plugin.CustomAggregateReport");
        mojo.setInputJtl(new File("/Users/sbuitizon/Downloads/AlphaWebAppCalls.jtl"));

        mojo.execute();
    }
}