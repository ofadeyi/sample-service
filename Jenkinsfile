#!/usr/bin/env groovy
node('maven') {

    def mvnHome
    def pom
    def artifactId
    def version
    def branchName
    def releaseBranch = "master"

    // Mark the code checkout 'stage'....
    stage('Preparation') {
        // Checkout code from repository
        checkout scm

        // Get the maven tool.
        mvnHome = tool 'M3'

        // Add MVN to the path
        env.PATH = "${mvnHome}/bin:${env.PATH}"

        // Read the branch name from git
        sh 'git branch -a --contains $(git rev-parse --short HEAD) --merged > branchName'
        branchName = readFile('branchName').trim()
        println "Releasing from branch: $branchName"

        // Read the POM file and extract the versionNumber
        pom = readMavenPom file: 'pom.xml'
        version = branchName.contains(releaseBranch) ? pom.version : "${pom.version}.${currentBuild.number}"
        println "The artefact version will be: $version"
    }

    // Mark the code build 'stage'....
    stage('Compile artefact') {
        // Retrieve the global settings.xml
        configFileProvider([configFile(fileId: 'wb-mvn-settings', variable: 'MAVEN_SETTINGS')]) {

            // Set the artefact version
            sh "mvn  -s $MAVEN_SETTINGS -U versions:set -DnewVersion=${version}"

            // Run the maven build
            sh "mvn -s $MAVEN_SETTINGS -U clean compile"
        }
    }

    stage('Verify artefact') {
        // Retrieve the global settings.xml
        configFileProvider([configFile(fileId: 'wb-mvn-settings', variable: 'MAVEN_SETTINGS')]) {

            // Run the maven test
            sh "mvn -s $MAVEN_SETTINGS -U -Dmaven.test.failure.ignore verify"

            // Store test results
            step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
        }
    }

    stage('Deploy artifact to Nexus') {
        // Retrieve the global settings.xml
        configFileProvider([configFile(fileId: 'wb-mvn-settings', variable: 'MAVEN_SETTINGS')]) {
            // Deploy to artefacts repository
            sh "mvn -s $MAVEN_SETTINGS -Dmaven.test.skip=true deploy"
        }
    }

    stage('Build Docker image') {
        def artifactCoordinate = "${pom.groupId.replace('.', '/')}/${pom.artifactId}/${version}/${pom.artifactId}-${version}.jar"
        def artifactDownloadLink = "${pom.distributionManagement.repository.url}/$artifactCoordinate"
        println "The Artifact Coordinate: $artifactCoordinate"
        println "The Artifact Download Link: $artifactDownloadLink"
        sh "sudo docker build --rm=true --build-arg ARTIFACT_DOWNLOAD_LINK=$artifactDownloadLink --tag=whitbreaddigital/${pom.artifactId}:${version} ."
    }

    stage('Deploy image to DockerHub') {
        withCredentials([[$class          : 'UsernamePasswordMultiBinding', credentialsId: 'docker-hub',
                          usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
            sh """
              sudo docker login -u=$USERNAME -p='$PASSWORD'
              sudo docker push whitbreaddigital/${pom.artifactId}:${version}
              sudo docker logout
            """
        }
    }
}
