pipeline {
	agent any

	tools {
	    jdk 'JDK 11'
		maven 'Maven_3.8.5'
	}

	options {
    	buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '10'))
  	}


	parameters { string(name: 'JDIAMETER_MAJOR_VERSION_NUMBER', defaultValue: '1.7.4', description: 'The major version for Naikeri-jDiameter') }

	stages {

		stage("Build") {
			steps {
				echo "Building application..."
				script {
                    currentBuild.displayName = "#${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
                    currentBuild.description = "RestComm-jDiameter"
                }
				sh "mvn clean install -DskipTests"
				echo "Maven build completed."
			}
		}
    	stage('Set Version') {
      		steps{
				sh "mvn versions:set -DnewVersion=${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER} clean install -DskipTests"
      		}
    	}
		stage("Release") {
			steps {
				echo "Building a release version of #${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
        		withAnt(installation: 'Ant_1.10.12') {
          			dir('release') {
          			    sh "rm -rf restcomm-diameter*.zip"
            			sh "ant -f build.xml -Ddiameter.release.version=${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
 					}
				}
				echo "Building a release version completed."
			}
		}

		stage('Save Artifacts') {
			//when { anyOf { branch 'master'; branch 'release' } }
        	steps {
          		echo "Archiving Naikeri-jDiameter version ${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}"
            	archiveArtifacts artifacts: "release/*.zip", followSymlinks: false, onlyIfSuccessful: true
        	}
    	}

    	stage('Push Artifacts') {
            when{ anyOf { branch 'master'; branch 'release' }}
            steps {
                /*script {
                    ROOT_PATH = "/var/www/html/RestComm/restcomm_jdiameter/${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}/"
                }*/
                sh "cp release/Naikeri-jDiameter-${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}.zip /var/www/html/NAIKERI/jdiameter/"
                /*sshagent(['ssh_grafana']) {
                    sh "cp release/Naikeri-jDiameter-${params.JDIAMETER_MAJOR_VERSION_NUMBER}-${BUILD_NUMBER}.zip /var/www/html/NAIKERI/jdiameter/"
                    sh "ssh root@127.0.0.1 \"mkdir -p ${ROOT_PATH}\""
                    sh "scp -r release/*.zip root@127.0.0.1:${ROOT_PATH}"
                }*/
            }
    	}

        stage('Push to jFrog') {
            when {anyOf {branch 'master'; branch 'release'}}
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }
  	}

	post {
		success {
			echo "Successfully built"
		}
		failure {
			echo "Building Naikeri-jDiameter failed."
		}
		always {
			echo "This will be called always. After testing do clean up."
			sh 'rm -rf release/checkout'
      	    sh 'rm -rf release/target'
		}
	}
}