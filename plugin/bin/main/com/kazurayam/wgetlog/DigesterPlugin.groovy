package com.kazurayam.wgetlog

import org.gradle.api.Project
import org.gradle.api.Plugin

/**
 * A Gradle plugin that transforms a log of GNU wget command
 * into a simpler line-oriented format.
 */
class DigesterPlugin implements Plugin<Project> {

    void apply(Project project) {
        // add the 'wgetLogDigest' extension object into the project
        def ext =
                project.extensions.create("wgetLogDigest", DigesterExtension)

        // create the "wget-log-digest" task and
        // register it into the project
        project.tasks.register("wgetLogDigest", DigesterTask) {
            task ->
                task.wgetLogFile.convention(ext.wgetLogFile)
                task.outputFile.convention(ext.outputFile)
        }
    }
}
