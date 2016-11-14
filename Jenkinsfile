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
        // Run the maven build
        sh 'mvn clean compile'
    }

    stage('Verify') {
        // Run the maven test
        sh 'mvn -Dmaven.test.failure.ignore verify'

        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    }

    stage('Deploy to Nexus') {
        // Retrieve the global settings.xml
        configFileProvider([configFile(fileId: 'wb-mvn-settings', variable: 'MAVEN_SETTINGS')]) {
            sh "mvn -s $MAVEN_SETTINGS deploy"
        }
    }
}