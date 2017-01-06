#!/usr/bin/env groovy
node('maven') {

    def mvnHome
    def pom
    def version
    def branchName

    // Mark the code checkout 'stage'....
    stage('Preparation') {
        // Checkout code from repository
        checkout scm

        // Get the maven tool.
        mvnHome = tool 'M3'

        // Add MVN to the path
        env.PATH = "${mvnHome}/bin:${env.PATH}"

        // Read the branch name from git
        sh 'git rev-parse --abbrev-ref HEAD > branchName'
        branchName = readFile('branchName').trim()

        // Read the POM file and extract the versionNumber
        pom = readMavenPom file: 'pom.xml'
//        version = branchName.contains('release') ? pom.version :  pom.version.replace("-SNAPSHOT", ".${currentBuild.number}")
        version = branchName.contains('release') ? pom.version : "${pom.version}.${currentBuild.number}"

        // Set the artefact version
        sh "mvn versions:set -DnewVersion=${version}"

        println "The artifact version will be: $version"
    }

    // Mark the code build 'stage'....
    stage('Build') {
        // Retrieve the global settings.xml
        configFileProvider([configFile(fileId: 'wb-mvn-settings', variable: 'MAVEN_SETTINGS')]) {
            // Run the maven build
            sh 'mvn -s $MAVEN_SETTINGS clean compile'
        }
    }

    stage('Verify') {
        // Retrieve the global settings.xml
        configFileProvider([configFile(fileId: 'wb-mvn-settings', variable: 'MAVEN_SETTINGS')]) {

            // Run the maven test
            sh 'mvn -s $MAVEN_SETTINGS -Dmaven.test.failure.ignore verify'

            // Store test results
            step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
        }
    }

    stage('Deploy to Nexus') {
        // Retrieve the global settings.xml
        configFileProvider([configFile(fileId: 'wb-mvn-settings', variable: 'MAVEN_SETTINGS')]) {
            // Deploy to artefacts repository
            sh "mvn -s $MAVEN_SETTINGS -Dmaven.test.skip=true deploy"
        }
    }

    stage('Docker Build') {
        sh "sudo docker build --rm=true --tag=whitbreaddigital/sample-service:${version} ."
    }
}