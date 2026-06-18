pipeline {
    agent any

    tools {
        jdk 'jdk-11'
        maven 'maven-3.9.12'
    }

    options {
        buildDiscarder(logRotator(daysToKeepStr: '30', numToKeepStr: '10'))
    }

    parameters {
        string(name: 'JDIAMETER_MAJOR_VERSION_NUMBER', defaultValue: '2.2.0', description: 'The major version for Naikeri-jDiameter')
    }

    stages {
        stage('Set Version') {
            steps {
                script {
                    if (BUILD_NUMBER == "1") {
                        error "Building for the first time"
                    }
                }
                sh "mvn versions:set -DnewVersion=${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER} -Ptestsuite"
                echo "Set version to ${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
            }
        }

        stage('Build') {
            steps {
                script {
                    currentBuild.displayName = "#${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
                    currentBuild.description = "Naikeri-jDiameter"
                }
                echo "Building Naikeri-jDiameter ${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
                sh "mvn clean install -DskipTests"
                echo "Maven build completed."
            }
        }

        stage('Testsuite') {
            when { anyOf { branch 'master'; branch 'release' } }
            steps {
                echo "Running HA testsuite for ${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
                dir('testsuite/tests') {
                    sh "mvn clean test -Ptestsuite"
                }
            }
        }

        stage('Push to jFrog') {
            when { anyOf { branch 'master'; branch 'release' } }
            steps {
                sh "mvn deploy -DskipTests"
            }
        }
    }

    post {
        success { echo "Successfully built Naikeri-jDiameter" }
        failure { echo "Building Naikeri-jDiameter failed." }
        always  { echo "Build complete." }
    }
}
