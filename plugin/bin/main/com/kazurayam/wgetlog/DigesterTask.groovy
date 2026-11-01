package com.kazurayam.wgetlog

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path

abstract class DigesterTask extends DefaultTask {

    @InputFile
    abstract RegularFileProperty getWgetLogFile()

    @OutputFile
    abstract RegularFileProperty getOutputFile()

    DigesterTask() {
        getWgetLogFile().convention(
                project.layout.projectDirectory.file("wget.log"))
        getOutputFile().convention(
                project.layout.projectDirectory.file("output.txt"))
    }

    /**
     * Read a wget log file, transform it into a simpler line-oriented text,
     * write the result into the output file.
     */
    @TaskAction
    void action() {
        Path wgetLog = getWgetLogFile().get().asFile.toPath()
        BufferedReader br = Files.newBufferedReader(wgetLog)
        Digester digester = new Digester(br)

        Path outputFile = getOutputFile().get().asFile.toPath()
        Files.createDirectories(outputFile.getParent())
        PrintWriter pw = new PrintWriter(Files.newBufferedWriter(outputFile))

        while (digester.hasNext()) {
            WgetLogEntry entry = digester.next()
            pw.println(entry.toString())
        }
        br.close()
        pw.flush()
        pw.close()
    }
}
