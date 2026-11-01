package com.kazurayam.wgetlog

import org.gradle.testkit.runner.BuildResult
import spock.lang.Specification
import spock.lang.TempDir
import org.gradle.testkit.runner.GradleRunner
import java.nio.file.Paths

/**¶
 * A simple functional test for the 'com.kazurayam.wget-log-digest' plugin.
 */
class DigesterPluginFunctionalTest extends Specification {

    @TempDir
    private File projectDir  // allocated by Spock

    private getBuildFile() {
        new File(projectDir, "build.gradle")
    }

    private getSettingsFile() {
        new File(projectDir, "settings.gradle")
    }

    def "can run wgetLogDigest task"() {
        given:
        settingsFile << "rootProject.name = 'functional-test-project'"
        buildFile << """                                             
plugins {                                                            
    id('com.kazurayam.wget-log-digest-plugin')                                       
}

tasks.register('demonstrate') {
    def output = layout.buildDirectory.file("tmp/functionalTest/straight/output.txt")
    wgetLogDigest {
        wgetLogFile = layout.projectDirectory.file("src/test/fixtures/wget.log")
        outputFile = output
    }
    doLast {
        println "output at " + output.get()
    }
}                                                                   
"""
        when:
        GradleRunner runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("demonstrate", '--stacktrace')
        runner.withProjectDir(projectDir)
        BuildResult result = runner.build()

        then:
        result.output.contains("output at ")
    }
}
