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

        stage('Release Mux-WildFly') {
            when { anyOf { branch 'master'; branch 'release' } }
            steps {
                withAnt(installation: 'Ant_1.10.15') {
                    dir('release') {
                        sh "ant -f build.xml init build-and-package-mux-wildfly -Ddiameter.release.version=${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
                    }
                }
                echo "Built restcomm-diameter-mux-wildfly-${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}.zip"
            }
        }

        stage('Save Artifacts') {
            when { anyOf { branch 'master'; branch 'release' } }
            steps {
                archiveArtifacts artifacts: "release/restcomm-diameter-mux-wildfly-${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}.zip", followSymlinks: false, onlyIfSuccessful: true
            }
        }

        stage('Push Zip to Artifactory') {
            when { anyOf { branch 'master'; branch 'release' } }
            steps {
                withCredentials([usernamePassword(credentialsId: '426e8cfb-a47c-4fd2-96ae-713c541dc3f6',
                                                  usernameVariable: 'ART_USER', passwordVariable: 'ART_PASS')]) {
                    sh """
                        curl -k -u \${ART_USER}:\${ART_PASS} -X PUT \
                        'https://ec2-56-126-119-82.sa-east-1.compute.amazonaws.com/artifactory/libs-release-local/org/mobicents/diameter/restcomm-diameter-mux-wildfly/${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}/restcomm-diameter-mux-wildfly-${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}.zip' \
                        -T release/restcomm-diameter-mux-wildfly-${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}.zip
                    """
                }
                echo "Pushed restcomm-diameter-mux-wildfly-${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}.zip to Artifactory"
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
