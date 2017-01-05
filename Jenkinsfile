#!/usr/bin/env groovy
node('maven') {

    def mvnHome

    // Mark the code checkout 'stage'....
    stage('Preparation') {
        // Checkout code from repository
        checkout scm

        // Get the maven tool.
        mvnHome = tool 'M3'

        // Add MVN to the path
        env.PATH = "${mvnHome}/bin:${env.PATH}"
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
            sh "mvn -s $MAVEN_SETTINGS deploy"
        }
    }

    stage('Docker Build') {
        // Retrieve the global settings.xml
        configFileProvider([configFile(fileId: 'wb-mvn-settings', variable: 'MAVEN_SETTINGS')]) {
            sh "mvn -s $MAVEN_SETTINGS docker:build"
        }
    }
}