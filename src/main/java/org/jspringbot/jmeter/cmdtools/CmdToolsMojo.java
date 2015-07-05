package org.jspringbot.jmeter.cmdtools;

import kg.apc.charting.GraphPanelChart;
import kg.apc.jmeter.JMeterPluginsUtils;
import kg.apc.jmeter.PluginsCMDWorker;
import kg.apc.jmeter.vizualizers.CorrectedResultCollector;
import org.apache.commons.lang.StringUtils;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;

@Mojo(name = "analyze")
@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"}) // Mojos get their fields set via reflection
public class CmdToolsMojo extends AbstractMojo {

    @Parameter
    private String generatePng;

    @Parameter(required = true, defaultValue = "${project.build.directory}/jmeter")
    private String jmeterHome;

    @Parameter
    private String generateCsv;

    @Parameter(required = true)
    private File inputJtl;

    @Parameter(required = true, defaultValue = "org.jspringbot.jmeter.cmdtools.plugin.CustomAggregateReport")
    private String pluginType;

    @Parameter(required = true, defaultValue = "${project.build.directory}/jmeter/results")
    private File targetDirectory;

    public void setGeneratePng(String generatePng) {
        this.generatePng = generatePng;
    }

    public void setGenerateCsv(String generateCsv) {
        this.generateCsv = generateCsv;
    }

    public void setInputJtl(File inputJtl) {
        this.inputJtl = inputJtl;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public void setTargetDirectory(File targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setJmeterHome(String jmeterHome) {
        this.jmeterHome = jmeterHome;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(" ");
        getLog().info("-------------------------------------------------------");
        getLog().info(" J M E T E R     C M D     T O O L S");
        getLog().info("-------------------------------------------------------");
        getLog().info(" ");

        try {
            JMeterUtils.setJMeterHome(jmeterHome);
            JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterHome() + File.separator + "bin" + File.separator + "jmeter.properties");
            JMeterUtils.initLocale();

            PluginsCMDWorker worker = new PluginsCMDWorker();

            if(StringUtils.isNotBlank(generatePng)) {
                worker.addExportMode(PluginsCMDWorker.EXPORT_PNG);
                worker.setOutputPNGFile(new File(targetDirectory, generatePng).getAbsolutePath());
            }

            if(StringUtils.isNotBlank(generateCsv)) {
                worker.addExportMode(PluginsCMDWorker.EXPORT_CSV);
                worker.setOutputCSVFile(new File(targetDirectory, generateCsv).getAbsolutePath());
            }

            worker.setPluginType(pluginType);
            worker.setInputFile(inputJtl.getAbsolutePath());

            worker.doJob();
        } catch (Exception e) {
            throw new MojoExecutionException("Error analysing", e);
        }
    }
}
