package com.kazurayam.wgetlog

import org.gradle.api.file.RegularFileProperty

abstract class DigesterExtension {
    abstract RegularFileProperty getWgetLogFile()
    abstract RegularFileProperty getOutputFile()
}
