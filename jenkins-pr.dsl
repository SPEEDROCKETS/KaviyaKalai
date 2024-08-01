def GIT_COMMIT
def GIT_BRANCH
def BUILD_URL

pipeline {
    agent any
    options {
        timestamps()
    }
    environment {
        appName = 'CES'
	JDK11_HOME = tool name: 'openjdk_11_0_2', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
	fossa_HOME = tool name: 'fossa_3_2_2', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
	JAVA_HOME = "${JAVA_HOME_ADOPTJDK11}"

        PATH = "${JDK11_HOME}:${fossa_HOME}:${PATH}"

        cfOrgPreProd = '${cfOrgName}_EDC1_Preprod'
        spaceDev = '${spaceName}-dev'
        pcfUrlPreProd = 'https://api.sys.pp01.edc1.cf.ford.com'

        HTTP_PROXY = "http://internet.ford.com:83"
        HTTPS_PROXY = "http://internet.ford.com:83"
        NO_PROXY = ".ford.com"

                projectName = 'China Enablement Systems'
        		sonarCredentialsId = 'SONARQUBE_API_KEY'
        		sonarProjectKey = 'com.ford.fc.api.ces'
        		SONAR_HOST_URL = 'https://www.sonarqube.ford.com'
        
        PROJECT_NAME = 'CES'
        GIT_URL = "https://github.ford.com/FBS-FC-API/$PROJECT_NAME"
        GIT_STATUS_URL = "https://github.ford.com/api/v3/repos/FBS-FC-API/$PROJECT_NAME/statuses"
        GIT_PULL_URL = "https://github.ford.com/api/v3/repos/FBS-FC-API/$PROJECT_NAME/pulls"

		FOSSA_SERVER = "https://ford.fossa.com"
		FOSSA_TEAM = 'CES-53399'
    }
    stages {
        stage('Pipeline Setup') {
        	steps{
        		deleteDir()
        		checkout scm
        		script {
        			def scmENV = checkout scm
        			GIT_COMMIT = scmENV.GIT_COMMIT
      				GIT_BRANCH = scmENV.GIT_BRANCH
      				BUILD_URL = env.BUILD_URL
      			}
      		}
        }
       stage("Build") {
       		steps {
       			sh "echo 'shell scripts to build project...'"
       			sh "chmod +x gradlew"
       			githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "pending", "$BUILD_URL", "PR Pipeline", "Build")
       			sh './gradlew clean build --refresh-dependencies'
       		}
       		post {
       			success {
       			githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "success", "$BUILD_URL", "PR Pipeline", "Build")
       			}
       			failure {
       			githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "failure", "$BUILD_URL", "PR Pipeline", "Build")
       			}
       		}
       }

       stage('CheckMarx Analysis') {
            steps{
                step([$class: 'CxScanBuilder', addGlobalCommenToBuildCommet: true, comment: '',
                        configAsCode: true, credentialsId: '', excludeFolders: '', exclusionsSetting: 'job', failBuildOnNewResults: false, failBuildOnNewSeverity: 'HIGH', filterPattern: '''!**/_cvs/**/*, !**/.svn/**/*, !**/.hg/**/*, !**/.git/**/*, !**/.bzr/**/*,
                        !**/.gitgnore/**/*, !**/.gradle/**/*, !**/.checkstyle/**/*, !**/.classpath/**/*, !**/bin/**/*,
                        !**/obj/**/*, !**/backup/**/*, !**/.idea/**/*, !**/*.DS_Store, !**/*.ipr, !**/*.iws,
                        !**/*.bak, !**/*.tmp, !**/*.aac, !**/*.aif, !**/*.iff, !**/*.m3u, !**/*.mid, !**/*.mp3,
                        !**/*.mpa, !**/*.ra, !**/*.wav, !**/*.wma, !**/*.3g2, !**/*.3gp, !**/*.asf, !**/*.asx,
                        !**/*.avi, !**/*.flv, !**/*.mov, !**/*.mp4, !**/*.mpg, !**/*.rm, !**/*.swf, !**/*.vob,
                        !**/*.wmv, !**/*.bmp, !**/*.gif, !**/*.jpg, !**/*.png, !**/*.psd, !**/*.tif, !**/*.swf,
                        !**/*.jar, !**/*.zip, !**/*.rar, !**/*.exe, !**/*.dll, !**/*.pdb, !**/*.7z, !**/*.gz,
                        !**/*.tar.gz, !**/*.tar, !**/*.gz, !**/*.ahtm, !**/*.ahtml, !**/*.fhtml, !**/*.hdm,
                        !**/*.hdml, !**/*.hsql, !**/*.ht, !**/*.hta, !**/*.htc, !**/*.htd, !**/*.war, !**/*.ear,
                        !**/*.htmls, !**/*.ihtml, !**/*.mht, !**/*.mhtm, !**/*.mhtml, !**/*.ssi, !**/*.stm,
                        !**/*.bin,!**/*.lock,!**/*.svg,!**/*.obj, !**/*.js,
                        !**/*.stml, !**/*.ttml, !**/*.txn, !**/*.xhtm, !**/*.xhtml, !**/*.class, !**/*.iml,
                        !Checkmarx/Reports/*.*,
                        !OSADependencies.json, !**/node_modules/**/*''', fullScanCycle: 10, groupId: '363',
                        password: '{AQAAABAAAAAQ+3atRXNTcyC0/w1zMf2zSBoNLAlLJvLjpougsNqXVx0=}', preset: '36',
                        projectName: 'CES_PR_Checks',
                        sastEnabled: true, serverUrl: 'https://www.checkmarx.ford.com',
                        sourceEncoding: '1', username: '', vulnerabilityThresholdResult: 'FAILURE',
                        waitForResultsEnabled: true])
            }
                post {
            		success {
            		    githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "success", "https://www.checkmarx.ford.com/CxWebClient", "PR Pipeline", "Checkmarx")
            		}
            		failure {
            			githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "failure", "https://www.checkmarx.ford.com/CxWebClient", "PR Pipeline", "Checkmarx")
            		}
            	}
       }
       stage('SonarQube Analysis') {
                options {
                            timeout(time: 5, unit: 'MINUTES')
                            retry(2)
                        }
                steps {
                        githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "pending", "$BUILD_URL", "PR Pipeline", "SonarQube")
                      	withCredentials([string(credentialsId: "Github",
                      	variable: "githubToken")]) {
                      	sh "chmod -R 777 ./scripts/sonarqubeStatus.sh"
                      	sh "./scripts/sonarqubeStatus.sh $GIT_BRANCH $githubToken"
                        }
                      }
                      post {
                      	success {
                        	githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "success", "$BUILD_URL", "PR Pipeline", "SonarQube")
                      	}
                      	failure {
                      		githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "failure", "$BUILD_URL", "PR Pipeline", "SonarQube")
                      	}
                      }
                }


       stage("FOSSA") {
                steps {
                  withCredentials([string(credentialsId: "FOSSA_API_KEY", variable: "fossa_api_key")]) {
                  sh("FOSSA_API_KEY=$fossa_api_key fossa analyze -e '$FOSSA_SERVER' -T '$FOSSA_TEAM' -p '$FOSSA_TEAM' --policy 'Website/Hosted Service Use' -b '$GIT_BRANCH' --title '$PROJECT_NAME'")
                       }
                }
                post {
                	success {
                		githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "success", "$BUILD_URL", "PR Pipeline",
                		"FOSSA")
                	}
                	failure {
                		githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "failure", "$BUILD_URL", "PR Pipeline",
                		"FOSSA")
                	}
                }
       }

    }
}
def githubStatus(String githubUrl, String state, String targetUrl, String description, String context) {
	withCredentials([string(credentialsId: "Github", variable: "githubToken")]) {
		sh "curl -X POST -H \"Authorization: token $githubToken\" $githubUrl -d \'{\"state\":\"$state\",\"target_url\": \"$targetUrl\", \"description\": \"$description\", \"context\": \"$context\"}\'"
	}
}