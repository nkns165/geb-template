import geb.report.ScreenshotReporter

reportsDir = "build/geb-reports"
baseUrl = "http://fathomless-stream-3131.herokuapp.com"
waiting {
    presets {
        slow {
            timeout = 15
            retryInterval = 1
        }
        quick {
            timeout = 1
        }
    }
}
waiting {
    timeout = 60
    retryInterval = 0.5
}
atCheckWaiting = true
baseNavigatorWaiting = true
reporter = new ScreenshotReporter() {
    @Override
    protected escapeFileName(String name) {
        name.replaceAll('[\\\\/:\\*?\\"&lt;>\\|]', '_')
    }
}