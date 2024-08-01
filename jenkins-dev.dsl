def GIT_COMMIT
def GIT_BRANCH
def BUILD_URL

pipeline {
    agent any
    options {
        timestamps()
    }
    environment {
        appName = 'EnablementSystemsAPI'
        cfOrgName = 'CES'
        spaceName = 'CES'
        newmanFilePrefix = 'CESAPI'


        ENVIRONMENT_NAME = 'Dev'
        //CES_KS1_Preprod
        cfCredentialsIdPreprod = 'pcf-preprod'
        cfOrgPreprod = '${cfOrgName}_KS1_Preprod'
        spaceDev = '${spaceName}-dev'
        pcfUrlPreprod = 'https://api.sys.pp01.ks1.cf.ford.com'


        //CES_SH8_Preprod
        cfOrgPreprod2 = '${cfOrgName}_SH8_Preprod'
        pcfUrlPreprod2 = 'https://api.sys.pp01.sh8.cf.ford.com'




	JDK11_HOME = tool name: 'openjdk_11_0_2', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
	fossa_HOME = tool name: 'fossa_3_2_2', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
	JAVA_HOME = "${JAVA_HOME_ADOPTJDK11}"
	ANT_HOME = tool name: 'cf_cli_7_2_0', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
    JQ_HOME = tool name: 'jq_1_5', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
    NODE_HOME = tool name: 'NodeJS_16_13_2', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'

        PATH = "${JDK11_HOME}:${fossa_HOME}:${ANT_HOME}:${JQ_HOME}:${NODE_HOME}:${PATH}"

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

        BUILD_AND_SONAR_STAGE_LOG_FILE = 'build_sonar.log'
        CHECKMARX_STAGE_LOG_FILE = 'checkmarx.log'
        FOSSA_STAGE_LOG_FILE = 'fossa.log'
        DEPLOYMENT_STAGE_LOG_FILE = 'deployment.log'
        INTEGRATION_STAGE_LOG_FILE = 'newman_integration.log'
        UPLOAD_NEXUS_STAGE_LOG_FILE = 'upload_nexus.log'
        VERSION_SELECTION = 'select-version.log'

        SUCCESS_STATUS = 'Succeeded'
        FAILED_STATUS = 'Failed'
    }
    stages {
        stage('Pipeline Setup') {
        			steps{
        				deleteDir()
        				checkout scm
        				script {

        				    sh "chmod +x ./scripts/getCurrentDateTime.sh"
                            sh "./scripts/getCurrentDateTime.sh"
        					def scmENV = checkout scm
        					GIT_COMMIT = scmENV.GIT_COMMIT
        					GIT_BRANCH = scmENV.GIT_BRANCH
        					BUILD_URL = env.BUILD_URL
        				}
        			}
        }

        stage('Create Environment CF homes') {
                    steps {
                        sh('ls -a $WORKSPACE')
                        sh('mkdir ' + spaceDev)
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
                    script {
                       tee(CHECKMARX_STAGE_LOG_FILE) {
                        step([$class: 'CxScanBuilder', addGlobalCommenToBuildCommet: true, comment: '', configAsCode: true, credentialsId: '', excludeFolders: '', exclusionsSetting: 'job', failBuildOnNewResults: false, failBuildOnNewSeverity: 'HIGH', filterPattern: '''!**/_cvs/**/*, !**/.svn/**/*, !**/.hg/**/*, !**/.git/**/*, !**/.bzr/**/*,
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
                                    !**/*.bin,!**/*.lock,!**/*.svg,!**/*.obj,
                                    !**/*.stml, !**/*.ttml, !**/*.txn, !**/*.xhtm, !**/*.xhtml, !**/*.class, !**/*.iml, !Checkmarx/Reports/*.*,
                                    !OSADependencies.json, !**/*.js, !**/node_modules/**/*''',
                                    fullScanCycle: 10,
                                    fullScansScheduled: true,
                                    generatePdfReport: true, groupId: '363',
                                    incremental: true,
                                    password: '{AQAAABAAAAAQMASXKLu9/gey4lp59p67rs5SqpiszqafzIjS+udTRQY=}',
                                    preset: '100005',
                                    projectName: 'CES_DEV',
                                    sastEnabled: true,
                                    serverUrl: 'https://www.checkmarx.ford.com',
                                    sourceEncoding: '1',
                                    username: '',
                                    vulnerabilityThresholdResult: 'FAILURE',
                                    waitForResultsEnabled: true])
                   	}
                }
                }
                post {
                      success {
                        sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                        sh "./scripts/taskLogsToSplunk.sh $CHECKMARX_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                              }
                      failure {
                         sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                         sh "./scripts/taskLogsToSplunk.sh $CHECKMARX_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                              }
                }
                }
       stage('SonarQube Analysis') {
                options {
                            timeout(time: 5, unit: 'MINUTES')
                            retry(2)
                        }
                steps {
                script {
                          tee(BUILD_AND_SONAR_STAGE_LOG_FILE) {
                            withSonarQubeEnv('My Sonar Installation') {
                                sh "chmod +x gradlew"
                                sh './gradlew -q -PrunSonarqube=Y -Psonar.host.url=$SONAR_HOST_URL -Psonar.login=$SONAR_AUTH_TOKEN --refresh-dependencies --stacktrace --info sonarqube ' +
                                        '-Dsonar.enabled=true ' +
                                        '-Dsonar.credentialsId=SONARQUBE_API_KEY ' +
                                        '-Dsonar.projectKey=com.ford.fc.api.ces ' +
                                        '-Dsonar.hostUrl=https://www.sonarqube.ford.com ' +
                                        '-Dsonar.jacoco.reportPath=build/jacoco/jacocoTest.exec ' +
                                        '-Dsonar.language=java ' +
                                        '-Dsonar.sources=. ' +
                                        '-Dsonar.tests=. ' +
                                        '-Dsonar.test.inclusions=**/*Test*/** ' +
                                        '-Dsonar.exclusions=src/test/java/**,**/entity/**,**/config/**,**/domain/**,**/constants/**,**/exception/**,**/ChinaamoApplication.*,**/blockedRoutingDetails/**,**/client/lookup/la/domain/**,**/Checkmarx/Reports/**,**/resources**,**/util/**' +
                                        '-Dsonar.projectVersion=$BUILD_NUMBER'
                                sh 'echo "sonarqube analysis request complete. Checking quality gate"'
                                }
                            }
                            }
                            }
                 post {
                             success {
                                   sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                                            sh "./scripts/taskLogsToSplunk.sh $BUILD_AND_SONAR_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                                        }
                                        failure {
                                            sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                                            sh "./scripts/taskLogsToSplunk.sh $BUILD_AND_SONAR_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                                        }
                                    }

                }

       stage("FOSSA") {
                steps {
                        script {
                            tee(FOSSA_STAGE_LOG_FILE) {
                       withCredentials([string(credentialsId: "FOSSA_API_KEY", variable: "fossa_api_key")]) {
                                   sh("FOSSA_API_KEY=$fossa_api_key fossa analyze -e '$FOSSA_SERVER' -T '$FOSSA_TEAM' -p '$FOSSA_TEAM' --policy 'Website/Hosted Service Use' -b '$GIT_BRANCH' --title '$PROJECT_NAME'")
                       }
                       }
                       }
                }
                    post {
                        success {
                            sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                            sh "./scripts/taskLogsToSplunk.sh $FOSSA_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                        }
                        failure {
                            sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                            sh "./scripts/taskLogsToSplunk.sh $FOSSA_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                        }
                    }
       }
        stage('CF Map Real Route Dev (KS1)') {
                                   environment{
                                        CF_HOME = '${spaceDev}'
                                    }
                                   steps{
                                   script {
                                        tee(DEPLOYMENT_STAGE_LOG_FILE) {
                                        withCredentials([usernamePassword(credentialsId: cfCredentialsIdPreprod, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                            sh "cf --version"
                                            sh "cf login -a ${pcfUrlPreprod} -o ${cfOrgPreprod} -s ${spaceDev} -u $USERNAME -p $PASSWORD"
                                            sh "cf push ${appName} -f manifest.yml --vars-file vars-ks1-dev.yml"
                     				    }
                                  }
                                  }
                                  }
                                        post {
                                             success {
                                                 sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                                                 sh "./scripts/taskLogsToSplunk.sh $DEPLOYMENT_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                                             }
                                             failure {
                                                 sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                                                 sh "./scripts/taskLogsToSplunk.sh $DEPLOYMENT_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                                             }
                                         }
                              }

        stage('CF Map Real Route Dev (SH8)') {
                                   environment{
                                     CF_HOME = '${spaceDev}'
                                           }
                                     steps{
                                      withCredentials([usernamePassword(credentialsId: cfCredentialsIdPreprod, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                          sh "cf --version"
                                          sh "cf login -a ${pcfUrlPreprod2} -o ${cfOrgPreprod2} -s ${spaceDev} -u $USERNAME -p $PASSWORD"
                                          sh "cf push ${appName} -f manifest.yml --vars-file vars-sh8-dev.yml"
                                            }
                                           }
                                         }

        stage('DEV Integration Test KS1') {
                      steps {
                        script {
                            tee(INTEGRATION_STAGE_LOG_FILE) {
                            withCredentials([string(credentialsId: "DEV_ADFS_CLIENT_SECRET", variable: "client_secret")]) {
                                      sh 'echo "DEV Integration Test KS1"'
                                      sh 'newman -version'
                                      sh 'newman run newman/' + newmanFilePrefix + '-Dev.postman_collection.json -e newman/' + newmanFilePrefix + '-Dev-ks1.postman_environment.json --env-var "adfs_dev_client_secret=$client_secret"'
                        }
                       }
                       }
                }
                                    post {
                                        success {
                                            sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                                            sh "./scripts/taskLogsToSplunk.sh $INTEGRATION_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                                        }
                                        failure {
                                            sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                                            sh "./scripts/taskLogsToSplunk.sh $INTEGRATION_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                                        }
                                    }
    }
        stage('DEV Integration Test SH8') {
                            steps {
                                 script {
                                                     withCredentials([string(credentialsId: "DEV_ADFS_CLIENT_SECRET", variable: "client_secret")]) {
                                sh 'echo "DEV Integration Test SH8"'
                                sh 'newman -version'
                                sh 'newman run newman/' + newmanFilePrefix + '-Dev.postman_collection.json -e newman/' + newmanFilePrefix + '-Dev-sh8.postman_environment.json --env-var "adfs_dev_client_secret=$client_secret"'
                            }
                            }
                            }
        }
    }
    post {
        success {
            sh "chmod +x ./scripts/jenkinsLogsToSplunk.sh"
            sh "./scripts/jenkinsLogsToSplunk.sh $PROJECT_NAME $ENVIRONMENT_NAME $SUCCESS_STATUS"
            echo 'I succeeded!'
        }
        failure {
            sh "chmod +x ./scripts/jenkinsLogsToSplunk.sh"
            sh "./scripts/jenkinsLogsToSplunk.sh $PROJECT_NAME $ENVIRONMENT_NAME $FAILED_STATUS"
            echo 'I failed :('
        }
    }
}
def githubStatus(String githubUrl, String state, String targetUrl, String description, String context) {
	withCredentials([string(credentialsId: "Github", variable: "githubToken")]) {
		sh "curl -X POST -H \"Authorization: token $githubToken\" $githubUrl -d \'{\"state\":\"$state\",\"target_url\": \"$targetUrl\", \"description\": \"$description\", \"context\": \"$context\"}\'"
	}
}