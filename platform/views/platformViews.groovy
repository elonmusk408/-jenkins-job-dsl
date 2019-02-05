package platform

import static org.edx.jenkins.dsl.JenkinsPublicConstants.DEFAULT_VIEW

List<String> branchList = [ "edx-platform", // Represents all non-release testing
                            "hawthorn"
                            ]

branchList.each { branch ->

    listView("${branch}-pr-tests") {
        if (branch == "edx-platform" ) {
            description('Jobs for running tests on PRs on the edx-platform')
        }
        else {
            description("Jobs for running tests on PRs against the " +
                        "open-release/${branch}.master branch of the edx-platform")
            filterBuildQueue(true)
            filterExecutors(true)
        }

        jobs {
            regex("${branch}-.*-pr")
        }
        columns DEFAULT_VIEW.call()

    }

    listView("${branch}-master-tests") {
        if (branch == "edx-platform" ) {
            description('Jobs for running tests pushes to master branch of the edx-platform')
        }
        else {
            description("Jobs for running tests on commits pushed onto the " +
                        "open-release/${branch}.master branch of the edx-platform")
            filterBuildQueue(true)
            filterExecutors(true)
        }

        jobs {
            name('github-build-status')
            regex("${branch}-.*-master")
        }
        columns DEFAULT_VIEW.call()
    }

}

listView("edx-platform-pipeline-pr-tests") {
    description('Pipeline Jobs for running tests on PRs on the edx-platform')

    jobs {
        regex("edx-platform-.*-pipeline-pr")
    }
    columns DEFAULT_VIEW.call()
}

listView("edx-platform-private-tests") {
    description('Jobs for running tests on edx-platform-private')

    jobs {
        regex(".*_private")
    }
    columns DEFAULT_VIEW.call()
}
