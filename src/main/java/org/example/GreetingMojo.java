package org.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import java.io.IOException;

@Mojo(name = "exec")
public class GreetingMojo extends AbstractMojo {

    @Parameter(property = "path")
    private String path;


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // 获取当前项目的根目录
        try {
            SwaggerToSmartdocUtil.exec(path);
            getLog().info("执行成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}